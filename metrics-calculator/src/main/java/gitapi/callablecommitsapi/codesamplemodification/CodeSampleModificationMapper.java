package gitapi.callablecommitsapi.codesamplemodification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class CodeSampleModificationMapper {

    public static CodeSampleModification from(String hash,
                                              List<String> addedCodeLines) {
        int addedLines = addedCodeLines.size();
        int deletedLines = 0;
        return CodeSampleModification.builder()
                .hash(hash)
                .addedLines(addedLines)
                .deletedLines(deletedLines)
                .isCreatingCodeSample(true)
                .build();
    }

    public static CodeSampleModification from(String hash,
                                              List<String> codeLinesFromCurrentVersion,
                                              List<String> codeLinesFromPreviousVersion) {
        int addedLines = getAddedLines(codeLinesFromCurrentVersion, codeLinesFromPreviousVersion);
        int deletedLines = getDeletedLines(codeLinesFromCurrentVersion, codeLinesFromPreviousVersion);
        return CodeSampleModification.builder()
                .hash(hash)
                .addedLines(addedLines)
                .deletedLines(deletedLines)
                .isCreatingCodeSample(false)
                .build();
    }

    private static int getAddedLines(List<String> codeLinesFromCurrentVersion,
                                     List<String> codeLinesFromPreviousVersion) {
        return (int) codeLinesFromCurrentVersion.stream()
                .filter(codeLineFromCurrentVersion -> notExistsInPreviousVersion(codeLineFromCurrentVersion, codeLinesFromPreviousVersion))
                .count();
    }

    private static boolean notExistsInPreviousVersion(String codeLineFromCurrentVersion, List<String> codeLinesFromPreviousVersion) {
        return !codeLinesFromPreviousVersion.contains(codeLineFromCurrentVersion);
    }

    private static int getDeletedLines(List<String> codeLinesFromCurrentVersion,
                                       List<String> codeLinesFromPreviousVersion) {
        return (int) codeLinesFromPreviousVersion.stream()
                .filter(codeLineFromPreviousVersion -> notExistsInCurrentVersion(codeLineFromPreviousVersion, codeLinesFromCurrentVersion))
                .count();
    }

    private static boolean notExistsInCurrentVersion(String codeLineFromPreviousVersion, List<String> codeLinesFromCurrentVersion) {
        return !codeLinesFromCurrentVersion.contains(codeLineFromPreviousVersion);
    }

}
