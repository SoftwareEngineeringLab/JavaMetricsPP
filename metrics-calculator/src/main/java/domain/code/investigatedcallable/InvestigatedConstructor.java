package domain.code.investigatedcallable;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import domain.code.CodeSampleType;
import domain.code.Metric;
import domain.code.investigatedcallable.comparator.ParametersTypesComparator;
import domain.git.Repository;
import exception.CodeSampleNotFoundException;
import gitapi.callablecommitsapi.CallableCommitsApi;
import gitapi.callablecommitsapi.fileversionsapi.FileVersion;
import lombok.Builder;
import lombok.Getter;
import metricscalculator.processmetric.ProcessMetricsCalculator;
import metricscalculator.productmetric.ProductMetricsCallableCalculator;
import utils.CompilationUnitProvider;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static domain.code.investigatedcallable.InvestigatedConstructorUtils.findAllConstructorDeclarations;
import static domain.code.investigatedcallable.InvestigatedConstructorUtils.getDeletedConstructorsFromPreviousVersion;
import static domain.code.investigatedcallable.provider.BodyLinesProvider.determineBodyLines;
import static domain.code.investigatedcallable.provider.DeclarationProvider.determineDeclarationWithDetails;
import static domain.code.investigatedcallable.provider.DeclarationProvider.determineDeclarationWithoutDetails;
import static domain.code.investigatedcallable.provider.ExtendedTypesProvider.determineExtendedTypesByClass;
import static domain.code.investigatedcallable.provider.ImplementedTypesProvider.determineImplementedTypesByClass;
import static domain.code.investigatedcallable.provider.OuterClassesNamesProvider.determineOuterClassesNames;
import static domain.code.investigatedcallable.provider.ParameterTypesProvider.determineParameterTypes;
import static domain.code.investigatedcallable.provider.TypeParametersProvider.determineTypeParameters;

@Getter
public class InvestigatedConstructor extends InvestigatedCallable {

    @Builder
    private InvestigatedConstructor(int id,
                                    Repository repository,
                                    String fileRelativePath,
                                    String fileAbsolutePath,
                                    int startLine,
                                    int endLine) {
        super(id,
                repository,
                fileRelativePath,
                fileAbsolutePath,
                startLine,
                endLine,
                CodeSampleType.CONSTRUCTOR);
    }

    private InvestigatedConstructor(int id,
                                    ConstructorDeclaration constructorDeclaration) {
        this.id = id;
        initData(constructorDeclaration);
    }

    private void initData(ConstructorDeclaration constructorDeclaration) {
        this.declarationWithoutDetails = determineDeclarationWithoutDetails(constructorDeclaration);
        this.declarationWithDetails = determineDeclarationWithDetails(constructorDeclaration);
        this.parameterTypes = determineParameterTypes(constructorDeclaration);
        this.typeParameters = determineTypeParameters(constructorDeclaration);
        this.bodyLines = determineBodyLines(constructorDeclaration);
        this.implementedTypesByClass = determineImplementedTypesByClass(constructorDeclaration);
        this.extendedTypesByClass = determineExtendedTypesByClass(constructorDeclaration);
        this.outerClassesNames = determineOuterClassesNames(constructorDeclaration);
    }

    @Override
    protected List<Metric> getCalculatedProductMetrics() {
        ConstructorDeclaration constructorDeclaration = getConstructorDeclaration();
        return ProductMetricsCallableCalculator.calculateMetricsWithoutDependencyResolution(constructorDeclaration);
    }

    @Override
    protected List<Metric> getCalculatedProcessMetrics() {
        ConstructorDeclaration constructorDeclaration = getConstructorDeclaration();
        initData(constructorDeclaration);
        this.commits = CallableCommitsApi.getCommits(this);
        return ProcessMetricsCalculator.calculateAllMetrics(this);
    }

    private ConstructorDeclaration getConstructorDeclaration() {
        CompilationUnit compilationUnit = CompilationUnitProvider.from(fileAbsolutePath);
        return findAllConstructorDeclarations(compilationUnit)
                .stream()
                .filter(this::beginsInTheSameLine)
                .filter(this::endsInTheSameLine)
                .findAny()
                .orElseThrow(() -> new CodeSampleNotFoundException(this.id));
    }

    @Override
    public Optional<InvestigatedCallable> findPreviousCodeSample(FileVersion previousFileVersion,
                                                                 FileVersion currentFileVersion) {
        List<ConstructorDeclaration> constructorsFromPreviousVersion = getConstructorsDefinedInTheSameClass(previousFileVersion.getCompilationUnit());
        return findTheSameConstructor(constructorsFromPreviousVersion)
                .or(() -> findModifiedConstructor(constructorsFromPreviousVersion, currentFileVersion));
    }

    private List<ConstructorDeclaration> getConstructorsDefinedInTheSameClass(CompilationUnit compilationUnit) {
        return findAllConstructorDeclarations(compilationUnit)
                .stream()
                .filter(this::isInTheSameClass)
                .toList();
    }

    private Optional<InvestigatedCallable> findTheSameConstructor(List<ConstructorDeclaration> constructorsDefinedInTheSameClass) {
        return constructorsDefinedInTheSameClass
                .stream()
                .filter(this::haveTheSameSignature)
                .findAny()
                .map(constructorDeclaration -> new InvestigatedConstructor(this.id, constructorDeclaration));
    }

    private boolean haveTheSameSignature(ConstructorDeclaration otherConstructorDeclaration) {
        return determineDeclarationWithoutDetails(otherConstructorDeclaration).equals(declarationWithoutDetails) &&
                determineTypeParameters(otherConstructorDeclaration).equals(typeParameters);
    }

    public Optional<InvestigatedCallable> findModifiedConstructor(List<ConstructorDeclaration> constructorsFromPreviousVersion,
                                                                  FileVersion currentFileVersion) {
        List<ConstructorDeclaration> constructorsFromCurrentVersion = getConstructorsDefinedInTheSameClass(currentFileVersion.getCompilationUnit());
        List<ConstructorDeclaration> deletedConstructorsFromPreviousVersion = getDeletedConstructorsFromPreviousVersion(constructorsFromPreviousVersion,
                constructorsFromCurrentVersion);

        return getMostSimilarConstructor(deletedConstructorsFromPreviousVersion)
                .map(constructorDeclaration -> new InvestigatedConstructor(this.id, constructorDeclaration));
    }

    private Optional<ConstructorDeclaration> getMostSimilarConstructor(List<ConstructorDeclaration> constructors) {
        return constructors.stream()
                .max(Comparator.comparingDouble(this::getSimilarity));
    }

    private double getSimilarity(ConstructorDeclaration otherConstructorDeclaration) {
        List<String> otherParameterTypes = determineParameterTypes(otherConstructorDeclaration);
        return ParametersTypesComparator.compare(this.parameterTypes, otherParameterTypes);
    }

}
