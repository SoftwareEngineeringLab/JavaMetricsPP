package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricCallable;
import metricscalculator.productmetric.visitors.LambdaVisitor;

/**
 * NOL_M - Number Of Lambda expressions (Java 8 feature) in a method or a constructor
 */
public class NumberOfLambdaExpressionsInCallableMetric implements ProductMetricCallable {

    @Override
    public Number getValue(ConstructorDeclaration constructorDeclaration) {
        LambdaVisitor lambdaVisitor = new LambdaVisitor();
        lambdaVisitor.visit(constructorDeclaration, null);
        return lambdaVisitor.getCount();
    }

    @Override
    public Number getValue(MethodDeclaration methodDeclaration) {
        LambdaVisitor lambdaVisitor = new LambdaVisitor();
        lambdaVisitor.visit(methodDeclaration, null);
        return lambdaVisitor.getCount();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_LAMBDA_EXPRESSIONS;
    }

}
