package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Number of Distinct Committers
 * Description: The metric counts the number of distinct authors, usually developers,
 * who committed their changes in a given Java class/method during the development
 * of the investigated release of a software system.
 * Reference: Lech Madeyski and Marian Jureczko. 2015. Which process metrics can significantly improve defect prediction models?
 * An empirical study. Software Qual J23, 393–422
 */
public class NumberOfDistinctCommittersMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .map(Commit::getDeveloper)
                .distinct()
                .count();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_DISTINCT_COMMITTERS;
    }

}
