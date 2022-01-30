package output.writer;

import input.row.MetricsRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import output.header.MetricsSmellCallableHeader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MetricsSmellCallableWriter extends MetricsSmellWriter {

    @Override
    protected List<String> getIds(List<MetricsRow> processMetricsRows) {
        return processMetricsRows.stream()
                .filter(MetricsRow::isCallable)
                .map(MetricsRow::getSampleId)
                .toList();
    }

    @Override
    protected CSVPrinter getPrinter(FileWriter out) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(MetricsSmellCallableHeader.class)
                .print(out);
    }

}
