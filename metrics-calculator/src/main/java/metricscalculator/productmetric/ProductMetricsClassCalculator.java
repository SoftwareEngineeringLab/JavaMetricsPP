package metricscalculator.productmetric;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.Metric;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metricscalculator.productmetric.ck.*;
import metricscalculator.productmetric.general.*;
import metricscalculator.productmetric.martin.AfferentCouplingMetric;
import metricscalculator.productmetric.martin.EfferentCouplingMetric;
import metricscalculator.productmetric.qmood.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMetricsClassCalculator {

    private static final List<ProductMetricClass> PRODUCT_METRICS_WITHOUT_DEPENDENCY_RESOLUTION = List.of(
            new LambdaDensityMetric(),
            new LinesOfCodeInClassMetric(),
            new MethodReferenceDensityMetric(),
            new NumberOfAccessorMethodsMetric(),
            new NumberOfLambdaExpressionsInClassMetric(),
            new NumberOfMethodReferencesPerClassMetric(),
            new NumberOfMethodsMetric(),
            new NumberOfMutatorMethodsMetric(),
            new NumberOfPublicFieldsMetric(),
            new NumberOfPrivateFieldsMetric(),
            new WeightedMethodsPerClassMetric(),
            new WeightedMethodsPerClassWithoutAccessorAndMutatorMethodsMetric(),
            new WeightOfClassMetric(),
            new NumberOfPublicMethodsMetric(),
            new ResponseForClassMetric(),
            new NumberOfAnnotationsMetric());

    private static final List<ProductMetricClass> PRODUCT_METRICS_WITH_DEPENDENCY_RESOLUTION = List.of(
            new AccessToForeignDataMetric(),
            new NumberOfChildrenMetric(),
            new DepthOfInheritanceTreeMetric(),
            new CouplingBetweenObjectsMetric(),
            new LackOfCohesionInMethodsMetric(),
            new BidirectionalCouplingBetweenObjectsMetric(),
            new DataAccessMetric(),
            new MeasureOfAggregationMetric(),
            new MeasureOfFunctionalAbstractionMetric(),
            new CohesionAmongMethodsMetric(),
            new EfferentCouplingMetric(),
            new AfferentCouplingMetric(),
            new ForeignDataProvidersMetric(),
            new LocalityOfAttributeAccessesMetric(),
            new NumberOfInheritedMethodsMetric());

    public static List<Metric> calculateMetricsWithoutDependencyResolution(ClassOrInterfaceDeclaration classDeclaration) {
        return PRODUCT_METRICS_WITHOUT_DEPENDENCY_RESOLUTION.stream()
                .map(productMetric -> productMetric.compute(classDeclaration))
                .toList();
    }

    public static List<Metric> calculateMetricsWithDependencyResolution(ClassOrInterfaceDeclaration classDeclaration) {
        return PRODUCT_METRICS_WITH_DEPENDENCY_RESOLUTION.stream()
                .map(productMetric -> productMetric.compute(classDeclaration))
                .toList();
    }

}
