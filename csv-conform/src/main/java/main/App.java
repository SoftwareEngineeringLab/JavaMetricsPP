package main;

import input.CSVReader;
import input.Row;
import normalizer.CSVNormalizer;
import output.CSVWriter;
import output.ConformedRow;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        setLoggingLevel();
        if (args.length == 1) {
            List<Row> rows = CSVReader.readCSV(args[0]);
            rows = CSVNormalizer.cleanseCSV(rows);

            List<ConformedRow> conformedRows = CSVNormalizer.conformCSV(rows);
            CSVWriter.writeCSV(args[0], conformedRows);
        } else if (args.length == 2) {
            List<Row> rows = CSVReader.readCSV(args[0]);
            rows = CSVNormalizer.cleanseCSV(rows);

            List<ConformedRow> conformedRows = CSVNormalizer.conformCSV(rows);
            CSVWriter.writeCSV(args[1], conformedRows);
        } else {
            System.err.println("Usage: java -jar csv-conform-1.0-SNAPSHOT-jar-with-dependencies.jar <input_csv> [<output_csv>]");
            System.exit(1);
        }
        System.exit(0);
    }

    private static void setLoggingLevel() {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.WARNING);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.WARNING);
        }
    }

}
