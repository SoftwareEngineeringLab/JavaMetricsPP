package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.LambdaExpr;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * NOL_C - Number Of Lambda expressions (Java 8 feature) in Class
 */
public class NumberOfLambdaExpressionsInClassMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(LambdaExpr.class).size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_LAMBDA_EXPRESSIONS;
    }

}
