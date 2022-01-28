package input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.logging.Logger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Row {

    private static final Logger LOGGER = Logger.getLogger(Row.class.getName());

    public static final String CLASS_TYPE = "class";
    public static final String METHOD_TYPE = "function";

    @EqualsAndHashCode.Include
    private int sampleId;

    private String type;
    private String codeName;
    private String repository;
    private String commitHash;
    private String link;
    private String startLine;
    private String endLine;
    private String filePath;

    public boolean isCorrect() {
        if (isEmpty(type)) {
            LOGGER.warning("Found empty type in " + this);
            return false;
        } else if (isEmpty(codeName)) {
            LOGGER.warning("Found empty code_name in " + this);
            return false;
        }
        return true;
    }

    private boolean isEmpty(String field) {
        return field.isBlank() || field.isEmpty();
    }

}
