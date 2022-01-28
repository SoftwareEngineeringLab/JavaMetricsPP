package gitapi;

import domain.git.Commit;
import domain.git.CommitBasicInfo;
import domain.git.Developer;
import domain.git.Repository;
import gitapi.callablecommitsapi.codesamplemodification.CodeSampleModification;
import gitapi.classcommitsapi.FollowNumStatGitLog;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommitMapper {

    public static Commit from(CodeSampleModification codeSampleModification,
                              Repository repository) {
        CommitBasicInfo commitBasicInfo = CommitBasicInfoApi.getCommitBasicInfo(repository.getAbsolutePath(),
                codeSampleModification.getHash());
        return Commit.builder()
                .hash(codeSampleModification.getHash())
                .message(commitBasicInfo.getMessage())
                .developer(repository.getDeveloper(commitBasicInfo.getMail()))
                .addedLines(codeSampleModification.getAddedLines())
                .deletedLines(codeSampleModification.getDeletedLines())
                .date(commitBasicInfo.getDate())
                .build();
    }

    public static Commit from(FollowNumStatGitLog followNumStatGitLog,
                              Repository repository) {
        String hash = followNumStatGitLog.getHash();
        Developer developer = repository.getDeveloper(followNumStatGitLog.getMail());
        LocalDate date = followNumStatGitLog.getDate();
        String message = followNumStatGitLog.getMessage();
        int addedLines = followNumStatGitLog.getAddedLines();
        int deletedLines = followNumStatGitLog.getDeletedLines();
        return Commit.builder()
                .hash(hash)
                .developer(developer)
                .date(date)
                .message(message)
                .addedLines(addedLines)
                .deletedLines(deletedLines)
                .build();
    }

}
