package input;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class CSVInputRow {

    private final int sampleId;
    private final String type;
    private final String repositoryAddressURI;
    private final String repositoryAbsolutePath;
    private final String currentHashCommit;
    private final int startLine;
    private final int endLine;
    private final String fileRelativePath;
    private final String fileAbsolutePath;
    private final String className;
    private final String methodName;
    private final List<String> arguments;

}
