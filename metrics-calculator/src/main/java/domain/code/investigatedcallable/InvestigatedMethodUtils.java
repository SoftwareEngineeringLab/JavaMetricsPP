package domain.code.investigatedcallable;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static domain.code.investigatedcallable.provider.DeclarationProvider.determineDeclarationWithoutDetails;
import static domain.code.investigatedcallable.provider.TypeParametersProvider.determineTypeParameters;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvestigatedMethodUtils {

    public static List<MethodDeclaration> findAllMethodDeclarations(CompilationUnit compilationUnit) {
        return compilationUnit.findAll(MethodDeclaration.class);
    }

    public static List<MethodDeclaration> getDeletedMethodsFromPreviousVersion(List<MethodDeclaration> methodsFromPreviousVersion,
                                                                               List<MethodDeclaration> methodsFromCurrentVersion) {
        return methodsFromPreviousVersion.stream()
                .filter(methodFromPreviousVersion -> notExistsInCurrentVersion(methodFromPreviousVersion, methodsFromCurrentVersion))
                .toList();
    }

    private static boolean notExistsInCurrentVersion(MethodDeclaration methodFromPreviousVersion,
                                                     List<MethodDeclaration> methodsFromCurrentVersion) {
        return methodsFromCurrentVersion.stream()
                .noneMatch(methodFromCurrentVersion -> haveTheSameSignatures(methodFromCurrentVersion, methodFromPreviousVersion));
    }

    private static boolean haveTheSameSignatures(MethodDeclaration methodFirst,
                                                 MethodDeclaration methodSecond) {
        return determineDeclarationWithoutDetails(methodFirst).equals(determineDeclarationWithoutDetails(methodSecond)) &&
                determineTypeParameters(methodFirst).equals(determineTypeParameters(methodSecond));
    }

}
