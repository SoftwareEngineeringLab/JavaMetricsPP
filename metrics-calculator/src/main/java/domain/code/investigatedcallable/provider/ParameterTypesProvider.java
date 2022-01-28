package domain.code.investigatedcallable.provider;

import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.type.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParameterTypesProvider {

    public static List<String> determineParameterTypes(CallableDeclaration<?> callableDeclaration) {
        return callableDeclaration.getSignature()
                .getParameterTypes()
                .stream()
                .map(Type::asString)
                .toList();
    }

}
