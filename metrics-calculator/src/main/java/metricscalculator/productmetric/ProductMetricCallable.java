package metricscalculator.productmetric;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.Metric;
import domain.code.MetricName;
import domain.code.MetricType;

public interface ProductMetricCallable {

    default Metric compute(ConstructorDeclaration constructorDeclaration) {
        MetricName name = getName();
        Number value = getValue(constructorDeclaration);
        return Metric.builder()
                .name(name)
                .value(value)
                .type(MetricType.PRODUCT_METRIC)
                .build();
    }

    default Metric compute(MethodDeclaration methodDeclaration) {
        MetricName name = getName();
        Number value = getValue(methodDeclaration);
        return Metric.builder()
                .name(name)
                .value(value)
                .type(MetricType.PRODUCT_METRIC)
                .build();
    }

    MetricName getName();

    Number getValue(ConstructorDeclaration constructorDeclaration);

    Number getValue(MethodDeclaration methodDeclaration);

}
