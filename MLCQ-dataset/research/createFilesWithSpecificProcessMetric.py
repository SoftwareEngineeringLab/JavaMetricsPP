import pandas as pd

columns = ['AverageNumberOfAddedLines',
           'MaxNumberOfAddedLines',
           'Age',
           'AverageTimeBetweenChanges',
           'NumberOfBugFixes',
           'CodeChurn',
           'MeanCommitMessageLength',
           'NumberOfRevisions',
           'NumberOfCommitsWithoutMessage',
           'DaysWithCommits',
           'AverageNumberOfDeletedLines',
           'MaxNumberOfDeletedLines',
           'MeanAuthorCommits',
           'NumberOfDistinctCommitters',
           'AverageNumberOfModifiedLines',
           'MaxNumberOfModifiedLines',
           'NumberOfRefactorings',
           'AuthorFragmentation',
           'TimePassedSinceTheLastChange']

data = pd.read_csv('all_metrics_with_feature_envy_smell.csv')
file_name = 'product_metrics_with_feature_envy_smell.csv'
for column in columns:
    data.pop(column)

data.to_csv(file_name, index=True, index_label='id')


for column in columns:
    file_name = 'product_metrics_'+column + '_with_feature_envy_smell.csv'
    columns_to_delete = columns.copy()
    columns_to_delete.remove(column)

    data = pd.read_csv('all_metrics_with_feature_envy_smell.csv')
    for column_to_delete in columns_to_delete:
        data.pop(column_to_delete)

    data.to_csv(file_name, index=True, index_label='id')
