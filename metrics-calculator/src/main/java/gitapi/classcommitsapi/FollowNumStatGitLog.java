package gitapi.classcommitsapi;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class FollowNumStatGitLog {

    private final String hash;
    private final String mail;
    private final LocalDate date;
    private final String message;
    private final int addedLines;
    private final int deletedLines;

}
