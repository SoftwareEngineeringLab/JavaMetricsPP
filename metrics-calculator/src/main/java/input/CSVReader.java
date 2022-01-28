package input;

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
public class CSVReader {

    public static List<CSVInputRow> getRows(String csvFilePath) {
        List<CSVInputRow> rows = new ArrayList<>();
        try (Reader in = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = getRecords(in);
            for (CSVRecord record : records) {
                CSVInputRow csvInputRow = CSVInputRowMapper.from(record);
                rows.add(csvInputRow);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    private static Iterable<CSVRecord> getRecords(Reader in) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(CSVInputHeader.class)
                .withSkipHeaderRecord()
                .parse(in);
    }

}
