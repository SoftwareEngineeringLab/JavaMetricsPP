package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Max Number of Deleted Lines
 * Description: The maximum number of lines of code deleted with one commit from the source element.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class MaxNumberOfDeletedLinesMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .mapToInt(Commit::getDeletedLines)
                .max()
                .orElse(0);
    }

    @Override
    public MetricName getName() {
        return MetricName.MAX_NUMBER_OF_DELETED_LINES;
    }

}
