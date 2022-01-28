package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.Metric;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessMetricsCalculator {

    private static final List<ProcessMetric> PROCESS_METRICS = List.of(
            new AverageNumberOfAddedLinesMetric(),
            new MaxNumberOfAddedLinesMetric(),
            new AgeMetric(),
            new AverageTimeBetweenChangesMetric(),
            new NumberOfBugFixesMetric(),
            new CodeChurnMetric(),
            new MeanCommitMessageLengthMetric(),
            new NumberOfRevisionsMetric(),
            new NumberOfCommitsWithoutMessageMetric(),
            new DaysWithCommitsMetric(),
            new AverageNumberOfDeletedLinesMetric(),
            new MaxNumberOfDeletedLinesMetric(),
            new MeanAuthorCommitsMetric(),
            new NumberOfDistinctCommittersMetric(),
            new AverageNumberOfModifiedLinesMetric(),
            new MaxNumberOfModifiedLinesMetric(),
            new NumberOfRefactoringsMetric(),
            new AuthorFragmentationMetric(),
            new TimePassedSinceTheLastChangeMetric()
    );

    public static List<Metric> calculateAllMetrics(CodeSample codeSample) {
        if (codeSample.getCommits().isEmpty()) {
            return Collections.emptyList();
        }
        return PROCESS_METRICS.stream()
                .map(processMetric -> processMetric.compute(codeSample))
                .toList();
    }

}
