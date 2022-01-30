package input.row;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MLCQRow {

    private final String sampleId;
    private final Smell smell;
    private final int severity;

}
