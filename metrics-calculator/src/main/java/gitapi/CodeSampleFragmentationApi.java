package gitapi;

import domain.code.CodeSample;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.ProcessExecutor;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeSampleFragmentationApi {

    private static final Pattern PATTERN = Pattern.compile("<.*>");

    public static long getFragmentationAcrossDevelopers(CodeSample codeSample) {
        int startLine = codeSample.getStartLine();
        int endLine = codeSample.getEndLine();

        String fileRelativePath = codeSample.getFileRelativePath();
        String repositoryAbsolutePath = codeSample.getRepository().getAbsolutePath();
        String range = startLine + "," + endLine;

        List<String> command = List.of("git", "blame", "--show-email", "-L", range, fileRelativePath);
        List<String> processLogs = ProcessExecutor.executeCommandAndReturnProcessLogs(repositoryAbsolutePath, command);

        return getFragmentationAcrossDevelopers(processLogs);
    }

    private static long getFragmentationAcrossDevelopers(List<String> processLogs) {
        return processLogs.stream()
                .map(CodeSampleFragmentationApi::getMail)
                .distinct()
                .count();
    }

    private static String getMail(String processLog) {
        return Optional.of(PATTERN.matcher(processLog))
                .filter(Matcher::find)
                .map(matcher -> matcher.group().replaceAll("[<>]", ""))
                .orElse("");
    }

}
