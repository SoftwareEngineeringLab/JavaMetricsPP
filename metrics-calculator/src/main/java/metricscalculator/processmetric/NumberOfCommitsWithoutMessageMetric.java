package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Number of Commits Without Message
 * Description: The number of previous modifications without any comment message.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class NumberOfCommitsWithoutMessageMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .map(Commit::getMessage)
                .filter(String::isEmpty)
                .count();
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_COMMITS_WITHOUT_MESSAGE;
    }

}
