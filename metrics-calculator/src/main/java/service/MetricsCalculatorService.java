package service;

import config.Constants;
import domain.code.CodeSample;
import domain.git.Repository;
import input.CSVInputRow;
import input.CSVReader;
import input.codesampleprovider.CodeSamplesProvider;
import input.repositoryprovider.RepositoriesProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import output.writer.CallableRowsWriter;
import output.writer.ClassRowsWriter;
import utils.DirectoryUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetricsCalculatorService {

    //private static final String DEFAULT_OUTPUT_REPOSITORY_DIR = System.getProperty("user.home") + File.separator + "java-metrics-source-repos";
    //private static final String WORK_DIRECTORY = DEFAULT_OUTPUT_REPOSITORY_DIR + File.separator + "file-versions";

    public static void calculateMetrics() {
        DirectoryUtils.prepareDirectory(Constants.WORK_DIRECTORY);
        List<CSVInputRow> rows = CSVReader.getRows(Constants.CSV_INPUT_FILE_PATH);
        List<Repository> repositories = RepositoriesProvider.from(rows);
        //UnavailableRepositoriesWriter.write(repositories);

        List<CodeSample> codeSamples = CodeSamplesProvider.getAvailableCodeSamples(rows, repositories);

        int doneElements = 0;
        int allElements = codeSamples.size();

        for (CodeSample codeSample : codeSamples) {
            System.out.println("Sample id: " + codeSample.getId());
            codeSample.calculateProductMetrics();
            codeSample.calculateProcessMetrics();
            doneElements++;
            System.out.println("Done code sample : " + doneElements + "/" + allElements);
        }

        List<CodeSample> processedCodeSamples = codeSamples.stream()
                .filter(CodeSample::isProcessed)
                .toList();

        CallableRowsWriter.write(processedCodeSamples);
        ClassRowsWriter.write(processedCodeSamples);
    }

}
