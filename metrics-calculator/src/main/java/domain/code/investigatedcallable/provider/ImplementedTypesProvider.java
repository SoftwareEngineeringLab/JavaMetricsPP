package domain.code.investigatedcallable.provider;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithImplements;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImplementedTypesProvider {

    public static List<String> determineImplementedTypesByClass(Node callableDeclaration) {
        return getParentNodeWithImplements(callableDeclaration)
                .map(ImplementedTypesProvider::getImplementedTypesByClass)
                .orElse(Collections.emptyList());
    }

    private static Optional<NodeWithImplements<?>> getParentNodeWithImplements(Node callableDeclaration) {
        return callableDeclaration.getParentNode()
                .filter(parentNode -> parentNode instanceof NodeWithImplements)
                .map(parentNode -> (NodeWithImplements<?>) parentNode);
    }

    private static List<String> getImplementedTypesByClass(NodeWithImplements<?> nodeWithImplements) {
        return nodeWithImplements.getImplementedTypes().stream()
                .map(NodeWithSimpleName::getNameAsString)
                .toList();
    }

}
