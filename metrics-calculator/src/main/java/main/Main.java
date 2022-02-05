package main;

import config.Constants;
import org.apache.commons.cli.*;
import service.MetricsCalculatorService;

import java.io.File;

public class Main {

    private static final String CSV_OPTION = "csv";
    private static final String GIT_SOURCES_OUTPUT_DIRECTORY_OPTION = "gitsources";

    public static void main(String[] args) {

        Options options = prepareOptions();
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine line = parser.parse(options, args);
            Constants.CSV_INPUT_FILE_PATH = line.getOptionValue(CSV_OPTION);
            Constants.REPOSITORIES_DIRECTORY = line.getOptionValue(GIT_SOURCES_OUTPUT_DIRECTORY_OPTION);
            Constants.WORK_DIRECTORY = Constants.REPOSITORIES_DIRECTORY + File.separator + "work_dir";
            Constants.CALLABLES_OUTPUT_FILE_PATH = Constants.WORK_DIRECTORY + File.separator + "callables_metrics.xlsx";
            Constants.CLASSES_OUTPUT_FILE_PATH = Constants.WORK_DIRECTORY + File.separator + "class_metrics.xlsx";
            MetricsCalculatorService.calculateMetrics();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static Options prepareOptions() {
        Options options = new Options();

        Option csvOption = Option.builder(CSV_OPTION)
                .argName(CSV_OPTION)
                .desc("csv input file")
                .required()
                .hasArg()
                .build();

        Option gitSourcesOutputDirectoryOption = Option.builder(GIT_SOURCES_OUTPUT_DIRECTORY_OPTION)
                .argName(GIT_SOURCES_OUTPUT_DIRECTORY_OPTION)
                .desc("output directory with sources downloaded based on csv file [-csv flag]")
                .hasArg()
                .build();

        options.addOption(csvOption);
        options.addOption(gitSourcesOutputDirectoryOption);
        return options;
    }

}
