package metricscalculator.productmetric.martin;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing Ce metric.
 * Ce (Efferent coupling) - number of classes that the measured class is depended upon.
 */
public class EfferentCouplingMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return getEfferentCouplingClasses(classDeclaration).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.EFFERENT_COUPLING;
    }

}
