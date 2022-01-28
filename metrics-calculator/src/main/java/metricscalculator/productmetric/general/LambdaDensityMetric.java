package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.LambdaExpr;
import config.Constants;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * LD (Lambda Density) - density of lambda expressions (Java 8 feature) in class in percents
 * LD = NOL * 100 / LOC_C = Number of Lambdas * 100 / Lines of Code in Class
 */
public class LambdaDensityMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        double lambdaExpressionsNumber = classDeclaration.findAll(LambdaExpr.class).size();
        double codeLinesNumber = classDeclaration.toString().split(Constants.NEW_LINE_REGEX).length;
        return lambdaExpressionsNumber * 100 / codeLinesNumber;
    }

    @Override
    public MetricName getName() {
        return MetricName.LAMBDA_DENSITY;
    }

}
