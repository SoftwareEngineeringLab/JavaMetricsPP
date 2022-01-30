package output.provider;

import com.google.common.primitives.Ints;
import input.row.MLCQRow;
import input.row.MetricsRow;
import input.row.Smell;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import output.row.MetricsSmellRow;
import output.utlis.Median;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetricsSmellRowProvider {

    public static List<MetricsSmellRow> from(List<String> samplesIds,
                                             Smell smell,
                                             List<MLCQRow> mlcqRows,
                                             List<MetricsRow> productMetricsRows,
                                             List<MetricsRow> processMetricsRows) {
        List<MetricsSmellRow> metricsSmellRows = new ArrayList<>();
        for (String sampleId : samplesIds) {
            List<String> metrics = getMetrics(sampleId, productMetricsRows, processMetricsRows);
            Integer severity = countSeverity(sampleId, smell, mlcqRows);
            if (metrics != null && severity != null) {
                metricsSmellRows.add(MetricsSmellRow.builder()
                        .severity(severity.toString())
                        .metrics(metrics)
                        .build());
            }
        }
        return metricsSmellRows;
    }

    private static List<String> getMetrics(String sampleId,
                                           List<MetricsRow> productMetricsRows,
                                           List<MetricsRow> processMetricsRows) {
        List<String> productMetrics = getMetrics(sampleId, productMetricsRows);
        List<String> processMetrics = getMetrics(sampleId, processMetricsRows);
        List<String> metrics = null;
        if (productMetrics != null && processMetrics != null) {
            metrics = new ArrayList<>();
            metrics.addAll(productMetrics);
            metrics.addAll(processMetrics);
        }
        return metrics;
    }

    private static List<String> getMetrics(String sampleId,
                                           List<MetricsRow> metricsRows) {
        return metricsRows.stream()
                .filter(metricsRow -> metricsRow.getSampleId().equals(sampleId))
                .findAny()
                .map(MetricsRow::getMetrics)
                .orElse(null);
    }

    private static Integer countSeverity(String sampleId,
                                         Smell smell,
                                         List<MLCQRow> mlcqRows) {
        List<Integer> severities = getSeverities(sampleId, smell, mlcqRows);
        if (severities.isEmpty()) {
            return null;
        } else {
            return Median.count(Ints.toArray(severities));
        }
    }

    private static List<Integer> getSeverities(String sampleId,
                                               Smell smell,
                                               List<MLCQRow> mlcqRows) {
        return mlcqRows.stream()
                .filter(mlcqRow -> mlcqRow.getSampleId().equals(sampleId))
                .filter(mlcqRow -> mlcqRow.getSmell() == smell)
                .map(MLCQRow::getSeverity)
                .toList();
    }

}
