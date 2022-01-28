package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.List;

/**
 * Class for computing RFC metric.
 * RFC - response for a class (the value of RFC is the sum of number of methods called
 * within the class method bodies and the number of class methods)
 */
public class ResponseForClassMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        List<String> classMethods = getClassMethods(classDeclaration);
        List<String> methodsCalled = getMethodsCalled(classDeclaration, classMethods);
        return classMethods.size() + methodsCalled.size();
    }

    private List<String> getClassMethods(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(MethodDeclaration.class).stream()
                .map(NodeWithSimpleName::getNameAsString)
                .toList();
    }

    private List<String> getMethodsCalled(ClassOrInterfaceDeclaration classDeclaration, List<String> classMethods) {
        return classDeclaration.findAll(MethodCallExpr.class).stream()
                .filter(methodCallExpr -> isCalled(methodCallExpr, classMethods))
                .map(NodeWithSimpleName::getNameAsString)
                .toList();
    }

    private boolean isCalled(MethodCallExpr methodCallExpr, List<String> classMethods) {
        return !classMethods.contains(methodCallExpr.getNameAsString());
    }

    @Override
    public MetricName getName() {
        return MetricName.RESPONSE_FOR_CLASS;
    }

}
