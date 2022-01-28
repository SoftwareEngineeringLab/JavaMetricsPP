package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.Metric;
import domain.code.MetricName;
import domain.code.MetricType;

public interface ProcessMetric {

    default Metric compute(CodeSample codeSample) {
        MetricName name = getName();
        Number value = getValue(codeSample);
        return Metric.builder()
                .name(name)
                .value(value)
                .type(MetricType.PROCESS_METRIC)
                .build();
    }

    MetricName getName();

    Number getValue(CodeSample codeSample);

}
