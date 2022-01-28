package metricscalculator.productmetric.qmood;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import domain.code.MetricName;
import metricscalculator.productmetric.ProductMetricClass;

/**
 * Class for computing DAM metric.
 * DAM (Data Access Metric) - ratio of the number of private (protected) attributes to the total number of attributes
 * declared in the class
 */
public class DataAccessMetric implements ProductMetricClass {

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        long allAttributesNumber = getAllClassFieldNames(classDeclaration).size();
        if (allAttributesNumber == 0) {
            return 0;
        }
        long privateAndProtectedAttributesNumber = getPrivateAndProtectedAttributesNumber(classDeclaration);
        return privateAndProtectedAttributesNumber / (double) allAttributesNumber;
    }

    private long getPrivateAndProtectedAttributesNumber(ClassOrInterfaceDeclaration classDeclaration) {
        return classDeclaration.findAll(FieldDeclaration.class).stream()
                .filter(this::hasPrivateOrProtectedAccessSpecifier)
                .count();
    }

    private boolean hasPrivateOrProtectedAccessSpecifier(FieldDeclaration fieldDeclaration) {
        return fieldDeclaration.getAccessSpecifier().equals(AccessSpecifier.PRIVATE) ||
                fieldDeclaration.getAccessSpecifier().equals(AccessSpecifier.PROTECTED);
    }

    @Override
    public MetricName getName() {
        return MetricName.DATA_ACCESS;
    }

}
