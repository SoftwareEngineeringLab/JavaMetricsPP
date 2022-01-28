package metricscalculator.productmetric.general;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.resolution.MethodUsage;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NIM metric.
 * NIM - Number of Inherited Methods
 * Number of inherited methods of a given class.
 */
public class NumberOfInheritedMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        ResolvedReferenceTypeDeclaration resolved = classDeclaration.resolve();
        return resolved.getAllMethods().stream()
                .filter(methodUsage -> isInherited(methodUsage, resolved))
                .count();
    }

    private boolean isInherited(MethodUsage methodUsage, ResolvedReferenceTypeDeclaration resolved) {
        return !methodUsage.declaringType().equals(resolved.asType()) &&
                !methodUsage.getDeclaration().accessSpecifier().equals(AccessSpecifier.PRIVATE);
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_INHERITED_METHODS;
    }

}
