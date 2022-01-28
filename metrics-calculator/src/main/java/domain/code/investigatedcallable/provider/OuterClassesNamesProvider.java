package domain.code.investigatedcallable.provider;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OuterClassesNamesProvider {

    public static List<String> determineOuterClassesNames(Node callableDeclaration) {
        return callableDeclaration.getParentNode()
                .map(OuterClassesNamesProvider::getOuterClassesNames)
                .orElse(Collections.emptyList());
    }

    private static List<String> getOuterClassesNames(Node parentNode) {
        if (parentNode instanceof ClassOrInterfaceDeclaration || parentNode instanceof EnumDeclaration) {
            List<String> outerClassesNames = new ArrayList<>();
            outerClassesNames.add(getSimpleName(parentNode));
            return parentNode.getParentNode()
                    .map(nextParentNode -> getOuterClassesNames(nextParentNode, outerClassesNames))
                    .orElse(outerClassesNames);
        } else if (parentNode instanceof ObjectCreationExpr) {
            ObjectCreationExpr objectCreationExpr = (ObjectCreationExpr) parentNode;
            return List.of(getClassType(objectCreationExpr) + " " + getVariableName(objectCreationExpr));
        } else if (parentNode instanceof EnumConstantDeclaration) {
            return List.of(getSimpleName(parentNode));
        } else {
            return Collections.emptyList();
        }
    }

    private static List<String> getOuterClassesNames(Node parentNode,
                                                     List<String> outerClassesNames) {
        if (parentNode instanceof ClassOrInterfaceDeclaration) {
            outerClassesNames.add(getSimpleName(parentNode));
            return parentNode.getParentNode()
                    .map(nextParentNode -> getOuterClassesNames(nextParentNode, outerClassesNames))
                    .orElse(outerClassesNames);
        }
        return outerClassesNames;
    }

    private static String getClassType(ObjectCreationExpr objectCreationExpr) {
        ClassOrInterfaceType classType = objectCreationExpr.getType();
        return classType.getNameAsString();
    }

    private static String getVariableName(ObjectCreationExpr objectCreationExpr) {
        return getVariableDeclarator(objectCreationExpr)
                .map(NodeWithSimpleName::getNameAsString)
                .orElse("");
    }

    private static Optional<VariableDeclarator> getVariableDeclarator(ObjectCreationExpr objectCreationExpr) {
        return objectCreationExpr.getParentNode()
                .filter(parentNode -> parentNode instanceof VariableDeclarator)
                .map(parentNode -> (VariableDeclarator) parentNode);
    }

    private static String getSimpleName(Node node) {
        NodeWithSimpleName<?> nodeWithSimpleName = (NodeWithSimpleName<?>) node;
        return nodeWithSimpleName.getNameAsString();
    }

}
