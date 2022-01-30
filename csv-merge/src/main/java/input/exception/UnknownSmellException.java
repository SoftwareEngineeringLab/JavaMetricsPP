package input.exception;

import java.text.MessageFormat;

public class UnknownSmellException extends RuntimeException {

    private static final String UNKNOWN_SMELL_MSG = "Unknown smell was found - {0}";

    public UnknownSmellException(String unknownSmell) {
        super(MessageFormat.format(UNKNOWN_SMELL_MSG, unknownSmell));
    }

}
