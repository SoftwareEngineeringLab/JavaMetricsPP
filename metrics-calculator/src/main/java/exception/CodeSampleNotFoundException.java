package exception;

import java.text.MessageFormat;

public class CodeSampleNotFoundException extends RuntimeException {

    private static final String CODE_SAMPLE_NOT_FOUND_MSG = "Code sample with id: {0} not found";

    public CodeSampleNotFoundException(int sampleId) {
        super(MessageFormat.format(CODE_SAMPLE_NOT_FOUND_MSG, String.valueOf(sampleId)));
    }

}
