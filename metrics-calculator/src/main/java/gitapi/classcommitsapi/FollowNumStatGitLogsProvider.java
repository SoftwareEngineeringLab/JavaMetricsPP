package gitapi.classcommitsapi;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static config.Constants.JAVA_FILE_EXTENSION;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class FollowNumStatGitLogsProvider {

    public static List<FollowNumStatGitLog> from(List<String> processLogs) {
        List<FollowNumStatGitLog> followNumStatGitLogs = new ArrayList<>();

        int currentLineIndex = 0;

        while (currentLineIndex < processLogs.size()) {
            String hash = processLogs.get(currentLineIndex);
            currentLineIndex++;

            String mail = processLogs.get(currentLineIndex);
            currentLineIndex++;

            LocalDate date = LocalDate.parse(processLogs.get(currentLineIndex));
            currentLineIndex++;

            String message = processLogs.get(currentLineIndex);
            currentLineIndex++;
            currentLineIndex++;

            boolean fileIsChanged = false;
            int addedLines = 0;
            int deletedLines = 0;
            while (currentLineIndex < processLogs.size() && lineContainsPath((processLogs.get(currentLineIndex)))) {
                if (processLogs.get(currentLineIndex).contains(JAVA_FILE_EXTENSION)) {
                    addedLines = getAddedLines(processLogs.get(currentLineIndex));
                    deletedLines = getDeletedLines(processLogs.get(currentLineIndex));
                    fileIsChanged = true;
                }
                currentLineIndex++;
            }

            if (fileIsChanged) {
                FollowNumStatGitLog followNumStatGitLog = FollowNumStatGitLog.builder()
                        .hash(hash)
                        .mail(mail)
                        .date(date)
                        .message(message)
                        .addedLines(addedLines)
                        .deletedLines(deletedLines)
                        .build();

                followNumStatGitLogs.add(followNumStatGitLog);
            }
        }
        return followNumStatGitLogs;
    }

    private static boolean lineContainsPath(String processLog) {
        return processLog.split("\\s+").length != 1;
    }

    private static int getAddedLines(String processLog) {
        String[] processLogElements = processLog.split("\\s+");
        return Integer.parseInt(processLogElements[0]);
    }

    private static int getDeletedLines(String processLog) {
        String[] processLogElements = processLog.split("\\s+");
        return Integer.parseInt(processLogElements[1]);
    }

}
