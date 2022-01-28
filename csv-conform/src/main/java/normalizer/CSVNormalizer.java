package normalizer;

import input.Row;
import mapper.ConformedRowMapper;
import output.ConformedRow;

import java.util.List;
import java.util.logging.Logger;

public class CSVNormalizer {

    private static final Logger LOGGER = Logger.getLogger(CSVNormalizer.class.getName());

    public static List<Row> cleanseCSV(List<Row> rows) {
        return rows.stream()
                .filter(Row::isCorrect)
                .map(CSVNormalizer::getRowWithCorrectCodeName)
                .toList();
    }

    private static Row getRowWithCorrectCodeName(Row row) {
        String codeName = row.getCodeName();
        if (isUsedGenericType(codeName)) {
            LOGGER.warning("Found generic type usage (<...>): " + codeName + " in " + row.toString() + ". Cleansing...");
        }
        String correctCodeName = codeName.replaceAll("<.*?>", "");
        row.setCodeName(correctCodeName);
        return row;
    }

    private static boolean isUsedGenericType(String codeName) {
        return codeName.contains("<") && codeName.contains(">");
    }

    public static List<ConformedRow> conformCSV(List<Row> rows) {
        return rows.stream()
                .map(ConformedRowMapper::from)
                .toList();
    }

}
