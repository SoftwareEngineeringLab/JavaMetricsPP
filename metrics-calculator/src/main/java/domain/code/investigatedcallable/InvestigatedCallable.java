package domain.code.investigatedcallable;

import com.github.javaparser.ast.Node;
import domain.code.CodeSample;
import domain.code.CodeSampleType;
import domain.git.Repository;
import gitapi.callablecommitsapi.fileversionsapi.FileVersion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static domain.code.investigatedcallable.provider.ExtendedTypesProvider.determineExtendedTypesByClass;
import static domain.code.investigatedcallable.provider.ImplementedTypesProvider.determineImplementedTypesByClass;
import static domain.code.investigatedcallable.provider.OuterClassesNamesProvider.determineOuterClassesNames;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class InvestigatedCallable extends CodeSample {

    protected String declarationWithoutDetails;
    protected String declarationWithDetails;
    protected List<String> parameterTypes;
    protected List<String> typeParameters;
    protected List<String> bodyLines;

    protected List<String> implementedTypesByClass;
    protected List<String> extendedTypesByClass;
    protected List<String> outerClassesNames;

    public InvestigatedCallable(int id,
                                Repository repository,
                                String fileRelativePath,
                                String fileAbsolutePath,
                                int startLine,
                                int endLine,
                                CodeSampleType type) {
        super(id,
                repository,
                fileRelativePath,
                fileAbsolutePath,
                startLine,
                endLine,
                type);
    }

    public abstract Optional<InvestigatedCallable> findPreviousCodeSample(FileVersion previousFileVersion,
                                                                          FileVersion currentFileVersion);

    public List<String> getCodeLines() {
        List<String> codeLines = new ArrayList<>();
        codeLines.add(declarationWithDetails);
        codeLines.addAll(bodyLines);
        return codeLines;
    }

    protected boolean isInTheSameClass(Node otherCallableDeclaration) {
        return determineImplementedTypesByClass(otherCallableDeclaration).equals(implementedTypesByClass) &&
                determineExtendedTypesByClass(otherCallableDeclaration).equals(extendedTypesByClass) &&
                determineOuterClassesNames(otherCallableDeclaration).equals(outerClassesNames);
    }

}
