package domain.git;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class Commit {

    private final String hash;
    private final String message;
    private final Developer developer;
    private final int addedLines;
    private final int deletedLines;
    private final LocalDate date;

    public int getModifiedLines() {
        return addedLines + deletedLines;
    }

}
