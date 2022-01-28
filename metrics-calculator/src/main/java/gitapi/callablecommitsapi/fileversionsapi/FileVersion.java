package gitapi.callablecommitsapi.fileversionsapi;

import com.github.javaparser.ast.CompilationUnit;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import utils.CompilationUnitProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
@Builder
@ToString
public class FileVersion {

    private static final long FILE_MAX_SIZE = 100_000;

    private final String hash;
    private final String fileURI;
    private final String fileAbsolutePath;
    private final String fileName;

    public CompilationUnit getCompilationUnit() {
        return CompilationUnitProvider.from(fileAbsolutePath);
    }

    public boolean isProcessable() {
        try {
            return getFileSize() <= FILE_MAX_SIZE;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private long getFileSize() throws IOException {
        Path path = Paths.get(fileAbsolutePath);
        return Files.lines(path).count();
    }

}
