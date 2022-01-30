package input.provider;

import input.header.ProcessMetricsHeader;
import input.mapper.ProcessMetricsRowMapper;
import input.row.ProcessMetricsRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessMetricsRowsProvider {

    public static List<ProcessMetricsRow> from(String csvFilePath) {
        List<ProcessMetricsRow> rows = new ArrayList<>();
        try (Reader in = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = getRecords(in);
            records.forEach(record -> rows.add(ProcessMetricsRowMapper.from(record)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    private static Iterable<CSVRecord> getRecords(Reader in) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(ProcessMetricsHeader.class)
                .withSkipHeaderRecord()
                .parse(in);
    }

}
