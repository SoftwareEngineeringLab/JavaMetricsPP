package input.mapper;

import input.header.ProcessMetricsHeader;
import input.row.CodeSampleType;
import input.row.ProcessMetricsRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessMetricsRowMapper {

    public static ProcessMetricsRow from(CSVRecord record) {
        String sampleId = record.get(ProcessMetricsHeader.SampleId);
        String sampleType = record.get(ProcessMetricsHeader.SampleType);
        String averageNumberOfAddedLines = record.get(ProcessMetricsHeader.AverageNumberOfAddedLines);
        String maxNumberOfAddedLines = record.get(ProcessMetricsHeader.MaxNumberOfAddedLines);
        String age = record.get(ProcessMetricsHeader.Age);
        String averageTimeBetweenChanges = record.get(ProcessMetricsHeader.AverageTimeBetweenChanges);
        String numberOfBugFixes = record.get(ProcessMetricsHeader.NumberOfBugFixes);
        String codeChurn = record.get(ProcessMetricsHeader.CodeChurn);
        String meanCommitMessageLength = record.get(ProcessMetricsHeader.MeanCommitMessageLength);
        String numberOfRevisions = record.get(ProcessMetricsHeader.NumberOfRevisions);
        String numberOfCommitsWithoutMessage = record.get(ProcessMetricsHeader.NumberOfCommitsWithoutMessage);
        String daysWithCommits = record.get(ProcessMetricsHeader.DaysWithCommits);
        String averageNumberOfDeletedLines = record.get(ProcessMetricsHeader.AverageNumberOfDeletedLines);
        String maxNumberOfDeletedLines = record.get(ProcessMetricsHeader.MaxNumberOfDeletedLines);
        String meanAuthorCommits = record.get(ProcessMetricsHeader.MeanAuthorCommits);
        String numberOfDistinctCommitters = record.get(ProcessMetricsHeader.NumberOfDistinctCommitters);
        String averageNumberOfModifiedLines = record.get(ProcessMetricsHeader.AverageNumberOfModifiedLines);
        String maxNumberOfModifiedLines = record.get(ProcessMetricsHeader.MaxNumberOfModifiedLines);
        String numberOfRefactorings = record.get(ProcessMetricsHeader.NumberOfRefactorings);
        String authorFragmentation = record.get(ProcessMetricsHeader.AuthorFragmentation);
        String timePassedSinceTheLastChange = record.get(ProcessMetricsHeader.TimePassedSinceTheLastChange);

        return ProcessMetricsRow.builder()
                .sampleId(sampleId)
                .codeSampleType(CodeSampleType.valueOf(sampleType))
                .averageNumberOfAddedLines(averageNumberOfAddedLines)
                .maxNumberOfAddedLines(maxNumberOfAddedLines)
                .age(age)
                .averageTimeBetweenChanges(averageTimeBetweenChanges)
                .numberOfBugFixes(numberOfBugFixes)
                .codeChurn(codeChurn)
                .meanCommitMessageLength(meanCommitMessageLength)
                .numberOfRevisions(numberOfRevisions)
                .numberOfCommitsWithoutMessage(numberOfCommitsWithoutMessage)
                .daysWithCommits(daysWithCommits)
                .averageNumberOfDeletedLines(averageNumberOfDeletedLines)
                .maxNumberOfDeletedLines(maxNumberOfDeletedLines)
                .meanAuthorCommits(meanAuthorCommits)
                .numberOfDistinctCommitters(numberOfDistinctCommitters)
                .averageNumberOfModifiedLines(averageNumberOfModifiedLines)
                .maxNumberOfModifiedLines(maxNumberOfModifiedLines)
                .numberOfRefactorings(numberOfRefactorings)
                .authorFragmentation(authorFragmentation)
                .timePassedSinceTheLastChange(timePassedSinceTheLastChange)
                .build();
    }

}
