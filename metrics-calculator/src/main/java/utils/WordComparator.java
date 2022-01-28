package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WordComparator {

    public static double compare(String wordFirst,
                                 String wordSecond) {
        double longestCommonSubsequenceLength = LongestCommonSubsequence.getLength(wordFirst, wordSecond);
        return 2 * longestCommonSubsequenceLength / (wordFirst.length() + wordSecond.length());
    }

}
