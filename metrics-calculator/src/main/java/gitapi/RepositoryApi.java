package gitapi;

import domain.git.Repository;
import domain.git.RepositoryStatus;
import domain.git.RepositoryStatusType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.DirectoryUtils;
import utils.ProcessExecutor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryApi {

    private static final String GIT_ERROR = "fatal";
    private static final String ERROR_BY_LS_REMOTE = "ERROR: Repository not found. fatal: Could not read from remote repository";
    private static final String ERROR_BY_HASH_AVAILABILITY = "fatal: bad object";

    public static void resetRepository(Repository repository) {
        List<String> command = List.of("git", "reset", "--hard", repository.getCurrentHashCommit());
        ProcessExecutor.executeCommandAndReturnProcessLogs(repository.getAbsolutePath(), command);
    }

    public static RepositoryStatus getRepositoryStatus(String addressURI,
                                                       String repositoryAbsolutePath,
                                                       String hash) {
        if (DirectoryUtils.directoryExists(repositoryAbsolutePath)) {
            return getRepositoryStatusFromGit(addressURI, repositoryAbsolutePath, hash);
        }
        return getUnavailableRepositoryStatus("Given repository path does not exist: " + repositoryAbsolutePath);
    }

    private static RepositoryStatus getRepositoryStatusFromGit(String addressURI,
                                                               String repositoryAbsolutePath,
                                                               String hash) {
        RepositoryStatus repositoryStatusByLsRemote = getRepositoryStatusByLsRemote(addressURI, repositoryAbsolutePath);
        if (repositoryStatusByLsRemote.isAvailable()) {
            return getRepositoryStatusByHashAvailability(repositoryAbsolutePath, hash);
        }
        return repositoryStatusByLsRemote;
    }

    private static RepositoryStatus getRepositoryStatusByLsRemote(String addressURI,
                                                                  String repositoryAbsolutePath) {
        List<String> command = List.of("git", "ls-remote", addressURI, "HEAD");
        List<String> processLogs = ProcessExecutor.executeCommandAndReturnProcessLogs(repositoryAbsolutePath, command);
        String possibleError = repositoryAbsolutePath + " - " + ERROR_BY_LS_REMOTE + " " + addressURI;
        return getRepositoryStatusFromProcessLogs(processLogs, possibleError);
    }

    private static RepositoryStatus getRepositoryStatusByHashAvailability(String repositoryAbsolutePath,
                                                                          String hash) {
        List<String> command = List.of("git", "show", "-s", hash);
        List<String> processLogs = ProcessExecutor.executeCommandAndReturnProcessLogs(repositoryAbsolutePath, command);
        String possibleError = repositoryAbsolutePath + " - " + ERROR_BY_HASH_AVAILABILITY + " " + hash;
        return getRepositoryStatusFromProcessLogs(processLogs, possibleError);
    }

    private static RepositoryStatus getRepositoryStatusFromProcessLogs(List<String> processLogs,
                                                                       String possibleError) {
        boolean isErrorInProcessLogs = isErrorInProcessLogs(processLogs);
        if (isErrorInProcessLogs) {
            return getUnavailableRepositoryStatus(possibleError);
        }
        return getAvailableRepositoryStatus();
    }

    private static boolean isErrorInProcessLogs(List<String> processLogs) {
        return processLogs.stream()
                .anyMatch(processLog -> processLog.contains(GIT_ERROR));
    }

    private static RepositoryStatus getUnavailableRepositoryStatus(String errorLog) {
        return RepositoryStatus.builder()
                .statusType(RepositoryStatusType.UNAVAILABLE)
                .errorLog(errorLog)
                .build();
    }

    private static RepositoryStatus getAvailableRepositoryStatus() {
        return RepositoryStatus.builder()
                .statusType(RepositoryStatusType.AVAILABLE)
                .build();
    }

}
