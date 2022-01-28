package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;
import metricscalculator.productmetric.visitors.CycloVisitor;

import java.util.List;

/**
 * Class for computing WMCNAMM metric.
 * WMCNAMM - weighted methods per class without accessor and mutator methods
 * (sum of cyclomatic complexities of its methods without accessor and mutator methods)
 * Methods of inner classes are counted as a methods of enclosing class!
 */
public class WeightedMethodsPerClassWithoutAccessorAndMutatorMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        List<MethodDeclaration> methodsWithoutAccessorAndMutatorMethods = getMethodsWithoutAccessorAndMutatorMethods(classDeclaration);
        return methodsWithoutAccessorAndMutatorMethods.stream()
                .mapToInt(this::getMethodCyclomaticComplexity)
                .sum();
    }

    private List<MethodDeclaration> getMethodsWithoutAccessorAndMutatorMethods(ClassOrInterfaceDeclaration classDeclaration) {
        List<MethodDeclaration> methodsWithoutAccessorAndMutatorMethods = classDeclaration.findAll(MethodDeclaration.class);
        List<MethodDeclaration> mutators = getClassMutatorMethods(classDeclaration);
        List<MethodDeclaration> accessors = getClassAccessorMethods(classDeclaration);
        methodsWithoutAccessorAndMutatorMethods.removeAll(mutators);
        methodsWithoutAccessorAndMutatorMethods.removeAll(accessors);

        return methodsWithoutAccessorAndMutatorMethods;
    }

    private int getMethodCyclomaticComplexity(MethodDeclaration methodDeclaration) {
        CycloVisitor cycloVisitor = new CycloVisitor();
        cycloVisitor.visit(methodDeclaration, null);
        return cycloVisitor.getCount();
    }

    @Override
    public MetricName getName() {
        return MetricName.WEIGHTED_METHODS_PER_CLASS_WITHOUT_ACCESSOR_AND_MUTATOR_METHODS;
    }

}
