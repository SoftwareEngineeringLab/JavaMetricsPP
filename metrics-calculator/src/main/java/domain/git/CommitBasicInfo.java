package domain.git;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class CommitBasicInfo {

    private final String hash;
    private final String mail;
    private final String message;
    private final LocalDate date;

}
