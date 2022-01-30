package input.provider;

import input.header.ProductMetricsCallableHeader;
import input.mapper.ProductMetricsCallableRowMapper;
import input.row.ProductMetricsCallableRow;
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
public class ProductMetricsCallableRowsProvider {

    public static List<ProductMetricsCallableRow> from(String csvFilePath) {
        List<ProductMetricsCallableRow> rows = new ArrayList<>();
        try (Reader in = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = getRecords(in);
            records.forEach(record -> rows.add(ProductMetricsCallableRowMapper.from(record)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    private static Iterable<CSVRecord> getRecords(Reader in) throws IOException {
        return CSVFormat.DEFAULT
                .withHeader(ProductMetricsCallableHeader.class)
                .withSkipHeaderRecord()
                .parse(in);
    }

}
