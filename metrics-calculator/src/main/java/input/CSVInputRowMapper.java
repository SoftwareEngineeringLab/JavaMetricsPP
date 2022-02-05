package input;

import config.Constants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVInputRowMapper {

    private static final String DEFAULT_OUTPUT_REPOSITORY_NAME = "unknown-repository";
    private static final Pattern PATTERN = Pattern.compile(".*:(.*)\\.git");

    public static CSVInputRow from(CSVRecord csvRecord) {
        int sampleId = Integer.parseInt(csvRecord.get(CSVInputHeader.SAMPLE_ID));
        String type = csvRecord.get(CSVInputHeader.TYPE);
        String repositoryAddressURI = csvRecord.get(CSVInputHeader.REPOSITORY);
        String repositoryAbsolutePath = getRepositoryAbsolutePath(repositoryAddressURI);
        String currentHashCommit = csvRecord.get(CSVInputHeader.COMMIT_HASH);
        int startLine = Integer.parseInt(csvRecord.get(CSVInputHeader.START_LINE));
        int endLine = Integer.parseInt(csvRecord.get(CSVInputHeader.END_LINE));
        String fileRelativePath = getFileRelativePath(csvRecord.get(CSVInputHeader.PATH));
        String fileAbsolutePath = getFileAbsolutePath(repositoryAbsolutePath, fileRelativePath);
        String className = csvRecord.get(CSVInputHeader.CLASS);
        String methodName = csvRecord.get(CSVInputHeader.METHOD);
        List<String> arguments = Arrays.asList(csvRecord.get(CSVInputHeader.PARAMETERS).split("\\|"));
        return CSVInputRow.builder()
                .sampleId(sampleId)
                .type(type)
                .repositoryAddressURI(repositoryAddressURI)
                .repositoryAbsolutePath(repositoryAbsolutePath)
                .currentHashCommit(currentHashCommit)
                .startLine(startLine)
                .endLine(endLine)
                .fileRelativePath(fileRelativePath)
                .fileAbsolutePath(fileAbsolutePath)
                .className(className)
                .methodName(methodName)
                .arguments(arguments)
                .build();
    }

    private static String getRepositoryAbsolutePath(String repositoryAddressURI) {
        String repositoryAbsolutePath = Constants.REPOSITORIES_DIRECTORY + File.separator + getRepositoryName(repositoryAddressURI);
        return repositoryAbsolutePath.replace("/", File.separator);
    }

    private static String getRepositoryName(String repositoryAddressURI) {
        return Optional.of(PATTERN.matcher(repositoryAddressURI))
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1))
                .orElse(DEFAULT_OUTPUT_REPOSITORY_NAME);
    }

    private static String getFileRelativePath(String filePath) {
        return filePath.substring(1);
    }

    private static String getFileAbsolutePath(String repositoryAbsolutePath,
                                              String fileRelativePath) {
        return repositoryAbsolutePath + File.separator + fileRelativePath;
    }

}
