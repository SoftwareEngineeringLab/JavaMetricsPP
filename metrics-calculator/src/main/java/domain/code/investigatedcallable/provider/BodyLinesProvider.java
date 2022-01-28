package domain.code.investigatedcallable.provider;

import com.github.javaparser.JavaToken;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalBlockStmt;
import exception.RangeNotFoundException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BodyLinesProvider {

    public static List<String> determineBodyLines(NodeWithBlockStmt<?> callableDeclaration) {
        return callableDeclaration.getBody()
                .getTokenRange()
                .map(BodyLinesProvider::getLines)
                .orElse(Collections.emptyList());
    }

    public static List<String> determineBodyLines(NodeWithOptionalBlockStmt<?> callableDeclaration) {
        return callableDeclaration.getBody()
                .flatMap(Node::getTokenRange)
                .map(BodyLinesProvider::getLines)
                .orElse(Collections.emptyList());
    }

    private static List<String> getLines(TokenRange javaTokens) {
        Map<Integer, StringBuilder> lines = new LinkedHashMap<>();
        for (JavaToken javaToken : javaTokens) {
            String text = javaToken.getText();
            if (isNotLineSeparator(text)) {
                int lineNumber = getJavaTokenLineNumber(javaToken);
                lines.computeIfPresent(lineNumber, (key, value) -> value.append(text));
                lines.putIfAbsent(lineNumber, new StringBuilder(text));
            }
        }
        return getLines(lines);
    }

    private static boolean isNotLineSeparator(String text) {
        return Stream.of("\n", "\r", "\r\n")
                .noneMatch(text::equals);
    }

    private static int getJavaTokenLineNumber(JavaToken javaToken) {
        return javaToken.getRange()
                .map(range -> range.begin.line)
                .orElseThrow(RangeNotFoundException::new);
    }

    private static List<String> getLines(Map<Integer, StringBuilder> lines) {
        return lines.values()
                .stream()
                .map(StringBuilder::toString)
                .toList();
    }

}
