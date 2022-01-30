package input.exception;

import java.text.MessageFormat;

public class UnknownSeverityException extends RuntimeException {

    private static final String UNKNOWN_SEVERITY_MSG = "Unknown severity was found - {0}";

    public UnknownSeverityException(String unknownSeverity) {
        super(MessageFormat.format(UNKNOWN_SEVERITY_MSG, unknownSeverity));
    }

}
