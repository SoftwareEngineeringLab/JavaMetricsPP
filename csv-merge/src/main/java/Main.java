import input.provider.MLCQRowsProvider;
import input.provider.ProcessMetricsRowsProvider;
import input.provider.ProductMetricsCallableRowsProvider;
import input.provider.ProductMetricsClassRowsProvider;
import input.row.MLCQRow;
import input.row.MetricsRow;
import input.row.Smell;
import output.writer.MetricsSmellCallableWriter;
import output.writer.MetricsSmellClassWriter;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String MLCQ_FILE = "C:\\Users\\przem\\IdeaProjects\\ProcessMetrics\\MLCQ-dataset\\MLCQCodeSmellSamples.csv";
    private static final String PROCESS_METRICS_FILE = "C:\\Users\\przem\\IdeaProjects\\ProcessMetrics\\MLCQ-dataset\\process_metrics.csv";
    private static final String PRODUCT_METRICS_CLASS_FILE = "C:\\Users\\przem\\IdeaProjects\\ProcessMetrics\\MLCQ-dataset\\product_metrics_class.csv";
    private static final String PRODUCT_METRICS_CALLABLE_FILE = "C:\\Users\\przem\\IdeaProjects\\ProcessMetrics\\MLCQ-dataset\\product_metrics_callable.csv";

    public static final String DATA_CLASS_OUTPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\metrics_with_data_class_smell.csv";
    public static final String BLOB_OUTPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\metrics_with_blob_smell.csv";
    public static final String LONG_METHOD_OUTPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\metrics_with_long_method_smell.csv";
    public static final String FEATURE_ENVY_OUTPUT_FILE_PATH = "C:\\Users\\przem\\OneDrive\\Pulpit\\csvinput\\metrics_with_feature_envy_smell.csv";

    public static void main(String[] args) {

        List<MLCQRow> mlcqRows = MLCQRowsProvider.from(MLCQ_FILE);
        List<MetricsRow> processMetricsRows = new ArrayList<>(ProcessMetricsRowsProvider.from(PROCESS_METRICS_FILE));
        List<MetricsRow> productMetricsClassRows = new ArrayList<>(ProductMetricsClassRowsProvider.from(PRODUCT_METRICS_CLASS_FILE));
        List<MetricsRow> productMetricsCallableRows = new ArrayList<>(ProductMetricsCallableRowsProvider.from(PRODUCT_METRICS_CALLABLE_FILE));

        MetricsSmellClassWriter metricsSmellClassWriter = new MetricsSmellClassWriter();
        metricsSmellClassWriter.writeCSV(BLOB_OUTPUT_FILE_PATH,
                Smell.BLOB,
                mlcqRows,
                productMetricsClassRows,
                processMetricsRows);

        metricsSmellClassWriter.writeCSV(DATA_CLASS_OUTPUT_FILE_PATH,
                Smell.DATA_CLASS,
                mlcqRows,
                productMetricsClassRows,
                processMetricsRows);

        MetricsSmellCallableWriter metricsSmellCallableWriter = new MetricsSmellCallableWriter();
        metricsSmellCallableWriter.writeCSV(LONG_METHOD_OUTPUT_FILE_PATH,
                Smell.LONG_METHOD,
                mlcqRows,
                productMetricsCallableRows,
                processMetricsRows);

        metricsSmellCallableWriter.writeCSV(FEATURE_ENVY_OUTPUT_FILE_PATH,
                Smell.FEATURE_ENVY,
                mlcqRows,
                productMetricsCallableRows,
                processMetricsRows);

    }

}
