package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

import java.util.List;

/**
 * Name: Code Churn
 * Description: The sum of lines added minus lines deleted
 * from the source element.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class CodeChurnMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        List<Commit> commits = codeSample.getCommits();
        int addedLinesSum = commits.stream()
                .mapToInt(Commit::getAddedLines)
                .sum();
        int deleteLinesSum = commits.stream()
                .mapToInt(Commit::getDeletedLines)
                .sum();
        return addedLinesSum - deleteLinesSum;
    }

    @Override
    public MetricName getName() {
        return MetricName.CODE_CHURN;
    }

}
