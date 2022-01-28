package exception;

public class RangeNotFoundException extends RuntimeException {

    private static final String RANGE_NOT_FOUND_MSG = "Range not found";

    public RangeNotFoundException() {
        super(RANGE_NOT_FOUND_MSG);
    }

}
