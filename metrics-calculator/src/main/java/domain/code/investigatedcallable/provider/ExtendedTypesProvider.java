package domain.code.investigatedcallable.provider;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithExtends;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtendedTypesProvider {

    public static List<String> determineExtendedTypesByClass(Node callableDeclaration) {
        return getParentNodeWithExtends(callableDeclaration)
                .map(ExtendedTypesProvider::getExtendedTypesByClass)
                .orElse(Collections.emptyList());
    }

    private static Optional<NodeWithExtends<?>> getParentNodeWithExtends(Node callableDeclaration) {
        return callableDeclaration.getParentNode()
                .filter(parentNode -> parentNode instanceof NodeWithExtends)
                .map(parentNode -> (NodeWithExtends<?>) parentNode);
    }

    private static List<String> getExtendedTypesByClass(NodeWithExtends<?> nodeWithExtends) {
        return nodeWithExtends.getExtendedTypes().stream()
                .map(NodeWithSimpleName::getNameAsString)
                .toList();
    }

}
