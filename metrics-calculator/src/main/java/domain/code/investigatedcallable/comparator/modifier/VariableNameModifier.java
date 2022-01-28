package domain.code.investigatedcallable.comparator.modifier;

import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.util.List;

public class VariableNameModifier extends ModifierVisitor<List<String>> {

    private static final String DEFAULT_VARIABLE_NAME = "variableName";

    @Override
    public SimpleName visit(SimpleName simpleName,
                            List<String> variableNames) {
        super.visit(simpleName, variableNames);
        if (variableNames.contains(simpleName.getIdentifier())) {
            simpleName.setId(DEFAULT_VARIABLE_NAME);
        }
        return simpleName;
    }

}
