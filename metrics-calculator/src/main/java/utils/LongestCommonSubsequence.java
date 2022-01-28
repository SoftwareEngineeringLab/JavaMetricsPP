package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LongestCommonSubsequence {

    public static int getLength(List<String> listFirst,
                                List<String> listSecond) {
        int[][] temp = new int[listFirst.size() + 1][listSecond.size() + 1];
        int max = 0;
        for (int i = 1; i < temp.length; i++) {
            for (int j = 1; j < temp[i].length; j++) {
                if (listFirst.get(i - 1).equals(listSecond.get(j - 1))) {
                    temp[i][j] = temp[i - 1][j - 1] + 1;
                } else {
                    temp[i][j] = Math.max(temp[i][j - 1], temp[i - 1][j]);
                }
                if (temp[i][j] > max) {
                    max = temp[i][j];
                }
            }
        }
        return max;
    }

    public static int getLength(String wordFirst,
                                String wordSecond) {
        List<String> listFirst = splitWord(wordFirst);
        List<String> listSecond = splitWord(wordSecond);
        return getLength(listFirst, listSecond);
    }

    private static List<String> splitWord(String word) {
        return Stream.of(word.toCharArray())
                .map(String::valueOf)
                .toList();
    }

}
