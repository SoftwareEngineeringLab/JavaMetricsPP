package gitapi.callablecommitsapi.codesamplemodification;

import domain.code.investigatedcallable.InvestigatedCallable;
import gitapi.callablecommitsapi.fileversionsapi.FileVersion;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeSampleModificationsProvider {

    public static List<CodeSampleModification> from(InvestigatedCallable currentCodeSample,
                                                    List<FileVersion> fileVersions) {
        List<CodeSampleModification> codeSampleModifications = new ArrayList<>();
        boolean existInPreviousVersion = true;

        for (int currentVersion = 0; currentVersion < fileVersions.size() - 1 && existInPreviousVersion;
             currentVersion++) {

            int previousVersion = currentVersion + 1;

            FileVersion currentFileVersion = fileVersions.get(currentVersion);
            FileVersion previousFileVersion = fileVersions.get(previousVersion);
            String currentHash = currentFileVersion.getHash();

            Optional<InvestigatedCallable> previousCodeSampleOptional
                    = findPreviousCodeSample(currentCodeSample, previousFileVersion, currentFileVersion);

            CodeSampleModification codeSampleModification;
            if (previousCodeSampleOptional.isPresent()) {
                InvestigatedCallable previousCodeSample = previousCodeSampleOptional.get();
                codeSampleModification =
                        getCodeSampleModification(currentHash, currentCodeSample, previousCodeSample);
                currentCodeSample = previousCodeSample;
            } else {
                existInPreviousVersion = false;
                codeSampleModification = getModificationCreatingCodeSample(currentHash, currentCodeSample);
            }
            codeSampleModifications.add(codeSampleModification);
        }
        if (existInPreviousVersion) {
            FileVersion lastFileVersion = fileVersions.get(fileVersions.size() - 1);
            String hash = lastFileVersion.getHash();
            CodeSampleModification codeSampleModification = getModificationCreatingCodeSample(hash, currentCodeSample);
            codeSampleModifications.add(codeSampleModification);
        }
        return codeSampleModifications;
    }

    private static Optional<InvestigatedCallable> findPreviousCodeSample(InvestigatedCallable currentCodeSample,
                                                                         FileVersion previousFileVersion,
                                                                         FileVersion currentFileVersion) {
        try {
            return currentCodeSample.findPreviousCodeSample(previousFileVersion, currentFileVersion);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static CodeSampleModification getCodeSampleModification(String hash,
                                                                    InvestigatedCallable currentCodeSample,
                                                                    InvestigatedCallable previousCodeSample) {
        List<String> codeLinesFromCurrentVersion = currentCodeSample.getCodeLines();
        List<String> codeLinesFromPreviousVersion = previousCodeSample.getCodeLines();
        return CodeSampleModificationMapper.from(hash, codeLinesFromCurrentVersion, codeLinesFromPreviousVersion);
    }

    private static CodeSampleModification getModificationCreatingCodeSample(String hash,
                                                                            InvestigatedCallable currentCodeSample) {
        List<String> codeLinesFromCurrentVersion = currentCodeSample.getCodeLines();
        return CodeSampleModificationMapper.from(hash, codeLinesFromCurrentVersion);
    }

}
