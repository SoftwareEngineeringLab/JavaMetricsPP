package gitapi.callablecommitsapi.fileversionsapi;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
class FollowNameOnlyGitLog {

    private final String hash;
    private final String fileRelativePath;

}
