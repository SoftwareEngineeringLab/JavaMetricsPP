package domain.code.investigatedcallable.comparator.modifier;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

public class MethodDeclarationModifier extends ModifierVisitor<Void> {

    private static final String DEFAULT_METHOD_NAME = "methodName";
    private static final String DEFAULT_PARAMETER_NAME = "parameterName";

    @Override
    public MethodDeclaration visit(MethodDeclaration methodDeclaration,
                                   Void arg) {
        super.visit(methodDeclaration, arg);
        methodDeclaration.setName(DEFAULT_METHOD_NAME);
        methodDeclaration.getParameters().forEach(parameter -> parameter.setName(DEFAULT_PARAMETER_NAME));
        return methodDeclaration;
    }

}
