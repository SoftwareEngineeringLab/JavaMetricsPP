package metricscalculator.productmetric.qmood;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import domain.code.MetricName;
import exception.JavaParserNotConfiguredException;
import metricscalculator.productmetric.ProductMetricClass;

import java.io.IOException;
import java.util.List;

/**
 * Class for computing MOA metric.
 * MOA (Measure of Aggregation) - metric is a count of the number of class fields whose types are user defined classes
 */
public class MeasureOfAggregationMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        List<FieldDeclaration> fields = classDeclaration.getFields();
        return fields.stream().filter(fieldDeclaration -> {
            try {
                return isUserDefined(fieldDeclaration.getElementType().toString(), fieldDeclaration.resolve().getType().describe());
            } catch (ParseException | IOException | JavaParserNotConfiguredException ex) {
                return false;
            }
        }).count();
    }

    private boolean isUserDefined(String fieldTypeName,
                                  String fieldDescribe) throws IOException, ParseException, JavaParserNotConfiguredException {
        List<String> classFilesWithFieldClass = getClassFilesContaining(fieldTypeName);

        for (String fileName : classFilesWithFieldClass) {
            CompilationUnit cu = parseFile(fileName);
            boolean found = cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                    .anyMatch(c -> fieldDescribe.equals(c.getFullyQualifiedName().orElse(null)));
            if (found) {
                return true;
            }
        }
        return false;
    }

    @Override
    public MetricName getName() {
        return MetricName.MEASURE_OF_AGGREGATION;
    }

}
