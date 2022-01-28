package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricCallable;

/**
 * NOMR_M - Number Of Method References (Java 8 feature) per method or constructor
 */
public class NumberOfMethodReferencesPerCallableMetric implements ProductMetricCallable {

    @Override
    public Number getValue(ConstructorDeclaration constructorDeclaration) {
        return constructorDeclaration.findAll(MethodReferenceExpr.class).size();
    }

    @Override
    public Number getValue(MethodDeclaration methodDeclaration) {
        return methodDeclaration.findAll(MethodReferenceExpr.class).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_METHOD_REFERENCES;
    }

}
