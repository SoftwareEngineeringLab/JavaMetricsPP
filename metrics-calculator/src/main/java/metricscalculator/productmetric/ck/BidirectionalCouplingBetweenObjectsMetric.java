package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for computing CBOMZ metric.
 * CBOMZ - bidirectional coupling between objects (These couplings can occur through method
 * calls, variable accesses, inheritance, method arguments, return types,
 * and exceptions). Coupled classes are not deduplicated and therefore the detection of sensitivity of change is increased.
 */
public class BidirectionalCouplingBetweenObjectsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        Set<ClassOrInterfaceDeclaration> afferentClasses = getAfferentCouplingClasses(classDeclaration);
        Set<String> afferent = afferentClasses.stream()
                .map(clazz -> clazz.getFullyQualifiedName().orElse(""))
                .collect(Collectors.toSet());
        Set<String> efferent = getEfferentCouplingClasses(classDeclaration);
        return afferent.size() + efferent.size();
    }

    @Override
    public MetricName getName() {
        return MetricName.BIDIRECTIONAL_COUPLING_BETWEEN_OBJECTS;
    }

}
