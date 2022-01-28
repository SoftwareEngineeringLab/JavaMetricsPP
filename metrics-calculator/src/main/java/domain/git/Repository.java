package domain.git;

import exception.DeveloperNotFoundException;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import utils.WordComparator;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@ToString
public class Repository {

    private final String addressURI;
    private final String absolutePath;
    private final String userName;
    private final String projectName;
    private final String currentHashCommit;
    private final RepositoryStatus status;
    private final LocalDate currentDate;
    private final List<Developer> developers;

    public boolean isAvailable() {
        return status.isAvailable();
    }

    public boolean isUnavailable() {
        return status.isUnavailable();
    }

    public Developer getDeveloper(String mail) {
        return getDeveloperWithMail(mail)
                .orElseGet(() -> getDeveloperWithMostSimilarMail(mail));
    }

    private Optional<Developer> getDeveloperWithMail(String mail) {
        return developers.stream()
                .filter(developer -> developer.getMail().equals(mail))
                .findAny();
    }

    private Developer getDeveloperWithMostSimilarMail(String mail) {
        return developers
                .stream()
                .max(Comparator.comparingDouble(developer -> getMailsSimilarity(developer.getMail(), mail)))
                .orElseThrow(() -> new DeveloperNotFoundException(mail));
    }

    private double getMailsSimilarity(String mailFirst, String mailSecond) {
        return WordComparator.compare(mailFirst, mailSecond);
    }

}
