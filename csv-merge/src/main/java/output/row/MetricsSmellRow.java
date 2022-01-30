package output.row;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@ToString
public class MetricsSmellRow {

    private final List<String> metrics;
    private final String severity;

    public List<String> getInfoToWrite() {
        List<String> infoToWrite = new ArrayList<>(metrics);
        infoToWrite.add(severity);
        return infoToWrite;
    }

}
