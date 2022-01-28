package metricscalculator.productmetric.general;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing NOPV metric.
 * NOPV - number of private fields in a class
 */
public class NumberOfPrivateFieldsMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(FieldDeclaration.class).stream()
                .filter(this::isPrivate)
                .count();
    }

    private boolean isPrivate(FieldDeclaration fieldDeclaration) {
        return fieldDeclaration.getModifiers().contains(Modifier.privateModifier());
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_PRIVATE_FIELDS;
    }

}
