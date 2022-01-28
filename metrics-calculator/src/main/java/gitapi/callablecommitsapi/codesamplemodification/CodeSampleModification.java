package gitapi.callablecommitsapi.codesamplemodification;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CodeSampleModification {

    private final String hash;
    private final int addedLines;
    private final int deletedLines;

    private final boolean isCreatingCodeSample;

    public boolean modifiesCode() {
        return isCreatingCodeSample || addedLines != 0 || deletedLines != 0;
    }

}
