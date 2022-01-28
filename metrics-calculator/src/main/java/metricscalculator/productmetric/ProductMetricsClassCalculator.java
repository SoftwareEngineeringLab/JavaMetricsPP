package metricscalculator.productmetric;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.Metric;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import metricscalculator.productmetric.ck.BidirectionalCouplingBetweenObjectsMetric;
import metricscalculator.productmetric.ck.CouplingBetweenObjectsMetric;
import metricscalculator.productmetric.ck.DepthOfInheritanceTreeMetric;
import metricscalculator.productmetric.ck.LackOfCohesionInMethodsMetric;
import metricscalculator.productmetric.ck.NumberOfChildrenMetric;
import metricscalculator.productmetric.ck.ResponseForClassMetric;
import metricscalculator.productmetric.ck.WeightedMethodsPerClassMetric;
import metricscalculator.productmetric.ck.WeightedMethodsPerClassWithoutAccessorAndMutatorMethodsMetric;
import metricscalculator.productmetric.general.AccessToForeignDataMetric;
import metricscalculator.productmetric.general.ForeignDataProvidersMetric;
import metricscalculator.productmetric.general.LambdaDensityMetric;
import metricscalculator.productmetric.general.LinesOfCodeInClassMetric;
import metricscalculator.productmetric.general.LocalityOfAttributeAccessesMetric;
import metricscalculator.productmetric.general.MethodReferenceDensityMetric;
import metricscalculator.productmetric.general.NumberOfAccessorMethodsMetric;
import metricscalculator.productmetric.general.NumberOfAnnotationsMetric;
import metricscalculator.productmetric.general.NumberOfInheritedMethodsMetric;
import metricscalculator.productmetric.general.NumberOfLambdaExpressionsInClassMetric;
import metricscalculator.productmetric.general.NumberOfMethodReferencesPerClassMetric;
import metricscalculator.productmetric.general.NumberOfMethodsMetric;
import metricscalculator.productmetric.general.NumberOfMutatorMethodsMetric;
import metricscalculator.productmetric.general.NumberOfPrivateFieldsMetric;
import metricscalculator.productmetric.general.NumberOfPublicFieldsMetric;
import metricscalculator.productmetric.general.WeightOfClassMetric;
import metricscalculator.productmetric.martin.AfferentCouplingMetric;
import metricscalculator.productmetric.martin.EfferentCouplingMetric;
import metricscalculator.productmetric.qmood.CohesionAmongMethodsMetric;
import metricscalculator.productmetric.qmood.DataAccessMetric;
import metricscalculator.productmetric.qmood.MeasureOfAggregationMetric;
import metricscalculator.productmetric.qmood.MeasureOfFunctionalAbstractionMetric;
import metricscalculator.productmetric.qmood.NumberOfPublicMethodsMetric;

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
