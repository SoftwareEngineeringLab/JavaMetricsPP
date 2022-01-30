package input.provider;

import input.header.MLCQHeader;
import input.mapper.MLCQRowMapper;
import input.row.MLCQRow;
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
public class MLCQRowsProvider {

    public static List<MLCQRow> from(String csvFilePath) {
        List<MLCQRow> rows = new ArrayList<>();
        try (Reader in = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = getRecords(in);
            records.forEach(record -> rows.add(MLCQRowMapper.from(record)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    private static Iterable<CSVRecord> getRecords(Reader in) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(MLCQHeader.class)
                .withSkipHeaderRecord()
                .withDelimiter(';')
                .parse(in);
    }

}
