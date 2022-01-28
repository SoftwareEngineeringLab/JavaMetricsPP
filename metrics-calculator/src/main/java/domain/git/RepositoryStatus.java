package domain.git;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RepositoryStatus {

    private final RepositoryStatusType statusType;
    private final String errorLog;

    public boolean isAvailable() {
        return this.statusType == RepositoryStatusType.AVAILABLE;
    }

    public boolean isUnavailable() {
        return this.statusType == RepositoryStatusType.UNAVAILABLE;
    }

}
