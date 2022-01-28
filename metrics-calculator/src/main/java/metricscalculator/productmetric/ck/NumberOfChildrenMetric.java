package metricscalculator.productmetric.ck;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import domain.code.MetricName;
import exception.JavaParserNotConfiguredException;
import metricscalculator.productmetric.ProductMetricClass;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for computing NOC metric.
 * NOC - number of children (number of immediate subclasses of a class)
 */
public class NumberOfChildrenMetric implements ProductMetricClass {

    private static final Logger LOGGER = Logger.getLogger(NumberOfChildrenMetric.class.getName());

    @Override
    public Number getValue(ClassOrInterfaceDeclaration classDeclaration) {
        // TODO: Add pre-parsing for all classes to count child classes. Keep the list in universally accessible map.
        Optional<String> baseClassQualifiedNameOptional = classDeclaration.getFullyQualifiedName();
        if (baseClassQualifiedNameOptional.isPresent()) {
            List<String> possibleSubclassesFiles = getImmediateSubclassFiles(classDeclaration.getNameAsString());
            return possibleSubclassesFiles.stream()
                    .mapToLong(fileName -> getAncestorsNumber(fileName, baseClassQualifiedNameOptional.get()))
                    .sum();
        }
        return 0;
    }

    private long getAncestorsNumber(String fileName,
                                    String baseClassQualifiedName) {
        try {
            CompilationUnit cu = parseFile(fileName);
            return cu.findAll(ClassOrInterfaceDeclaration.class)
                    .stream()
                    .flatMap(classDeclaration -> classDeclaration.resolve().getAncestors().stream())
                    .filter(resolvedReferenceType -> resolvedReferenceType.getQualifiedName().equals(baseClassQualifiedName))
                    .count();
        } catch (IOException | ParseException | JavaParserNotConfiguredException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return 0;
    }

    private List<String> getImmediateSubclassFiles(String baseClassName) {
        return getClassFilesContaining(".*extends\\s+" + baseClassName + ".*");
    }

    @Override
    public MetricName getName() {
        return MetricName.NUMBER_OF_CHILDREN;
    }

}
