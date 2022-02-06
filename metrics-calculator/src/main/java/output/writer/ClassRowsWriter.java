package output.writer;

import config.Constants;
import domain.code.CodeSample;
import domain.code.CodeSampleType;
import domain.code.MetricName;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import output.header.ClassHeader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ClassRowsWriter {

    private static CSVPrinter printer;

    public static void write(List<CodeSample> codeSamples) {
        try (FileWriter out = new FileWriter(Constants.CLASSES_OUTPUT_FILE_PATH)) {
            printer = getPrinter(out);
            List<CodeSample> classes = getClasses(codeSamples);
            classes.forEach(ClassRowsWriter::printCallable);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static List<CodeSample> getClasses(List<CodeSample> codeSamples) {
        return codeSamples.stream()
                .filter(codeSample -> codeSample.getType() == CodeSampleType.CLASS)
                .toList();
    }

    private static CSVPrinter getPrinter(FileWriter out) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(ClassHeader.class)
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
                    codeSample.getMetricValueAsString(MetricName.AVERAGE_NUMBER_OF_BETWEEN_CHANGES),
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

                    codeSample.getMetricValueAsString(MetricName.LAMBDA_DENSITY),
                    codeSample.getMetricValueAsString(MetricName.LINES_OF_CODE),
                    codeSample.getMetricValueAsString(MetricName.METHOD_REFERENCE_DENSITY),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_ACCESSOR_METHODS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_LAMBDA_EXPRESSIONS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_METHOD_REFERENCES),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_METHODS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_MUTATOR_METHODS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_PUBLIC_FIELDS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_PRIVATE_FIELDS),
                    codeSample.getMetricValueAsString(MetricName.WEIGHTED_METHODS_PER_CLASS),
                    codeSample.getMetricValueAsString(MetricName.WEIGHTED_METHODS_PER_CLASS_WITHOUT_ACCESSOR_AND_MUTATOR_METHODS),
                    codeSample.getMetricValueAsString(MetricName.WEIGHT_OF_CLASS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_PUBLIC_METHODS),
                    codeSample.getMetricValueAsString(MetricName.RESPONSE_FOR_CLASS),
                    codeSample.getMetricValueAsString(MetricName.NUMBER_OF_FIELD_ANNOTATIONS)
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
