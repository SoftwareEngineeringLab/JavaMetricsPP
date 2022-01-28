package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;
import domain.git.Developer;

/**
 * Name: Mean Author Commits
 * Description: The mean number of commits per author.
 * Reference: Linus W. Dietz, Robin Lichtenthälery, Adam Tornhillz and Simon Harrer.
 * 2019. Code Process Metrics in University Programming Education. Software
 * Engineering
 */
public class MeanAuthorCommitsMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        return codeSample.getCommits().stream()
                .map(Commit::getDeveloper)
                .distinct()
                .mapToInt(Developer::getCommits)
                .average()
                .orElse(0);
    }

    @Override
    public MetricName getName() {
        return MetricName.MEAN_AUTHOR_COMMITS;
    }

}
