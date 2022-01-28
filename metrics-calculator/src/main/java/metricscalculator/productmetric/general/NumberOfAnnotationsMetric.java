package metricscalculator.productmetric.general;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * NOA (Number of Annotations) - number of annotations inside a class computed for the fields
 */
public class NumberOfAnnotationsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(FieldDeclaration.class).stream()
                .mapToInt(this::getAnnotationsNumber)
                .sum();
    }

    private int getAnnotationsNumber(FieldDeclaration fieldDeclaration) {
        return fieldDeclaration.getAnnotations().size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_FIELD_ANNOTATIONS;
    }

}
