package domain.code.investigatedcallable;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static domain.code.investigatedcallable.provider.DeclarationProvider.determineDeclarationWithoutDetails;
import static domain.code.investigatedcallable.provider.TypeParametersProvider.determineTypeParameters;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvestigatedConstructorUtils {

    public static List<ConstructorDeclaration> findAllConstructorDeclarations(CompilationUnit compilationUnit) {
        return compilationUnit.findAll(ConstructorDeclaration.class);
    }

    public static List<ConstructorDeclaration> getDeletedConstructorsFromPreviousVersion(List<ConstructorDeclaration> constructorsFromPreviousVersion,
                                                                                         List<ConstructorDeclaration> constructorsFromCurrentVersion) {
        return constructorsFromPreviousVersion.stream()
                .filter(constructorFromPreviousVersion -> notExistsInCurrentVersion(constructorFromPreviousVersion, constructorsFromCurrentVersion))
                .toList();
    }

    private static boolean notExistsInCurrentVersion(ConstructorDeclaration constructorFromPreviousVersion,
                                                     List<ConstructorDeclaration> constructorsFromCurrentVersion) {
        return constructorsFromCurrentVersion.stream()
                .noneMatch(constructorFromCurrentVersion -> haveTheSameSignatures(constructorFromCurrentVersion, constructorFromPreviousVersion));
    }

    private static boolean haveTheSameSignatures(ConstructorDeclaration constructorFirst,
                                                 ConstructorDeclaration constructorSecond) {
        return determineDeclarationWithoutDetails(constructorFirst).equals(determineDeclarationWithoutDetails(constructorSecond)) &&
                determineTypeParameters(constructorFirst).equals(determineTypeParameters(constructorSecond));
    }

}
