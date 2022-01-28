package domain.code.investigatedcallable.comparator;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.LongestCommonSubsequence;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParametersTypesComparator {

    private static final double MAX_SIMILARITY = 1;

    public static double compare(List<String> parametersTypes,
                                 List<String> otherParametersTypes) {
        if (parametersTypes.isEmpty() && otherParametersTypes.isEmpty()) {
            return MAX_SIMILARITY;
        } else {
            double longestCommonSubsequenceLength = LongestCommonSubsequence.
                    getLength(parametersTypes, otherParametersTypes);
            return 2 * longestCommonSubsequenceLength / (parametersTypes.size() + otherParametersTypes.size());
        }
    }

}
