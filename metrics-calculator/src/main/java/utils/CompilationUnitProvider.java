package utils;

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationUnitProvider {

    public static CompilationUnit from(String fileAbsolutePath) {
        StaticJavaParser.setConfiguration(getParserConfiguration());
        CompilationUnit compilationUnit = null;
        try {
            compilationUnit = StaticJavaParser.parse(new File(fileAbsolutePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return compilationUnit;
    }

    private static ParserConfiguration getParserConfiguration() {
        return new ParserConfiguration().setAttributeComments(false);
    }

}
