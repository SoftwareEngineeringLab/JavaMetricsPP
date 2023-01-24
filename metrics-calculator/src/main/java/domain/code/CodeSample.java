package domain.code;

import com.github.javaparser.ast.Node;
import domain.git.Commit;
import domain.git.Repository;
import exception.RangeNotFoundException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class CodeSample {

    protected int id;
    protected Repository repository;
    protected String fileRelativePath;
    protected String fileAbsolutePath;
    protected int startLine;
    protected int endLine;
    protected CodeSampleType type;
    protected List<Commit> commits;
    protected List<Metric> calculatedMetrics;

    private boolean isProcessed = true;

    public CodeSample(int id,
                      Repository repository,
                      String fileRelativePath,
                      String fileAbsolutePath,
                      int startLine,
                      int endLine,
                      CodeSampleType type) {
        this.id = id;
        this.repository = repository;
        this.fileRelativePath = fileRelativePath;
        this.fileAbsolutePath = fileAbsolutePath;
        this.startLine = startLine;
        this.endLine = endLine;
        this.type = type;
        this.calculatedMetrics = new ArrayList<>();
    }


    public void calculateProductMetrics() {
        this.calculatedMetrics.addAll(getCalculatedProductMetrics());
    }

    public void calculateProcessMetrics() {
        this.calculatedMetrics.addAll(getCalculatedProcessMetrics());
    }

    protected abstract List<Metric> getCalculatedProductMetrics();

    protected abstract List<Metric> getCalculatedProcessMetrics();

    protected boolean beginsInTheSameLine(Node otherCodeSampleDeclaration) {
        return otherCodeSampleDeclaration.getRange()
                .map(range -> range.begin.line == startLine)
                .orElseThrow(RangeNotFoundException::new);
    }

    protected boolean endsInTheSameLine(Node otherCodeSampleDeclaration) {
        return otherCodeSampleDeclaration.getRange()
                .map(range -> range.end.line == endLine)
                .orElseThrow(RangeNotFoundException::new);
    }

    public boolean isAvailable() {
        return repository.isAvailable();
    }

    public String getMetricValueAsString(MetricName metricName) {
        return calculatedMetrics.stream()
                .filter(metric -> metric.hasTheSameName(metricName))
                .findAny()
                .map(metric -> metric.getValue().toString())
                .orElse("N/A");
    }

}
