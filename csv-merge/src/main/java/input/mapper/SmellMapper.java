package input.mapper;

import input.exception.UnknownSmellException;
import input.row.Smell;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SmellMapper {

    private static final String LONG_METHOD = "long method";
    private static final String FEATURE_ENVY = "feature envy";
    private static final String DATA_CLASS = "data class";
    private static final String BLOB = "blob";

    public static Smell from(String smellAsText) {
        Smell smell;
        switch (smellAsText) {
            case LONG_METHOD -> smell = Smell.LONG_METHOD;
            case FEATURE_ENVY -> smell = Smell.FEATURE_ENVY;
            case DATA_CLASS -> smell = Smell.DATA_CLASS;
            case BLOB -> smell = Smell.BLOB;
            default -> throw new UnknownSmellException(smellAsText);
        }
        return smell;

    }

}
