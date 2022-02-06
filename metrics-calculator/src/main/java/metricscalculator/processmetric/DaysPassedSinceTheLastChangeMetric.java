package metricscalculator.processmetric;

import domain.code.CodeSample;
import domain.code.MetricName;
import domain.git.Commit;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Name: Days Passed Since the Last Change
 * Description: Time passed in days since the last commit.
 * Reference: Péter Gyimesi. 2017. Automatic calculation of process metrics and their bug
 * prediction capabilities. Acta Cybernetica, 23(2), 537-559
 */
public class DaysPassedSinceTheLastChangeMetric implements ProcessMetric {

    @Override
    public Number getValue(CodeSample codeSample) {
        List<Commit> commits = codeSample.getCommits();
        LocalDate currentDate = codeSample.getRepository().getCurrentDate();
        Commit lastCommit = commits.get(0);
        LocalDate lastCommitDate = lastCommit.getDate();
        return ChronoUnit.DAYS.between(lastCommitDate, currentDate);
    }

    @Override
    public MetricName getName() {
        return MetricName.DAYS_PASSED_SINCE_THE_LAST_CHANGE;
    }

}
