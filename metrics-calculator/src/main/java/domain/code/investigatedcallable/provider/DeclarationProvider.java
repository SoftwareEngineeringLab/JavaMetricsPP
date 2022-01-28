package domain.code.investigatedcallable.provider;

import com.github.javaparser.ast.nodeTypes.NodeWithDeclaration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DeclarationProvider {

    public static String determineDeclarationWithoutDetails(NodeWithDeclaration callableDeclaration) {
        return callableDeclaration.getDeclarationAsString(false, false, false);
    }

    public static String determineDeclarationWithDetails(NodeWithDeclaration callableDeclaration) {
        return callableDeclaration.getDeclarationAsString(true, true, true);
    }

}
