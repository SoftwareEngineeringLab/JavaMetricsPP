package output.header;

public enum MetricsSmellCallableHeader {

    //product metrics
    McCabeCyclomaticComplexity,
    LinesOfCodeInCallable,
    NumberOfLambdaExpressionsInCallable,
    NumberOfMethodReferencesPerCallable,

    //process metrics
    AverageNumberOfAddedLines,
    MaxNumberOfAddedLines,
    Age,
    AverageTimeBetweenChanges,
    NumberOfBugFixes,
    CodeChurn,
    MeanCommitMessageLength,
    NumberOfRevisions,
    NumberOfCommitsWithoutMessage,
    DaysWithCommits,
    AverageNumberOfDeletedLines,
    MaxNumberOfDeletedLines,
    MeanAuthorCommits,
    NumberOfDistinctCommitters,
    AverageNumberOfModifiedLines,
    MaxNumberOfModifiedLines,
    NumberOfRefactorings,
    AuthorFragmentation,
    TimePassedSinceTheLastChange,

    //smell severity
    Severity

}
