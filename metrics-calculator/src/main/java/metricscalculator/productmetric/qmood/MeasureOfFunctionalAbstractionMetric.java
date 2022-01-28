package metricscalculator.productmetric.qmood;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing MFA metric.
 * MFA (Measure of Functional Abstraction) - ratio of the number of methods inherited by a class to the total number
 * of methods of the class. Constructors are omitted.
 */
public class MeasureOfFunctionalAbstractionMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        double allMethodsNumber = getAllClassMethodNames(classDeclaration).size();
        if (allMethodsNumber == 0) {
            return 0;
        }
        double classMethodsNumber = classDeclaration.getMethods().size();
        return (allMethodsNumber - classMethodsNumber) / allMethodsNumber;
    }

    @Override
    public MetricName getName() {
        return MetricName.MEASURE_OF_FUNCTIONAL_ABSTRACTION;
    }

}
