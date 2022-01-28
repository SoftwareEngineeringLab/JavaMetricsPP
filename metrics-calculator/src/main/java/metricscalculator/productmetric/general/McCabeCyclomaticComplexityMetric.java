package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricCallable;
import metricscalculator.productmetric.visitors.CycloVisitor;

/**
 * Class for computing CYCLO metric in a method or a constructor.
 * CYCLO - McCabe cyclomatic complexity (number of possible disjunctive paths of program execution)
 * Computed as number of: ifs, cases, catches, fors, for-eaches and whiles.
 */
public class McCabeCyclomaticComplexityMetric implements ProductMetricCallable {

    @Override
    public Number getValue(ConstructorDeclaration constructorDeclaration) {
        CycloVisitor cycloVisitor = new CycloVisitor();
        cycloVisitor.visit(constructorDeclaration, null);
        return cycloVisitor.getCount();
    }

    @Override
    public Number getValue(MethodDeclaration methodDeclaration) {
        CycloVisitor cycloVisitor = new CycloVisitor();
        cycloVisitor.visit(methodDeclaration, null);
        return cycloVisitor.getCount();
    }

    @Override
    public MetricName getName() {
        return MetricName.MCCABE_CYCLOMATIC_COMPLEXITY;
    }

}
