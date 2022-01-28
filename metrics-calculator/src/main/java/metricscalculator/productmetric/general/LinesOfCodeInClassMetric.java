package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import config.Constants;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing LOC metric for a class.
 * LOC - lines of code
 * Code of inner classes are counted as a code of enclosing class! No distinct entity for an inner class.
 */
public class LinesOfCodeInClassMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.toString().split(Constants.NEW_LINE_REGEX).length;
    }

    @Override
    public MetricName getName() {
        return MetricName.LINES_OF_CODE;
    }

}
