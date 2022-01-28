package domain.code.investigatedcallable.comparator;

import com.github.javaparser.JavaToken;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import domain.code.investigatedcallable.comparator.modifier.MethodDeclarationModifier;
import domain.code.investigatedcallable.comparator.modifier.VariableNameModifier;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.LongestCommonSubsequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MethodDeclarationComparator {

    public static double compare(MethodDeclaration methodFirst,
                                 MethodDeclaration methodSecond) {
        List<String> methodFirstJavaTokens = getNormalizedJavaTokens(methodFirst);
        List<String> methodSecondJavaTokens = getNormalizedJavaTokens(methodSecond);
        return getMethodsSimilarity(methodFirstJavaTokens, methodSecondJavaTokens);
    }

    private static List<String> getNormalizedJavaTokens(MethodDeclaration methodDeclaration) {
        MethodDeclaration normalizedMethod = getNormalizedMethod(methodDeclaration);
        return getJavaTokens(normalizedMethod);
    }

    private static MethodDeclaration getNormalizedMethod(MethodDeclaration methodDeclaration) {
        MethodDeclaration methodClone = methodDeclaration.clone();
        normalizeMethodDeclaration(methodClone);
        normalizeVariableNames(methodClone);
        return methodClone;
    }

    private static void normalizeMethodDeclaration(MethodDeclaration methodDeclaration) {
        ModifierVisitor<Void> methodDeclarationNameModifier = new MethodDeclarationModifier();
        methodDeclarationNameModifier.visit(methodDeclaration, null);
    }

    private static void normalizeVariableNames(MethodDeclaration methodDeclaration) {
        List<String> variableNames = getVariableNames(methodDeclaration);
        ModifierVisitor<List<String>> variableDeclaratorNameModifier = new VariableNameModifier();
        variableDeclaratorNameModifier.visit(methodDeclaration, variableNames);
    }

    private static List<String> getVariableNames(MethodDeclaration methodDeclaration) {
        return methodDeclaration.findAll(VariableDeclarator.class)
                .stream()
                .map(NodeWithSimpleName::getNameAsString)
                .toList();
    }

    private static List<String> getJavaTokens(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getBody()
                .flatMap(Node::getTokenRange)
                .map(MethodDeclarationComparator::getJavaTokens)
                .orElse(Collections.emptyList());
    }

    private static List<String> getJavaTokens(TokenRange tokenRange) {
        List<String> javaTokens = new ArrayList<>();
        for (JavaToken javaToken : tokenRange) {
            String text = javaToken.getText();
            if (isNotEmpty(text)) {
                javaTokens.add(text);
            }
        }
        return javaTokens;
    }

    private static boolean isNotEmpty(String text) {
        return Stream.of(" ", "\n", "\r", "\r\n")
                .noneMatch(text::equals);
    }

    private static double getMethodsSimilarity(List<String> methodFirstJavaTokens,
                                               List<String> methodSecondJavaTokens) {
        double longestCommonSubsequenceLength = LongestCommonSubsequence.
                getLength(methodFirstJavaTokens, methodSecondJavaTokens);
        return 2 * longestCommonSubsequenceLength / (methodFirstJavaTokens.size() + methodSecondJavaTokens.size());
    }

}
