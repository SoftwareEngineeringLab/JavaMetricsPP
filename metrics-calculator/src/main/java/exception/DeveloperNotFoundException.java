package exception;

import java.text.MessageFormat;

public class DeveloperNotFoundException extends RuntimeException {

    private static final String DEVELOPER_NOT_FOUND_MSG = "Developer with mail: {0} not found";

    public DeveloperNotFoundException(String mail) {
        super(MessageFormat.format(DEVELOPER_NOT_FOUND_MSG, mail));
    }

}
