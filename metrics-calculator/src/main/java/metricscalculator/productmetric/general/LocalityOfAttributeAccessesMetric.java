package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class for computing LAA metric.
 * LAA - Locality of Attribute Accesses.
 * Number of own attributes used divided by number of all attributes used.
 */
public class LocalityOfAttributeAccessesMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        List<String> classAccessorMethods = getAllClassMethodNames(classDeclaration)
                .stream()
                .filter(methodName -> methodName.startsWith("get"))
                .toList();
        List<String> classFieldNames = getAllClassFieldNames(classDeclaration);

        AtomicInteger ownAttributeUsages = new AtomicInteger(0);
        AtomicInteger allAttributeUsages = new AtomicInteger(0);

        classDeclaration.findAll(FieldAccessExpr.class).forEach(fieldAccessExpr -> {
            allAttributeUsages.incrementAndGet();
            if (!isForeignFieldAccessed(fieldAccessExpr, classDeclaration, classFieldNames)) {
                ownAttributeUsages.incrementAndGet();
            }
        });

        classDeclaration.findAll(MethodCallExpr.class).stream()
                .filter(methodCallExpr -> methodCallExpr.isMethodCallExpr() && methodCallExpr.getNameAsString().startsWith("get"))
                .forEach(methodCallExpr -> {
                    allAttributeUsages.incrementAndGet();
                    if (!isForeignAccessorCalled(methodCallExpr, classDeclaration, classAccessorMethods)) {
                        ownAttributeUsages.incrementAndGet();
                    }
                });

        return (double) ownAttributeUsages.get() / (double) allAttributeUsages.get();
    }

    @Override
    public MetricName getName() {
        return MetricName.LOCALITY_OF_ATTRIBUTE_ACCESSES;
    }

}
