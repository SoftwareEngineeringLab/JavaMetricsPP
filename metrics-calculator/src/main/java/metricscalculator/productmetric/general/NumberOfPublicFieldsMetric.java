package metricscalculator.productmetric.general;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NOPA metric.
 * NOPA - number of public fields in a class
 */
public class NumberOfPublicFieldsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(FieldDeclaration.class)
                .stream()
                .filter(this::isPublic)
                .count();
    }

    private boolean isPublic(FieldDeclaration fieldDeclaration) {
        return fieldDeclaration.getModifiers().contains(Modifier.publicModifier());
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_PUBLIC_FIELDS;
    }

}
