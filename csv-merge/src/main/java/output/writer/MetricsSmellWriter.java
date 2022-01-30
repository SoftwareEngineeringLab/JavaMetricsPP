package output.writer;

import input.row.MLCQRow;
import input.row.MetricsRow;
import input.row.Smell;
import org.apache.commons.csv.CSVPrinter;
import output.provider.MetricsSmellRowProvider;
import output.row.MetricsSmellRow;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class MetricsSmellWriter {

    public void writeCSV(String fileAbsolutePath,
                         Smell smell,
                         List<MLCQRow> mlcqRows,
                         List<MetricsRow> productMetricsRows,
                         List<MetricsRow> processMetricsRows) {
        List<String> samplesIds = getIds(processMetricsRows);
        List<MetricsSmellRow> metricsSmellRows = MetricsSmellRowProvider.from
                (samplesIds, smell, mlcqRows, productMetricsRows, processMetricsRows);
        writeCSV(fileAbsolutePath, metricsSmellRows);
    }

    private void writeCSV(String fileAbsolutePath,
                          List<MetricsSmellRow> metricsSmellRows) {
        try (FileWriter out = new FileWriter(fileAbsolutePath)) {
            CSVPrinter printer = getPrinter(out);
            for (MetricsSmellRow metricsSmellRow : metricsSmellRows) {
                List<String> record = metricsSmellRow.getInfoToWrite();
                printer.printRecord(record);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract List<String> getIds(List<MetricsRow> processMetricsRows);

    protected abstract CSVPrinter getPrinter(FileWriter out) throws IOException;

}
