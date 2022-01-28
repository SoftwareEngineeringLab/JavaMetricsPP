package metricscalculator.productmetric;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import exception.JavaParserNotConfiguredException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetricParserUtil {

    private static volatile JavaParser javaParser;
    private static String path;

    public static void setup(ParserConfiguration configuration, String projectPath) {
        if (javaParser == null) {
            synchronized (MetricParserUtil.class) {
                if (javaParser == null) {
                    javaParser = new JavaParser(configuration);
                    path = projectPath;
                }
            }
        }

    }

    public static JavaParser getJavaParserInstance() throws JavaParserNotConfiguredException {
        if (javaParser == null) {
            synchronized (MetricParserUtil.class) {
                if (javaParser == null) {
                    throw new JavaParserNotConfiguredException("Static Javaparser has not been configured.");
                }
            }
        }
        return javaParser;
    }

    public static String getProjectPath() {
        return path;
    }

}
