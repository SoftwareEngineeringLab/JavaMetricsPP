package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.resolution.types.ResolvedReferenceType;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.List;

/**
 * Class for computing DIT metric.
 * DIT - depth of inheritance tree (number of inheritance levels from object hierarchy top)
 */
public class DepthOfInheritanceTreeMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        List<ResolvedReferenceType> resolvedReferenceTypes = classDeclaration.resolve().getAncestors();
        return resolvedReferenceTypes.stream()
                .mapToInt(resolvedReferenceType -> getInheritanceDepth(resolvedReferenceType, 1))
                .max()
                .orElse(0);
    }

    private Integer getInheritanceDepth(ResolvedReferenceType resolvedReferenceType, Integer depth) {
        List<ResolvedReferenceType> ancestors = resolvedReferenceType.getDirectAncestors();
        if (ancestors.isEmpty()) {
            return depth;
        }
        return ancestors.stream()
                .mapToInt(ancestor -> getInheritanceDepth(ancestor, depth + 1)).max()
                .orElse(depth);
    }

    @Override
    public MetricName getName() {
        return MetricName.DEPTH_OF_INHERITANCE_TREE;
    }

}
