package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NOMM metric.
 * NOMM - number of mutator methods in a class
 * Here mutator is a method that has only one expression, which is an assignment expression.
 * It has to have one parameter that should be a source and assign expression should take any class' field as a target.
 * Methods of inner classes are counted as a methods of enclosing class!
 */
public class NumberOfMutatorMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return getClassMutatorMethods(classDeclaration).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_MUTATOR_METHODS;
    }

}
