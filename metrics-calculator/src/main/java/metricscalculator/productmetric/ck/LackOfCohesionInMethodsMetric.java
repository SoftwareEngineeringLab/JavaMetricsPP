package metricscalculator.productmetric.ck;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.utils.Pair;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for computing LCOM metric.
 * LCOM - lack of cohesion in methods (the lack of cohesion in methods is calculated by
 * subtracting from the number of method pairs that do not share a
 * field access the number of method pairs that do)
 */
public class LackOfCohesionInMethodsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        List<String> classFieldNames = getAllClassFieldNames(classDeclaration);
        Map<String, List<String>> fieldUsageMap = new HashMap<>();

        classDeclaration.findAll(MethodDeclaration.class)
                .forEach(methodDeclaration -> {
                    List<String> fieldAccessed = methodDeclaration.findAll(FieldAccessExpr.class).stream()
                            .filter(fieldAccessExpr -> classFieldNames.contains(fieldAccessExpr.getNameAsString()))
                            .map(NodeWithSimpleName::getNameAsString)
                            .toList();

                    String methodName = methodDeclaration.getNameAsString();

                    if (fieldUsageMap.containsKey(methodName)) { // Inner classes with same names?
                        fieldUsageMap.get(methodName).addAll(fieldAccessed);
                    } else {
                        fieldUsageMap.put(methodName, fieldAccessed);
                    }
                });

        Set<Pair<String, String>> pairs = new HashSet<>();
        Set<Pair<String, String>> nonPairs = new HashSet<>();

        for (String method : fieldUsageMap.keySet()) {
            fieldUsageMap.forEach((k, v) -> {
                if (isPair(fieldUsageMap.get(method), v)) {
                    Pair<String, String> p = new Pair<>(method, k);
                    if (!containsPair(pairs, p)) {
                        pairs.add(p);
                    }
                } else {
                    Pair<String, String> p = new Pair<>(method, k);
                    if (!containsPair(nonPairs, p))
                        nonPairs.add(p);
                }

            });
        }

        return nonPairs.size() - pairs.size();
    }

    private boolean isPair(List<String> a, List<String> b) {
        for (String s : a) {
            if (b.contains(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsPair(Set<Pair<String, String>> set, Pair<String, String> pair) {
        return set.stream()
                .anyMatch(p -> (p.a.equals(pair.a) && p.b.equals(pair.b))
                        || (p.a.equals(pair.b) && p.b.equals(pair.a)));
    }

    @Override
    public MetricName getName() {
        return MetricName.LACK_OF_COHESION_IN_METHODS;
    }

}
