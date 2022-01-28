package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Mean Commit Message Length
 * Description: The mean number of characters in commit messages.
 * Reference: Linus W. Dietz, Robin Lichtenthälery, Adam Tornhillz and Simon Harrer.
 * 2019. Code Process Metrics in University Programming Education. Software
 * Engineering
 */
public class MeanCommitMessageLengthMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .map(Commit::getMessage)
                .mapToInt(String::length)
                .average()
                .orElse(0);
    }

    @Override
    public MetricName getName() {
        return MetricName.MEAN_COMMIT_MESSAGE_LENGTH;
    }

}
