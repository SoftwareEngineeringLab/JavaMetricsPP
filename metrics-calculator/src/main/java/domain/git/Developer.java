package domain.git;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Developer {

    private final String mail;
    private final int commits;

}
