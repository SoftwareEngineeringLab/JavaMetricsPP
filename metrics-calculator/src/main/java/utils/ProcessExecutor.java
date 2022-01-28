package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessExecutor {

    public static List<String> executeCommandAndReturnProcessLogs(String directoryAbsolutePath,
                                                                  List<String> command) {
        List<String> processLogs = null;
        try {
            Process process = startProcess(directoryAbsolutePath, command);
            processLogs = readProcessLogs(process);
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return processLogs;
    }

    private static Process startProcess(String directoryAbsolutePath,
                                        List<String> command) throws IOException {
        return new ProcessBuilder(command)
                .directory(new File(directoryAbsolutePath))
                .redirectErrorStream(true)
                .start();
    }

    private static List<String> readProcessLogs(Process process) throws IOException {
        List<String> processLogs = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            processLogs.add(line);
        }
        return processLogs;
    }

}
