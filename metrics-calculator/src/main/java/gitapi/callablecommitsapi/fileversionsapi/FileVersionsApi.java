package gitapi.callablecommitsapi.fileversionsapi;

import domain.git.Repository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import utils.ProcessExecutor;

import java.io.File;
import java.net.URL;
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

    @SneakyThrows
    private static void downloadFileVersionToDirectory(String directoryAbsolutePath,
                                                       FileVersion fileVersion) {
        File path = new File(directoryAbsolutePath);
        if(!path.getParentFile().exists()) {
            path.mkdirs();
        }

        FileUtils.copyURLToFile(new URL(fileVersion.getFileURI()), new File(directoryAbsolutePath + "/" + fileVersion.getFileName()));
    }

}
