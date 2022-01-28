library(mlr3)
library(mlr3learners)
library(caret)
library(e1071)

data_files <- c("product_metrics_Age_with_long_method_smell",
                "product_metrics_AuthorFragmentation_with_long_method_smell",
                "product_metrics_AverageNumberOfAddedLines_with_long_method_smell",
                "product_metrics_AverageNumberOfDeletedLines_with_long_method_smell",
                "product_metrics_AverageNumberOfModifiedLines_with_long_method_smell",
                "product_metrics_AverageTimeBetweenChanges_with_long_method_smell",
                "product_metrics_CodeChurn_with_long_method_smell",
                "product_metrics_DaysWithCommits_with_long_method_smell",
                "product_metrics_MaxNumberOfAddedLines_with_long_method_smell",
                "product_metrics_MaxNumberOfDeletedLines_with_long_method_smell",
                "product_metrics_MaxNumberOfModifiedLines_with_long_method_smell",
                "product_metrics_MeanAuthorCommits_with_long_method_smell",
                "product_metrics_MeanCommitMessageLength_with_long_method_smell",
                "product_metrics_NumberOfBugFixes_with_long_method_smell",
                "product_metrics_NumberOfCommitsWithoutMessage_with_long_method_smell",
                "product_metrics_NumberOfDistinctCommitters_with_long_method_smell",
                "product_metrics_NumberOfRefactorings_with_long_method_smell",
                "product_metrics_NumberOfRevisions_with_long_method_smell",
                "product_metrics_TimePassedSinceTheLastChange_with_long_method_smell",
                "product_metrics_with_long_method_smell")

for(data_file in data_files){
  input_file_path=paste('C:/Users/przem/OneDrive/Pulpit/badania/long_method_smell/input/',data_file,'.csv',sep="")
  
  df <- read.table(input_file_path, header = TRUE, sep=",")
  df$Severity <- factor(df$Severity)
  
  mlr_learners$get("classif.kknn")
  knn = lrn("classif.kknn")
  
  testTask <- TaskClassif$new(id = "id", backend = df, target = "Severity")
  
  kFold = rsmp("repeated_cv", repeats=50, folds=10)
  kFold$instantiate(testTask)
  
  kFoldCV <- resample(learner = knn, task = testTask, resampling = kFold)
  
  pred = kFoldCV$prediction()
  
  output_file_path=paste('results_',data_file,'.txt',sep="")
  
  sink(output_file_path)
  print(confusionMatrix(pred$confusion,mode="everything"))
  sink()
  
  rm(kFold, kFoldCV, knn, pred, testTask, input_file_path, output_file_path)
  
}

rm(df, data_file, data_files)


