package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;
import metricscalculator.productmetric.visitors.CycloVisitor;

/**
 * Class for computing WMC metric.
 * WMC - weighted methods per class (sum of cyclomatic complexities of all of its methods)
 * Methods of inner classes are counted as a methods of enclosing class!
 */
public class WeightedMethodsPerClassMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(MethodDeclaration.class).stream()
                .mapToInt(this::getMethodCyclomaticComplexity)
                .sum();
    }

    private int getMethodCyclomaticComplexity(MethodDeclaration methodDeclaration) {
        CycloVisitor cycloVisitor = new CycloVisitor();
        cycloVisitor.visit(methodDeclaration, null);
        return cycloVisitor.getCount();
    }

    @Override
    public MetricName getName() {
        return MetricName.WEIGHTED_METHODS_PER_CLASS;
    }

}
