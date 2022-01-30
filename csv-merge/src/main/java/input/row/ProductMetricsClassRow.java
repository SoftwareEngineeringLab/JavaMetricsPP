package input.row;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class ProductMetricsClassRow extends MetricsRow {

    private final String lambdaDensity;
    private final String linesOfCodeInClass;
    private final String methodReferenceDensity;
    private final String numberOfAccessorMethods;
    private final String numberOfLambdaExpressionsInClass;
    private final String numberOfMethodReferencesPerClass;
    private final String numberOfMethods;
    private final String numberOfMutatorMethods;
    private final String numberOfPublicFields;
    private final String numberOfPrivateFields;
    private final String weightedMethodsPerClass;
    private final String weightedMethodsPerClassWithoutAccessorAndMutatorMethods;
    private final String weightOfClass;
    private final String numberOfPublicMethods;
    private final String responseForClass;
    private final String numberOfAnnotations;

    @Override
    public List<String> getMetrics() {
        return List.of(lambdaDensity,
                linesOfCodeInClass,
                methodReferenceDensity,
                numberOfAccessorMethods,
                numberOfLambdaExpressionsInClass,
                numberOfMethodReferencesPerClass,
                numberOfMethods,
                numberOfMutatorMethods,
                numberOfPublicFields,
                numberOfPrivateFields,
                weightedMethodsPerClass,
                weightedMethodsPerClassWithoutAccessorAndMutatorMethods,
                weightOfClass,
                numberOfPublicMethods,
                responseForClass,
                numberOfAnnotations);
    }

}
