package gitapi.callablecommitsapi.fileversionsapi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static config.Constants.JAVA_FILE_EXTENSION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FollowNameOnlyGitLogsProvider {

    public static List<FollowNameOnlyGitLog> from(List<String> processLogs) {
        List<FollowNameOnlyGitLog> followNameOnlyGitLogs = new ArrayList<>();

        int currentLineIndex = 0;

        while (currentLineIndex < processLogs.size()) {
            String hash = getHash(processLogs.get(currentLineIndex));
            currentLineIndex++;

            boolean fileIsChanged = false;
            String fileRelativePath = null;
            while (currentLineIndex < processLogs.size() && lineContainsPath((processLogs.get(currentLineIndex)))) {
                if (processLogs.get(currentLineIndex).contains(JAVA_FILE_EXTENSION)) {
                    fileRelativePath = processLogs.get(currentLineIndex);
                    fileIsChanged = true;
                }
                currentLineIndex++;
            }

            if (fileIsChanged) {
                FollowNameOnlyGitLog followNameOnlyGitLog = FollowNameOnlyGitLog.builder()
                        .hash(hash)
                        .fileRelativePath(fileRelativePath)
                        .build();
                followNameOnlyGitLogs.add(followNameOnlyGitLog);
            }

        }
        return followNameOnlyGitLogs;
    }

    private static String getHash(String processLog) {
        String[] processLogElements = processLog.split("\\s+");
        return processLogElements[0];
    }

    private static boolean lineContainsPath(String processLog) {
        return processLog.split("\\s+").length == 1;
    }

}
