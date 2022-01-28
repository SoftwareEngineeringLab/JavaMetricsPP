package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Max Number of Modified Lines
 * Description: The maximum number of modified lines (which were
 * added or removed) in a given Java class/method.
 * Reference: Lech Madeyski and Marian Jureczko. 2015. Which process metrics can significantly improve defect prediction models?
 * An empirical study. Software Qual J23, 393–422
 */
public class MaxNumberOfModifiedLinesMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .mapToInt(Commit::getModifiedLines)
                .max()
                .orElse(0);
    }

    @Override
    public MetricName getName() {
        return MetricName.MAX_NUMBER_OF_MODIFIED_LINES;
    }

}
