package input.mapper;

import input.header.MLCQHeader;
import input.row.MLCQRow;
import input.row.Smell;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MLCQRowMapper {

    public static MLCQRow from(CSVRecord record) {
        String sampleId = record.get(MLCQHeader.SAMPLE_ID);
        Smell smell = SmellMapper.from(record.get(MLCQHeader.SMELL));
        int severity = SeverityMapper.mapToNumber(record.get(MLCQHeader.SEVERITY));

        return MLCQRow.builder()
                .sampleId(sampleId)
                .smell(smell)
                .severity(severity)
                .build();
    }

}
