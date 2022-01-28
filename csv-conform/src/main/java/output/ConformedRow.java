package output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ConformedRow {

    public static final String CLASS_TYPE = "class";
    public static final String METHOD_TYPE = "method";
    public static final String CONSTRUCTOR_TYPE = "constructor";

    private int sampleId;
    private String type;
    private String packageName;
    private String outerClassName;
    private String className;
    private String methodName;
    private List<String> parameters;
    private String repository;
    private String commitHash;
    private String gitSourceFileUrl;
    private String startLine;
    private String endLine;
    private String filePath;

    public String getParameters() {
        if (isConstructorOrMethod()) {
            return String.join("|", parameters);
        }
        return "";
    }

    private boolean isConstructorOrMethod() {
        return type.equals(METHOD_TYPE) || type.equals(CONSTRUCTOR_TYPE);
    }

}
