package metricscalculator.productmetric.general;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import config.Constants;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricCallable;

/**
 * Class for computing LOC metric for a method or as constructor.
 * LOC - lines of code
 */
public class LinesOfCodeInCallableMetric implements ProductMetricCallable {

    @Override
    public Number getValue(ConstructorDeclaration constructorDeclaration) {
        String body = constructorDeclaration.getBody().toString();
        return getCodeLinesNumber(body);
    }

    @Override
    public Number getValue(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getBody()
                .map(Node::toString)
                .map(this::getCodeLinesNumber)
                .orElse(0);
    }

    private int getCodeLinesNumber(String body) {
        return body.split(Constants.NEW_LINE_REGEX).length;
    }

    @Override
    public MetricName getName() {
        return MetricName.LINES_OF_CODE;
    }

}
