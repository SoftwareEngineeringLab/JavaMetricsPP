package input.mapper;

import input.header.ProductMetricsCallableHeader;
import input.row.CodeSampleType;
import input.row.ProductMetricsCallableRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductMetricsCallableRowMapper {

    public static ProductMetricsCallableRow from(CSVRecord record) {
        String sampleId = record.get(ProductMetricsCallableHeader.SampleId);
        String sampleType = record.get(ProductMetricsCallableHeader.SampleType);
        String mcCabeCyclomaticComplexity = record.get(ProductMetricsCallableHeader.McCabeCyclomaticComplexity);
        String linesOfCodeInCallable = record.get(ProductMetricsCallableHeader.LinesOfCodeInCallable);
        String numberOfLambdaExpressionsInCallable = record.get(ProductMetricsCallableHeader.NumberOfLambdaExpressionsInCallable);
        String numberOfMethodReferencesPerCallable = record.get(ProductMetricsCallableHeader.NumberOfMethodReferencesPerCallable);

        return ProductMetricsCallableRow.builder()
                .sampleId(sampleId)
                .codeSampleType(CodeSampleType.valueOf(sampleType))
                .mcCabeCyclomaticComplexity(mcCabeCyclomaticComplexity)
                .linesOfCodeInCallable(linesOfCodeInCallable)
                .numberOfLambdaExpressionsInCallable(numberOfLambdaExpressionsInCallable)
                .numberOfMethodReferencesPerCallable(numberOfMethodReferencesPerCallable)
                .build();
    }

}
