package gitapi.callablecommitsapi.fileversionsapi;

import domain.git.Repository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.ProcessExecutor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileVersionsApi {

    public static List<FileVersion> downloadFileVersionsToDirectory(String directoryAbsolutePath,
                                                                    Repository repository,
                                                                    String fileRelativePath) {
        List<FollowNameOnlyGitLog> followNameOnlyGitLogs = getFollowNameOnlyGitLogs(repository.getAbsolutePath(), fileRelativePath);
        List<FileVersion> fileVersions = getFileVersionsToDownload(repository, directoryAbsolutePath, followNameOnlyGitLogs);
        System.out.println(fileVersions.size() + " files have to be downloaded");
        downloadFileVersionsToDirectory(directoryAbsolutePath, fileVersions);
        return fileVersions;
    }

    private static List<FollowNameOnlyGitLog> getFollowNameOnlyGitLogs(String repositoryAbsolutePath,
                                                                       String fileRelativePath) {
        List<String> command = List.of("git", "log", "--follow", "--name-only", "--oneline", fileRelativePath);
        List<String> processLogs = ProcessExecutor.executeCommandAndReturnProcessLogs(repositoryAbsolutePath, command);
        return FollowNameOnlyGitLogsProvider.from(processLogs);
    }

    private static List<FileVersion> getFileVersionsToDownload(Repository repository,
                                                               String directoryAbsolutePath,
                                                               List<FollowNameOnlyGitLog> followNameOnlyGitLogs) {
        return followNameOnlyGitLogs.stream()
                .map(followNameOnlyGitLog -> FileVersionMapper.from(repository, directoryAbsolutePath, followNameOnlyGitLog))
                .toList();
    }

    private static void downloadFileVersionsToDirectory(String directoryAbsolutePath,
                                                        List<FileVersion> fileVersions) {
        fileVersions.forEach(fileVersion -> downloadFileVersionToDirectory(directoryAbsolutePath, fileVersion));
    }

    private static void downloadFileVersionToDirectory(String directoryAbsolutePath,
                                                       FileVersion fileVersion) {
        List<String> command = List.of("wget", "-O", fileVersion.getFileName(), fileVersion.getFileURI());
        ProcessExecutor.executeCommandAndReturnProcessLogs(directoryAbsolutePath, command);
    }

}
