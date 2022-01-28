package exception;

import java.text.MessageFormat;

public class RepositoryNotFoundException extends RuntimeException {

    private static final String REPOSITORY_NOT_FOUND_MSG = "Repository with address: {0} not found";

    public RepositoryNotFoundException(String repositoryAddressUri) {
        super(MessageFormat.format(REPOSITORY_NOT_FOUND_MSG, repositoryAddressUri));
    }

}
