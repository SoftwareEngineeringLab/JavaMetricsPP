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
 * Class for computing FDP metric.
 * FDP - Foreign Data Providers.
 * Number of unrelated classes, which fields are accessed in this class (accessor methods or directly).
 * Java standard library classes are counted in as well, e. g. System.out is taken as a foreign data access.
 */
public class ForeignDataProvidersMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        Set<String> foreignProviders = new HashSet<>();

        List<String> classFieldNames = getAllClassFieldNames(classDeclaration);

        classDeclaration.findAll(FieldAccessExpr.class).stream()
                .filter(fieldAccessExpr -> isForeignFieldAccessed(fieldAccessExpr, classDeclaration, classFieldNames))
                .forEach(fieldAccessExpr -> foreignProviders.add(fieldAccessExpr.resolve().asField().declaringType().getQualifiedName()));

        List<String> classMethodNames = getAllClassMethodNames(classDeclaration);

        classDeclaration.findAll(MethodCallExpr.class).stream()
                .filter(methodCallExpr -> methodCallExpr.isMethodCallExpr() && methodCallExpr.getNameAsString().startsWith("get") &&
                        isForeignAccessorCalled(methodCallExpr, classDeclaration, classMethodNames))
                .forEach(me -> foreignProviders.add(me.resolve().declaringType().getQualifiedName()));

        return foreignProviders.size();
    }

    @Override
    public MetricName getName() {
        return MetricName.FOREIGN_DATA_PROVIDERS;
    }

}
