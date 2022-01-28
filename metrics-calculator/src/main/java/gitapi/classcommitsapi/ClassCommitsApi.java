package gitapi.classcommitsapi;

import domain.code.InvestigatedClass;
import domain.git.Commit;
import domain.git.Repository;
import gitapi.CommitMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import utils.ProcessExecutor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassCommitsApi {

    public static List<Commit> getCommits(InvestigatedClass investigatedClass) {
        Repository repository = investigatedClass.getRepository();

        List<FollowNumStatGitLog> followNumStatGitLogs = getFollowNumStatGitLogs(repository.getAbsolutePath(),
                investigatedClass.getFileRelativePath());

        return getCommits(followNumStatGitLogs, repository);
    }

    private static List<FollowNumStatGitLog> getFollowNumStatGitLogs(String repositoryAbsolutePath,
                                                                     String fileRelativePath) {
        List<String> command = List.of("git", "log", "--oneline", "--follow", "--numstat",
                "--format=%H%n%ae%n%cd%n%s", "--date=format:%Y-%m-%d", fileRelativePath);
        List<String> processLogs = ProcessExecutor.executeCommandAndReturnProcessLogs(repositoryAbsolutePath, command);
        return FollowNumStatGitLogsProvider.from(processLogs);
    }

    private static List<Commit> getCommits(List<FollowNumStatGitLog> followNumStatGitLogs,
                                           Repository repository) {
        return followNumStatGitLogs.stream()
                .map(followNumStatGitLog -> CommitMapper.from(followNumStatGitLog, repository))
                .toList();
    }

}


