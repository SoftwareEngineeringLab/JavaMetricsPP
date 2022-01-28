package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NOM metric.
 * NOM - number of methods in a class
 * Methods of inner classes are counted as a methods of enclosing class!
 */
public class NumberOfMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(MethodDeclaration.class).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_METHODS;
    }

}
