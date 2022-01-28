package domain.code;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.git.Repository;
import exception.CodeSampleNotFoundException;
import gitapi.classcommitsapi.ClassCommitsApi;
import lombok.Builder;
import lombok.Getter;
import metricscalculator.processmetric.ProcessMetricsCalculator;
import metricscalculator.productmetric.ProductMetricsClassCalculator;
import utils.CompilationUnitProvider;

import java.util.List;

@Getter
public class InvestigatedClass extends CodeSample {

    @Builder
    private InvestigatedClass(int id,
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
                CodeSampleType.CLASS);
    }

    @Override
    protected List<Metric> getCalculatedProductMetrics() {
        ClassOrInterfaceDeclaration classDeclaration = getClassDeclaration();
        return ProductMetricsClassCalculator.calculateMetricsWithoutDependencyResolution(classDeclaration);
    }

    private ClassOrInterfaceDeclaration getClassDeclaration() {
        CompilationUnit compilationUnit = CompilationUnitProvider.from(fileAbsolutePath);
        return compilationUnit.findAll(ClassOrInterfaceDeclaration.class)
                .stream()
                .filter(this::beginsInTheSameLine)
                .filter(this::endsInTheSameLine)
                .findAny()
                .orElseThrow(() -> new CodeSampleNotFoundException(this.id));
    }

    @Override
    protected List<Metric> getCalculatedProcessMetrics() {
        this.commits = ClassCommitsApi.getCommits(this);
        return ProcessMetricsCalculator.calculateAllMetrics(this);
    }

}
