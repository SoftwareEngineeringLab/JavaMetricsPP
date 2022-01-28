package metricscalculator.productmetric;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.Metric;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metricscalculator.productmetric.general.LinesOfCodeInCallableMetric;
import metricscalculator.productmetric.general.McCabeCyclomaticComplexityMetric;
import metricscalculator.productmetric.general.NumberOfLambdaExpressionsInCallableMetric;
import metricscalculator.productmetric.general.NumberOfMethodReferencesPerCallableMetric;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMetricsCallableCalculator {

    private static final List<ProductMetricCallable> PRODUCT_METRICS_WITHOUT_DEPENDENCY_RESOLUTION = List.of(
            new McCabeCyclomaticComplexityMetric(),
            new LinesOfCodeInCallableMetric(),
            new NumberOfLambdaExpressionsInCallableMetric(),
            new NumberOfMethodReferencesPerCallableMetric());

    public static List<Metric> calculateMetricsWithoutDependencyResolution(ConstructorDeclaration constructorDeclaration) {
        return PRODUCT_METRICS_WITHOUT_DEPENDENCY_RESOLUTION.stream()
                .map(productMetric -> productMetric.compute(constructorDeclaration))
                .toList();
    }

    public static List<Metric> calculateMetricsWithoutDependencyResolution(MethodDeclaration methodDeclaration) {
        return PRODUCT_METRICS_WITHOUT_DEPENDENCY_RESOLUTION.stream()
                .map(productMetric -> productMetric.compute(methodDeclaration))
                .toList();
    }

}
