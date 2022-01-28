package input;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import mapper.RowMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CSVReader {

    public static List<Row> readCSV(String fileName) {
        List<Row> rows = new ArrayList<>();
        try (Reader in = new FileReader(fileName)) {
            Iterable<CSVRecord> records = getRecords(in);
            records.forEach(record -> rows.add(RowMapper.from(record)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return getRowsWithoutDuplicates(rows);
    }

    private static Iterable<CSVRecord> getRecords(Reader in) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(RowHeader.class)
                .withSkipHeaderRecord()
                .withDelimiter(';')
                .parse(in);
    }

    private static List<Row> getRowsWithoutDuplicates(List<Row> rows) {
        return rows.stream()
                .distinct()
                .toList();
    }

}
