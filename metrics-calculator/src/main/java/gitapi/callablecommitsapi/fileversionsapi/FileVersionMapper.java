package gitapi.callablecommitsapi.fileversionsapi;

import domain.git.Repository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.text.MessageFormat;

import static config.Constants.JAVA_FILE_EXTENSION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileVersionMapper {

    private static final String FILE_URI_PATTERN = "https://raw.githubusercontent.com/{0}/{1}/{2}/{3}";

    public static FileVersion from(Repository repository,
                                   String directoryAbsolutePath,
                                   FollowNameOnlyGitLog followNameOnlyGitLog) {
        String hash = followNameOnlyGitLog.getHash();
        String fileRelativePath = followNameOnlyGitLog.getFileRelativePath();
        String fileURI = getFileURI(repository, hash, fileRelativePath);
        String fileName = hash + JAVA_FILE_EXTENSION;
        String fileAbsolutePath = directoryAbsolutePath + File.separator + fileName;
        return FileVersion.builder()
                .hash(hash)
                .fileURI(fileURI)
                .fileName(fileName)
                .fileAbsolutePath(fileAbsolutePath)
                .build();
    }

    private static String getFileURI(Repository repository,
                                     String hash,
                                     String fileRelativePath) {
        return MessageFormat.format(FILE_URI_PATTERN,
                repository.getUserName(),
                repository.getProjectName(),
                hash,
                fileRelativePath);
    }

}
