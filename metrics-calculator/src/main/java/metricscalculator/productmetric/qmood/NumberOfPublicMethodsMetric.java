package metricscalculator.productmetric.qmood;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NPM metric.
 * NPM - all the methods in a class that are declared as public.
 */
public class NumberOfPublicMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(MethodDeclaration.class).stream()
                .filter(this::isPublic)
                .count();
    }

    private boolean isPublic(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getAccessSpecifier().equals(AccessSpecifier.PUBLIC);
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_PUBLIC_METHODS;
    }

}
