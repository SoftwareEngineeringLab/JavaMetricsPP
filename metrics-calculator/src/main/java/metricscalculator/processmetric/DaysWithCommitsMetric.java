package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

/**
 * Name: Days With Commits
 * Description: The number of days with at least one commit in the assignment period.
 * Reference: Linus W. Dietz, Robin Lichtenthälery, Adam Tornhillz and Simon Harrer.
 * 2019. Code Process Metrics in University Programming Education. Software
 * Engineering
 */
public class DaysWithCommitsMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .map(Commit::getDate)
                .distinct()
                .count();
    }

    @Override
    public MetricName getName() {
        return MetricName.DAYS_WITH_COMMITS;
    }

}
