package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

import java.util.List;

/**
 * Name: Number of Bug Fixes
 * Description: The number of commits with 'bug' or 'fix' in the commit message.
 * Reference: Linus W. Dietz, Robin Lichtenthälery, Adam Tornhillz and Simon Harrer.
 * 2019. Code Process Metrics in University Programming Education. Software
 * Engineering
 */
public class NumberOfBugFixesMetric implements ProcessMetric {

    private static final List<String> WORDS = List.of("fix", "bug");

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .map(Commit::getMessage)
                .map(String::toLowerCase)
                .filter(this::containsWords)
                .count();
    }

    private boolean containsWords(String commitMessage) {
        return WORDS.stream()
                .anyMatch(commitMessage::contains);
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_BUG_FIXES;
    }

}
