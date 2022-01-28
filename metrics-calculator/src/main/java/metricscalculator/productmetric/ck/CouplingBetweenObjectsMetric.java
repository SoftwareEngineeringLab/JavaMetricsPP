package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for computing CBO metric.
 * CBO - coupling between objects (These couplings can occur through method
 * calls, variable accesses, inheritance, method arguments, return types,
 * and exceptions)
 */
public class CouplingBetweenObjectsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        Set<ClassOrInterfaceDeclaration> afferentClasses = getAfferentCouplingClasses(classDeclaration);
        Set<String> afferent = afferentClasses.stream()
                .map(clazz -> clazz.getFullyQualifiedName().orElse(""))
                .collect(Collectors.toSet());
        Set<String> efferent = getEfferentCouplingClasses(classDeclaration);
        Set<String> result = new HashSet<>(afferent);
        result.addAll(efferent);
        return result.size();
    }

    @Override
    public MetricName getName() {
        return MetricName.COUPLING_BETWEEN_OBJECTS;
    }

}
