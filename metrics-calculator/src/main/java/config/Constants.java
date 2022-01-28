package config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String NEW_LINE_REGEX = "(?>\\r\\n|\\n|\\r)";
    public static final String JAVA_FILE_EXTENSION = ".java";

    public static String CSV_INPUT_FILE_PATH;
    public static String REPOSITORIES_DIRECTORY;
    public static String WORK_DIRECTORY;

    public static String CALLABLES_OUTPUT_FILE_PATH;
    public static String CLASSES_OUTPUT_FILE_PATH;
    public static String ERROR_FILE_PATH;


    //public static final String CSV_INPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\input.csv";
    //public static final String CSV_OUTPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\output.csv";
    //public static final String ERROR_TXT = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\error.txt";

}
