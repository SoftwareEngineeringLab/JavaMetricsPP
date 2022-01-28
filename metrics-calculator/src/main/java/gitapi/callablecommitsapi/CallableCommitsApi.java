package gitapi.callablecommitsapi;

import config.Constants;
import domain.code.investigatedcallable.InvestigatedCallable;
import domain.git.Commit;
import domain.git.Repository;
import gitapi.CommitMapper;
import gitapi.callablecommitsapi.codesamplemodification.CodeSampleModification;
import gitapi.callablecommitsapi.codesamplemodification.CodeSampleModificationsProvider;
import gitapi.callablecommitsapi.fileversionsapi.FileVersion;
import gitapi.callablecommitsapi.fileversionsapi.FileVersionsApi;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.DirectoryUtils;

import java.io.File;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CallableCommitsApi {

    public static List<Commit> getCommits(InvestigatedCallable investigatedCallable) {
        String directoryAbsolutePath = getDirectoryAbsolutePath(investigatedCallable.getId());
        DirectoryUtils.prepareDirectory(directoryAbsolutePath);

        List<FileVersion> downloadedFileVersions = downloadFileVersionsToDirectory(directoryAbsolutePath,
                investigatedCallable.getRepository(),
                investigatedCallable.getFileRelativePath());

        List<Commit> commits;
        if (isEveryFileVersionProcessable(downloadedFileVersions)) {
            List<CodeSampleModification> codeSampleModifications = getCodeSampleModifications(investigatedCallable, downloadedFileVersions);
            commits = getCommits(codeSampleModifications, investigatedCallable.getRepository());
        } else {
            commits = Collections.emptyList();
            investigatedCallable.setProcessed(false);
            System.out.println("Code sample is not processed! " + investigatedCallable.getId());
        }

        DirectoryUtils.deleteDirectory(directoryAbsolutePath);

        return commits;
    }

    private static String getDirectoryAbsolutePath(int sampleId) {
        return Constants.WORK_DIRECTORY + File.separator + "sample_" + sampleId;
        //return DEFAULT_OUTPUT_REPOSITORY_DIR + File.separator + WORK_DIRECTORY_NAME + File.separator + "sample_" + sampleId;
    }

    private static List<FileVersion> downloadFileVersionsToDirectory(String directoryAbsolutePath,
                                                                     Repository repository,
                                                                     String fileRelativePath) {
        return FileVersionsApi.downloadFileVersionsToDirectory(directoryAbsolutePath, repository, fileRelativePath);
    }

    private static boolean isEveryFileVersionProcessable(List<FileVersion> fileVersions) {
        return fileVersions.stream()
                .allMatch(FileVersion::isProcessable);
    }

    private static List<CodeSampleModification> getCodeSampleModifications(InvestigatedCallable investigatedCallable,
                                                                           List<FileVersion> fileVersions) {
        return CodeSampleModificationsProvider.from(investigatedCallable, fileVersions)
                .stream()
                .filter(CodeSampleModification::modifiesCode)
                .toList();
    }

    private static List<Commit> getCommits(List<CodeSampleModification> codeSampleModifications,
                                           Repository repository) {
        return codeSampleModifications.stream()
                .map(codeSampleModification -> CommitMapper.from(codeSampleModification, repository))
                .toList();
    }

}
