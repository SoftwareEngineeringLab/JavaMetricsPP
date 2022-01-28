package domain.code.investigatedcallable.provider;

import com.github.javaparser.ast.nodeTypes.NodeWithTypeParameters;
import com.github.javaparser.ast.type.TypeParameter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeParametersProvider {

    public static List<String> determineTypeParameters(NodeWithTypeParameters<?> callableDeclaration) {
        return callableDeclaration.getTypeParameters()
                .stream()
                .map(TypeParameter::asString)
                .toList();
    }

}
