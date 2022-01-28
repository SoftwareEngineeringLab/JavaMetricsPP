package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * NOMR_C - Number Of Method References (Java 8 feature) per Class
 */
public class NumberOfMethodReferencesPerClassMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(MethodReferenceExpr.class).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_METHOD_REFERENCES;
    }

}


