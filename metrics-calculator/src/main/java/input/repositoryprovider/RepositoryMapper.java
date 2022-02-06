package input.repositoryprovider;

import domain.git.Developer;
import domain.git.Repository;
import domain.git.RepositoryStatus;
import gitapi.CommitBasicInfoApi;
import gitapi.DevelopersApi;
import gitapi.RepositoryApi;
import input.CSVInputRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoryMapper {

    public static Repository from(CSVInputRow row) {

        String addressURI = row.getRepositoryAddressURI();
        String absolutePath = row.getRepositoryAbsolutePath();
        String userName = getUserName(absolutePath);
        String projectName = getProjectName(absolutePath);
        String currentHashCommit = row.getCurrentHashCommit();
        RepositoryStatus repositoryStatus = RepositoryApi.getRepositoryStatus(addressURI, absolutePath, currentHashCommit);
        LocalDate currentDate = null;
        List<Developer> developers = null;
        if (repositoryStatus.isAvailable()) {
            currentDate = getCurrentDate(absolutePath, currentHashCommit);
            developers = getDevelopers(absolutePath);
        }
        return Repository.builder()
                .addressURI(addressURI)
                .absolutePath(absolutePath)
                .userName(userName)
                .projectName(projectName)
                .currentHashCommit(currentHashCommit)
                .status(repositoryStatus)
                .currentDate(currentDate)
                .developers(developers)
                .build();
    }

    private static LocalDate getCurrentDate(String repositoryAbsolutePath,
                                            String currentHashCommit) {
        return CommitBasicInfoApi.getCommitBasicInfo(repositoryAbsolutePath, currentHashCommit)
                .getDate();
    }

    private static List<Developer> getDevelopers(String repositoryAbsolutePath) {
        return DevelopersApi.getDevelopers(repositoryAbsolutePath);
    }

    private static String getUserName(String repositoryAbsolutePath) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] repositoryPathElements = repositoryAbsolutePath.split(pattern);
        return repositoryPathElements[repositoryPathElements.length - 2];
    }

    private static String getProjectName(String repositoryAbsolutePath) {
        String pattern = Pattern.quote(System.getProperty("file.separator"));
        String[] repositoryPathElements = repositoryAbsolutePath.split(pattern);
        return repositoryPathElements[repositoryPathElements.length - 1];
    }

}
