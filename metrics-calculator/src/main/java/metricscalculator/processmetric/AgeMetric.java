package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Name: Age
 * Description: The age of source element in days.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class AgeMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        LocalDate currentDate = codeSample.getRepository().getCurrentDate();
        List<Commit> commits = codeSample.getCommits();
        Commit creationCommit = commits.get(commits.size() - 1);
        LocalDate creationDate = creationCommit.getDate();
        return ChronoUnit.DAYS.between(creationDate, currentDate);
    }

    @Override
    public MetricName getName() {
        return MetricName.AGE;
    }

}
