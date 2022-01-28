package main;

import config.Constants;
import service.MetricsCalculatorService;

import java.io.File;

public class Main2 {

    public static void main(String[] args) {

        Constants.CSV_INPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\input.csv";
        Constants.REPOSITORIES_DIRECTORY = System.getProperty("user.home") + File.separator + "java-metrics-source-repos";
        Constants.WORK_DIRECTORY = Constants.REPOSITORIES_DIRECTORY + File.separator + "work_dir";
        Constants.CALLABLES_OUTPUT_FILE_PATH = Constants.WORK_DIRECTORY + File.separator + "callables_metrics.xlsx";
        Constants.CLASSES_OUTPUT_FILE_PATH = Constants.WORK_DIRECTORY + File.separator + "class_metrics.xlsx";
        MetricsCalculatorService.calculateMetrics();

    }

}
