package mapper;

import input.Row;
import input.RowHeader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.csv.CSVRecord;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RowMapper {

    public static Row from(CSVRecord record) {
        int sampleId = Integer.parseInt(record.get(RowHeader.SAMPLE_ID));
        String type = record.get(RowHeader.TYPE);
        String codeName = record.get(RowHeader.CODE_NAME);
        String repository = record.get(RowHeader.REPOSITORY);
        String commitHash = record.get(RowHeader.COMMIT_HASH);
        String link = record.get(RowHeader.LINK);
        String startLine = record.get(RowHeader.START_LINE);
        String endLine = record.get(RowHeader.END_LINE);
        String filePath = record.get(RowHeader.PATH);
        return Row.builder()
                .sampleId(sampleId)
                .type(type)
                .codeName(codeName)
                .repository(repository)
                .commitHash(commitHash)
                .link(link)
                .startLine(startLine)
                .endLine(endLine)
                .filePath(filePath)
                .build();
    }

}
