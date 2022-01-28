package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;

/**
 * Name: Number of Revisions
 * Description: The metric represents the number of revisions of a given
 * Java class/method during development of the investigated
 * release of a software system.
 * Reference: Lech Madeyski and Marian Jureczko. 2015. Which process metrics can significantly improve defect prediction models?
 * An empirical study. Software Qual J23, 393–422
 */
public class NumberOfRevisionsMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().size();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_REVISIONS;
    }

}
