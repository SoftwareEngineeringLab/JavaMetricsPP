package input.mapper;

import input.exception.UnknownSeverityException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SeverityMapper {

    private static final String NONE_SEVERITY = "none";
    private static final String MINOR_SEVERITY = "minor";
    private static final String MAJOR_SEVERITY = "major";
    private static final String CRITICAL_SEVERITY = "critical";

    public static int mapToNumber(String severity) {
        int result;
        switch (severity) {
            case NONE_SEVERITY -> result = 0;
            case MINOR_SEVERITY -> result = 1;
            case MAJOR_SEVERITY -> result = 2;
            case CRITICAL_SEVERITY -> result = 3;
            default -> throw new UnknownSeverityException(severity);
        }
        return result;
    }

}
