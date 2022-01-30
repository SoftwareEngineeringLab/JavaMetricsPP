package input.row;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@ToString
public class ProcessMetricsRow extends MetricsRow {

    private final String averageNumberOfAddedLines;
    private final String maxNumberOfAddedLines;
    private final String age;
    private final String averageTimeBetweenChanges;
    private final String numberOfBugFixes;
    private final String codeChurn;
    private final String meanCommitMessageLength;
    private final String numberOfRevisions;
    private final String numberOfCommitsWithoutMessage;
    private final String daysWithCommits;
    private final String averageNumberOfDeletedLines;
    private final String maxNumberOfDeletedLines;
    private final String meanAuthorCommits;
    private final String numberOfDistinctCommitters;
    private final String averageNumberOfModifiedLines;
    private final String maxNumberOfModifiedLines;
    private final String numberOfRefactorings;
    private final String authorFragmentation;
    private final String timePassedSinceTheLastChange;

    @Override
    public List<String> getMetrics() {
        return List.of(averageNumberOfAddedLines,
                maxNumberOfAddedLines,
                age,
                averageTimeBetweenChanges,
                numberOfBugFixes,
                codeChurn,
                meanCommitMessageLength,
                numberOfRevisions,
                numberOfCommitsWithoutMessage,
                daysWithCommits,
                averageNumberOfDeletedLines,
                maxNumberOfDeletedLines,
                meanAuthorCommits,
                numberOfDistinctCommitters,
                averageNumberOfModifiedLines,
                maxNumberOfModifiedLines,
                numberOfRefactorings,
                authorFragmentation,
                timePassedSinceTheLastChange);
    }

}
