package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Name: Average Number Of Days Between Changes
 * Description: The average number of days that passed between consecutive
 * modifications of the source element.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class AverageNumberOfDaysBetweenChangesMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        List<Commit> commits = codeSample.getCommits();
        long sumTimesBetweenChanges = 0;
        for (int i = 0; i < commits.size() - 1; i++) {
            int j = i + 1;
            LocalDate first = commits.get(i).getDate();
            LocalDate second = commits.get(j).getDate();
            sumTimesBetweenChanges += ChronoUnit.DAYS.between(second, first);
        }
        return sumTimesBetweenChanges / commits.size();
    }

    @Override
    public MetricName getName() {
        return MetricName.AVERAGE_NUMBER_OF_DAYS_BETWEEN_CHANGES;
    }

}
