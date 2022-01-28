package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NOAM metric.
 * NOAM - number of accessor methods in a class
 * Here accessor is a method that has only one expression, which is a return expression.
 * Methods of inner classes are counted as a methods of enclosing class!
 */
public class NumberOfAccessorMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return getClassAccessorMethods(classDeclaration).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_ACCESSOR_METHODS;
    }

}
