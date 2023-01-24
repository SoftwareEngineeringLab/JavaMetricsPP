package input.codesampleprovider;

import domain.code.CodeSample;
import domain.code.CodeSampleType;
import domain.git.Repository;
import exception.RepositoryNotFoundException;
import input.CSVInputRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeSamplesProvider {

    private static final Map<CodeSampleType, CodeSampleMapper> sourceElementMappers
            = new EnumMap<>(CodeSampleType.class);

    static {
        sourceElementMappers.put(CodeSampleType.CLASS, new InvestigatedClassMapper());
        sourceElementMappers.put(CodeSampleType.CONSTRUCTOR, new InvestigatedConstructorMapper());
        sourceElementMappers.put(CodeSampleType.METHOD, new InvestigatedMethodMapper());
    }

    public static List<CodeSample> getAvailableCodeSamples(List<CSVInputRow> rows,
                                                           List<Repository> repositories) {
        return getAllCodeSamples(rows, repositories)
                .stream()
                .filter(CodeSample::isAvailable)
                .toList();
    }

    private static List<CodeSample> getAllCodeSamples(List<CSVInputRow> rows,
                                                      List<Repository> repositories) {
        return rows.stream()
                .map(row -> {
                    try {
                        return getCodeSample(row, repositories);
                    } catch (Exception ex) {
                        System.err.println("Failed to fetch repository for " + row.getSampleId() + ". Exception: " + ex);
                        return null;
                    }
                })
                .filter(x -> x != null)
                .toList();
    }

    private static CodeSample getCodeSample(CSVInputRow row,
                                            List<Repository> repositories) {
        Repository repository = getRepository(row.getRepositoryAddressURI(), repositories);
        CodeSampleMapper codeSampleMapper = getCodeSampleMapper(row.getType());
        return codeSampleMapper.from(row, repository);
    }

    private static Repository getRepository(String repositoryAddressUri,
                                            List<Repository> repositories) {
        return repositories.stream()
                .filter(repository -> hasTheSameAddressURI(repository, repositoryAddressUri))
                .findAny()
                .orElseThrow(() -> new RepositoryNotFoundException(repositoryAddressUri));
    }

    private static boolean hasTheSameAddressURI(Repository repository,
                                                String repositoryAddressURI) {
        return repository.getAddressURI().equals(repositoryAddressURI);
    }

    private static CodeSampleMapper getCodeSampleMapper(String type) {
        return sourceElementMappers.get(getCodeSampleType(type));
    }

    private static CodeSampleType getCodeSampleType(String type) {
        return CodeSampleType.valueOf(type.toUpperCase());
    }

}
