package metricscalculator.productmetric.qmood;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.HashSet;
import java.util.Set;

/**
 * Class for computing CAM metric.
 * CAM (Cohesion Among Methods of Class) - computes the relatedness among methods of a class based upon the parameter
 * list of the methods. The metric is computed using the summation of number of different types
 * of method parameters in every method divided by a multiplication of number of different method parameter types
 * in whole class and number of methods
 */
public class CohesionAmongMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        double methodsNumber = classDeclaration.getMethods().size();
        if (methodsNumber == 0) {
            return 0;
        }
        double distinctParametersNumber = getDistinctParametersNumber(classDeclaration);
        return distinctParametersNumber / (methodsNumber * distinctParametersNumber);
    }

    private int getDistinctParametersNumber(ClassOrInterfaceDeclaration classDeclaration) {
        Set<String> distinctParameters = new HashSet<>();
        classDeclaration.getMethods().forEach(methodDeclaration -> {
            for (Parameter parameter : methodDeclaration.getParameters()) {
                distinctParameters.add(parameter.getType().resolve().describe());
            }
        });
        return distinctParameters.size();
    }

    @Override
    public MetricName getName() {
        return MetricName.COHESION_AMONG_METHODS;
    }

}
