package input.provider;

import input.header.ProductMetricsClassHeader;
import input.mapper.ProductMetricsClassRowMapper;
import input.row.ProductMetricsClassRow;
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
public class ProductMetricsClassRowsProvider {

    public static List<ProductMetricsClassRow> from(String csvFilePath) {
        List<ProductMetricsClassRow> rows = new ArrayList<>();
        try (Reader in = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = getRecords(in);
            records.forEach(record -> rows.add(ProductMetricsClassRowMapper.from(record)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    private static Iterable<CSVRecord> getRecords(Reader in) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(ProductMetricsClassHeader.class)
                .withSkipHeaderRecord()
                .parse(in);
    }

}
