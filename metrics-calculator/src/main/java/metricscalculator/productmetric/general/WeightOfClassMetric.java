package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing WOC metric.
 * WOC - weight of the class (number of functional (non-accessor and non-mutator) methods divided by number of all methods)
 * Methods of inner classes are counted as a methods of enclosing class!
 */
public class WeightOfClassMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        double methodsNumbers = classDeclaration.findAll(MethodDeclaration.class).size();
        if (methodsNumbers == 0) {
            return 0;
        }
        double mutatorsNumbers = getClassMutatorMethods(classDeclaration).size();
        double accessorsNumbers = getClassAccessorMethods(classDeclaration).size();
        return (methodsNumbers - mutatorsNumbers - accessorsNumbers) / methodsNumbers;
    }

    @Override
    public MetricName getName() {
        return MetricName.WEIGHT_OF_CLASS;
    }

}
