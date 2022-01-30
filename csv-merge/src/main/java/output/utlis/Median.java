package output.utlis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Median {

    public static Integer count(int[] values) {
        if (values.length == 1) {
            return values[0];
        }
        Arrays.sort(values);
        int middleIndex = values.length / 2;
        if (values.length % 2 == 1) {
            return values[middleIndex];
        } else if (values[middleIndex - 1] == values[middleIndex]) {
            return values[middleIndex];
        } else {
            return values[middleIndex - 1];
        }
    }

}
