package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for computing ATFD metric.
 * ATFD - Access to Foreign Data.
 * Number of fields from unrelated classes that are accessed in this class (accessor methods or directly).
 * If the same attribute is used in multiple methods,
 * it is counted multiple times (one per method even if used several times inside one).
 */
public class AccessToForeignDataMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {

        Set<String> foreignAttributeUsages = new HashSet<>();

        List<String> classFieldNames = getAllClassFieldNames(classDeclaration);

        classDeclaration.findAll(FieldAccessExpr.class).stream()
                .filter(fieldAccessExpr -> isForeignFieldAccessed(fieldAccessExpr, classDeclaration, classFieldNames))
                .forEach(fieldAccessExpr -> foreignAttributeUsages.add(
                        fieldAccessExpr.resolve().asField().declaringType().getQualifiedName() + "#" + fieldAccessExpr.getNameAsString()
                ));

        List<String> classMethodNames = getAllClassMethodNames(classDeclaration);

        classDeclaration.findAll(MethodCallExpr.class).stream()
                .filter(methodCallExpr -> methodCallExpr.isMethodCallExpr() && methodCallExpr.getNameAsString().startsWith("get") &&
                        isForeignAccessorCalled(methodCallExpr, classDeclaration, classMethodNames))
                .forEach(me -> foreignAttributeUsages.add(me.resolve().getQualifiedSignature()));

        return foreignAttributeUsages.size();

    }

    @Override
    public MetricName getName() {
        return MetricName.ACCESS_TO_FOREIGN_DATA;
    }

}
