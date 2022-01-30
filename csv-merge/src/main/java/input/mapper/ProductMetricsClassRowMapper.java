package input.mapper;

import input.header.ProductMetricsClassHeader;
import input.row.CodeSampleType;
import input.row.ProductMetricsClassRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMetricsClassRowMapper {

    public static ProductMetricsClassRow from(CSVRecord record) {
        String sampleId = record.get(ProductMetricsClassHeader.SampleId);
        String lambdaDensity = record.get(ProductMetricsClassHeader.LambdaDensity);
        String linesOfCodeInClass = record.get(ProductMetricsClassHeader.LinesOfCodeInClass);
        String methodReferenceDensity = record.get(ProductMetricsClassHeader.MethodReferenceDensity);
        String numberOfAccessorMethods = record.get(ProductMetricsClassHeader.NumberOfAccessorMethods);
        String numberOfLambdaExpressionsInClass = record.get(ProductMetricsClassHeader.NumberOfLambdaExpressionsInClass);
        String numberOfMethodReferencesPerClass = record.get(ProductMetricsClassHeader.NumberOfMethodReferencesPerClass);
        String numberOfMethods = record.get(ProductMetricsClassHeader.NumberOfMethods);
        String numberOfMutatorMethods = record.get(ProductMetricsClassHeader.NumberOfMutatorMethods);
        String numberOfPublicFields = record.get(ProductMetricsClassHeader.NumberOfPublicFields);
        String numberOfPrivateFields = record.get(ProductMetricsClassHeader.NumberOfPrivateFields);
        String weightedMethodsPerClass = record.get(ProductMetricsClassHeader.WeightedMethodsPerClass);
        String weightedMethodsPerClassWithoutAccessorAndMutatorMethods = record.get(ProductMetricsClassHeader.WeightedMethodsPerClassWithoutAccessorAndMutatorMethods);
        String weightOfClass = record.get(ProductMetricsClassHeader.WeightOfClass);
        String numberOfPublicMethods = record.get(ProductMetricsClassHeader.NumberOfPublicMethods);
        String responseForClass = record.get(ProductMetricsClassHeader.ResponseForClass);
        String numberOfAnnotations = record.get(ProductMetricsClassHeader.NumberOfAnnotations);

        return ProductMetricsClassRow.builder()
                .sampleId(sampleId)
                .codeSampleType(CodeSampleType.CLASS)
                .lambdaDensity(lambdaDensity)
                .linesOfCodeInClass(linesOfCodeInClass)
                .methodReferenceDensity(methodReferenceDensity)
                .numberOfAccessorMethods(numberOfAccessorMethods)
                .numberOfLambdaExpressionsInClass(numberOfLambdaExpressionsInClass)
                .numberOfMethodReferencesPerClass(numberOfMethodReferencesPerClass)
                .numberOfMethods(numberOfMethods)
                .numberOfMutatorMethods(numberOfMutatorMethods)
                .numberOfPublicFields(numberOfPublicFields)
                .numberOfPrivateFields(numberOfPrivateFields)
                .weightedMethodsPerClass(weightedMethodsPerClass)
                .weightedMethodsPerClassWithoutAccessorAndMutatorMethods(weightedMethodsPerClassWithoutAccessorAndMutatorMethods)
                .weightOfClass(weightOfClass)
                .numberOfPublicMethods(numberOfPublicMethods)
                .responseForClass(responseForClass)
                .numberOfAnnotations(numberOfAnnotations)
                .build();
    }

}
