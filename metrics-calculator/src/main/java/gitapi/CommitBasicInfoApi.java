package gitapi;

import domain.git.CommitBasicInfo;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.ProcessExecutor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommitBasicInfoApi {

    public static CommitBasicInfo getCommitBasicInfo(String repositoryAbsolutePath,
                                                     String hash) {
        List<String> command = List.of("git", "show", "-s", "--format=%ae%n%cd%n%B",
                "--date=format:%Y-%m-%d", hash);
        List<String> processLogs = ProcessExecutor.executeCommandAndReturnProcessLogs(repositoryAbsolutePath, command);
        return getCommitBasicInfo(hash, processLogs);
    }

    private static CommitBasicInfo getCommitBasicInfo(String hash,
                                                      List<String> processLogs) {
        String mail = getMail(processLogs);
        LocalDate date = getDate(processLogs);
        String message = getMessage(processLogs);
        return CommitBasicInfo.builder()
                .hash(hash)
                .mail(mail)
                .date(date)
                .message(message)
                .build();
    }

    private static String getMail(List<String> processLogs) {
        return processLogs.get(0);
    }

    private static LocalDate getDate(List<String> processLogs) {
        return LocalDate.parse(processLogs.get(1));
    }

    private static String getMessage(List<String> processLogs) {
        return processLogs.get(2);
    }

}
