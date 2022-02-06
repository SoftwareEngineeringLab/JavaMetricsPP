package output.writer;

import config.Constants;
import domain.code.CodeSample;
import domain.code.CodeSampleType;
import domain.code.MetricName;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import output.header.CallableHeader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CallableRowsWriter {

    private static CSVPrinter printer;

    public static void write(List<CodeSample> codeSamples) {
        try (FileWriter out = new FileWriter(Constants.CALLABLES_OUTPUT_FILE_PATH)) {
            printer = getPrinter(out);
            List<CodeSample> callables = getCallables(codeSamples);
            callables.forEach(CallableRowsWriter::printCallable);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<CodeSample> getCallables(List<CodeSample> codeSamples) {
        return codeSamples.stream()
                .filter(codeSample -> codeSample.getType() == CodeSampleType.CONSTRUCTOR || codeSample.getType() == CodeSampleType.METHOD)
                .toList();
    }

    private static CSVPrinter getPrinter(FileWriter out) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(CallableHeader.class)
                .print(out);
    }

    private static void printCallable(CodeSample codeSample) {
        try {
            printer.printRecord(
                    codeSample.getId(),
                    codeSample.getType(),

                    codeSample.getMetricValueAsString(MetricName.AVERAGE_NUMBER_OF_ADDED_LINES),
                    codeSample.getMetricValueAsString(MetricName.MAX_NUMBER_OF_ADDED_LINES),
                    codeSample.getMetricValueAsString(MetricName.AGE_IN_DAYS),
                    codeSample.getMetricValueAsString(MetricName.AVERAGE_NUMBER_OF_DAYS_BETWEEN_CHANGES),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_BUG_FIXES),
                    codeSample.getMetricValueAsString(MetricName.CODE_CHURN),
                    codeSample.getMetricValueAsString(MetricName.MEAN_COMMIT_MESSAGE_LENGTH),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_REVISIONS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_COMMITS_WITHOUT_MESSAGE),
                    codeSample.getMetricValueAsString(MetricName.DAYS_WITH_COMMITS),
                    codeSample.getMetricValueAsString(MetricName.AVERAGE_NUMBER_OF_DELETED_LINES),
                    codeSample.getMetricValueAsString(MetricName.MAX_NUMBER_OF_DELETED_LINES),
                    codeSample.getMetricValueAsString(MetricName.MEAN_AUTHOR_COMMITS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_DISTINCT_COMMITTERS),
                    codeSample.getMetricValueAsString(MetricName.AVERAGE_NUMBER_OF_MODIFIED_LINES),
                    codeSample.getMetricValueAsString(MetricName.MAX_NUMBER_OF_MODIFIED_LINES),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_REFACTORINGS),
                    codeSample.getMetricValueAsString(MetricName.AUTHOR_FRAGMENTATION),
                    codeSample.getMetricValueAsString(MetricName.DAYS_PASSED_SINCE_THE_LAST_CHANGE),

                    codeSample.getMetricValueAsString(MetricName.MCCABE_CYCLOMATIC_COMPLEXITY),
                    codeSample.getMetricValueAsString(MetricName.LINES_OF_CODE),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_LAMBDA_EXPRESSIONS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_METHOD_REFERENCES)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
