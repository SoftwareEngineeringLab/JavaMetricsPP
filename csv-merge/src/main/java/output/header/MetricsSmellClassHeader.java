package output.header;

public enum MetricsSmellClassHeader {

    //product metrics
    LambdaDensity,
    LinesOfCodeInClass,
    MethodReferenceDensity,
    NumberOfAccessorMethods,
    NumberOfLambdaExpressionsInClass,
    NumberOfMethodReferencesPerClass,
    NumberOfMethods,
    NumberOfMutatorMethods,
    NumberOfPublicFields,
    NumberOfPrivateFields,
    WeightedMethodsPerClass,
    WeightedMethodsPerClassWithoutAccessorAndMutatorMethods,
    WeightOfClass,
    NumberOfPublicMethods,
    ResponseForClass,
    NumberOfAnnotations,

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
