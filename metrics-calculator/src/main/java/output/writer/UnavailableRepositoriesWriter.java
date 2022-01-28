package output.writer;

import config.Constants;
import domain.git.Repository;
import domain.git.RepositoryStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UnavailableRepositoriesWriter {

    public static void write(List<Repository> repositories) {
        try {
            FileWriter writer = new FileWriter(Constants.ERROR_FILE_PATH);
            List<String> errorLogs = getErrorLogs(repositories);
            for (String errorLog : errorLogs) {
                writer.write(errorLog);
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getErrorLogs(List<Repository> repositories) {
        return repositories.stream()
                .filter(Repository::isUnavailable)
                .map(Repository::getStatus)
                .map(RepositoryStatus::getErrorLog)
                .toList();
    }

}
