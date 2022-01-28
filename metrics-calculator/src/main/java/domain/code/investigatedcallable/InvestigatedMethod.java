package domain.code.investigatedcallable;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import domain.code.CodeSampleType;
import domain.code.Metric;
import domain.code.investigatedcallable.comparator.MethodDeclarationComparator;
import domain.git.Repository;
import exception.CodeSampleNotFoundException;
import gitapi.callablecommitsapi.CallableCommitsApi;
import gitapi.callablecommitsapi.fileversionsapi.FileVersion;
import lombok.Builder;
import lombok.Getter;
import metricscalculator.processmetric.ProcessMetricsCalculator;
import metricscalculator.productmetric.ProductMetricsCallableCalculator;
import utils.CompilationUnitProvider;

import java.util.List;
import java.util.Optional;

import static domain.code.investigatedcallable.InvestigatedMethodUtils.findAllMethodDeclarations;
import static domain.code.investigatedcallable.InvestigatedMethodUtils.getDeletedMethodsFromPreviousVersion;
import static domain.code.investigatedcallable.provider.BodyLinesProvider.determineBodyLines;
import static domain.code.investigatedcallable.provider.DeclarationProvider.determineDeclarationWithDetails;
import static domain.code.investigatedcallable.provider.DeclarationProvider.determineDeclarationWithoutDetails;
import static domain.code.investigatedcallable.provider.ExtendedTypesProvider.determineExtendedTypesByClass;
import static domain.code.investigatedcallable.provider.ImplementedTypesProvider.determineImplementedTypesByClass;
import static domain.code.investigatedcallable.provider.OuterClassesNamesProvider.determineOuterClassesNames;
import static domain.code.investigatedcallable.provider.ParameterTypesProvider.determineParameterTypes;
import static domain.code.investigatedcallable.provider.TypeParametersProvider.determineTypeParameters;

@Getter
public class InvestigatedMethod extends InvestigatedCallable {

    private static final double MINIMAL_LIMIT_SIMILARITY = 0.65;
    private MethodDeclaration methodDeclaration;

    @Builder
    private InvestigatedMethod(int id,
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
                CodeSampleType.METHOD);
    }

    private InvestigatedMethod(int id,
                               MethodDeclaration methodDeclaration) {
        this.id = id;
        initData(methodDeclaration);
    }

    private void initData(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
        this.declarationWithoutDetails = determineDeclarationWithoutDetails(methodDeclaration);
        this.declarationWithDetails = determineDeclarationWithDetails(methodDeclaration);
        this.parameterTypes = determineParameterTypes(methodDeclaration);
        this.typeParameters = determineTypeParameters(methodDeclaration);
        this.bodyLines = determineBodyLines(methodDeclaration);
        this.implementedTypesByClass = determineImplementedTypesByClass(methodDeclaration);
        this.extendedTypesByClass = determineExtendedTypesByClass(methodDeclaration);
        this.outerClassesNames = determineOuterClassesNames(methodDeclaration);
    }

    @Override
    protected List<Metric> getCalculatedProductMetrics() {
        MethodDeclaration methodDeclaration = getMethodDeclaration();
        return ProductMetricsCallableCalculator.calculateMetricsWithoutDependencyResolution(methodDeclaration);
    }

    @Override
    protected List<Metric> getCalculatedProcessMetrics() {
        MethodDeclaration methodDeclaration = getMethodDeclaration();
        initData(methodDeclaration);
        this.commits = CallableCommitsApi.getCommits(this);
        List<Metric> processMetrics = ProcessMetricsCalculator.calculateAllMetrics(this);
        this.methodDeclaration = null;
        return processMetrics;
    }

    private MethodDeclaration getMethodDeclaration() {
        CompilationUnit compilationUnit = CompilationUnitProvider.from(fileAbsolutePath);
        return findAllMethodDeclarations(compilationUnit)
                .stream()
                .filter(this::beginsInTheSameLine)
                .filter(this::endsInTheSameLine)
                .findAny()
                .orElseThrow(() -> new CodeSampleNotFoundException(this.id));
    }

    @Override
    public Optional<InvestigatedCallable> findPreviousCodeSample(FileVersion previousFileVersion,
                                                                 FileVersion currentFileVersion) {
        List<MethodDeclaration> methodsFromPreviousVersion = getMethodsDefinedInTheSameClass(previousFileVersion.getCompilationUnit());
        return findTheSameMethod(methodsFromPreviousVersion)
                .or(() -> findModifiedMethod(methodsFromPreviousVersion, currentFileVersion));
    }

    public Optional<InvestigatedCallable> findTheSameMethod(List<MethodDeclaration> methodsDefinedInTheSameClass) {
        return methodsDefinedInTheSameClass
                .stream()
                .filter(this::haveTheSameSignature)
                .findAny()
                .map(methodDeclaration -> new InvestigatedMethod(this.id, methodDeclaration));
    }

    private boolean haveTheSameSignature(MethodDeclaration otherMethodDeclaration) {
        return determineDeclarationWithoutDetails(otherMethodDeclaration).equals(declarationWithoutDetails) &&
                determineTypeParameters(otherMethodDeclaration).equals(typeParameters);
    }

    public Optional<InvestigatedCallable> findModifiedMethod(List<MethodDeclaration> methodsFromPreviousVersion,
                                                             FileVersion currentFileVersion) {
        List<MethodDeclaration> methodsFromCurrentVersion = getMethodsDefinedInTheSameClass(currentFileVersion.getCompilationUnit());
        List<MethodDeclaration> deletedMethodsFromPreviousVersion = getDeletedMethodsFromPreviousVersion(methodsFromPreviousVersion,
                methodsFromCurrentVersion);

        return findMostSimilarMethod(deletedMethodsFromPreviousVersion)
                .map(methodDeclaration -> new InvestigatedMethod(this.id, methodDeclaration));
    }

    private List<MethodDeclaration> getMethodsDefinedInTheSameClass(CompilationUnit compilationUnit) {
        return findAllMethodDeclarations(compilationUnit)
                .stream()
                .filter(this::isInTheSameClass)
                .toList();
    }

    private Optional<MethodDeclaration> findMostSimilarMethod(List<MethodDeclaration> methods) {

        MethodDeclaration mostSimilarMethod = null;
        double maxSimilarity = -1;

        for (MethodDeclaration method : methods) {
            double similarity = MethodDeclarationComparator.compare(this.methodDeclaration, method);
            if (similarity >= maxSimilarity) {
                maxSimilarity = similarity;
                mostSimilarMethod = method;
            }
        }

        if (mostSimilarMethod != null && maxSimilarity >= MINIMAL_LIMIT_SIMILARITY) {
            return Optional.of(mostSimilarMethod);
        }
        return Optional.empty();
    }

}
