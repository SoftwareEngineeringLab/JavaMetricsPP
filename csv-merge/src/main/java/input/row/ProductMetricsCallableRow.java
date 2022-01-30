package input.row;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class ProductMetricsCallableRow extends MetricsRow {

    private final String mcCabeCyclomaticComplexity;
    private final String linesOfCodeInCallable;
    private final String numberOfLambdaExpressionsInCallable;
    private final String numberOfMethodReferencesPerCallable;

    @Override
    public List<String> getMetrics() {
        return List.of(mcCabeCyclomaticComplexity,
                linesOfCodeInCallable,
                numberOfLambdaExpressionsInCallable,
                numberOfMethodReferencesPerCallable);
    }

}
