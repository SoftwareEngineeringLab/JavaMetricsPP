package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import config.Constants;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * MRD (Method Reference Density) - density of method references (Java 8 feature) in class in percents
 * MRD = NOMR_C * 100 / LOC_C = Number Of Method Reference * 100 / Lines of Code in Class
 */
public class MethodReferenceDensityMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        double methodReferencesNumber = classDeclaration.findAll(MethodReferenceExpr.class).size();
        double codeLinesNumber = classDeclaration.toString().split(Constants.NEW_LINE_REGEX).length;
        return methodReferencesNumber * 100 / codeLinesNumber;
    }

    @Override
    public MetricName getName() {
        return MetricName.METHOD_REFERENCE_DENSITY;
    }

}
