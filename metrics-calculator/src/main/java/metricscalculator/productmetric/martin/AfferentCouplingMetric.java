package metricscalculator.productmetric.martin;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing Ca metric.
 * Ca (Afferent coupling) - number of classes that depend upon the measured class
 */
public class AfferentCouplingMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return getAfferentCouplingClasses(classDeclaration).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.AFFERENT_COUPLING;
    }

}
