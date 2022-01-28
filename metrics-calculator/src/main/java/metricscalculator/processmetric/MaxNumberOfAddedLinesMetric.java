package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Max Number of Added Lines
 * Description: The maximum number of lines of code added with one commit to the source element.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class MaxNumberOfAddedLinesMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .mapToInt(Commit::getAddedLines)
                .max()
                .orElse(0);
    }

    @Override
    public MetricName getName() {
        return MetricName.MAX_NUMBER_OF_ADDED_LINES;
    }

}
