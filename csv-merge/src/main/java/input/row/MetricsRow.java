package input.row;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public abstract class MetricsRow {

    protected final String sampleId;
    protected final CodeSampleType codeSampleType;

    public abstract List<String> getMetrics();

    public boolean isCallable() {
        return codeSampleType == CodeSampleType.CONSTRUCTOR || codeSampleType == CodeSampleType.METHOD;
    }

    public boolean isClass() {
        return codeSampleType == CodeSampleType.CLASS;
    }

}
