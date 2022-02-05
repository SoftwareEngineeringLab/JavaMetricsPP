package input.repositoryprovider;

import domain.git.Repository;
import input.CSVInputRow;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RepositoriesProvider {

    public static List<Repository> from(List<CSVInputRow> rows) {

        List<CSVInputRow> distinctRows = getDistinctRowsByRepositoryAddressURI(rows);
        List<Repository> repositories = new ArrayList<>();

        int doneElements = 0;
        int allElements = distinctRows.size();

        for (CSVInputRow row : distinctRows) {
            Repository repository = RepositoryMapper.from(row);
            repositories.add(repository);
            doneElements++;
            System.out.println(repository);
            System.out.println("Done repositories : " + doneElements + "/" + allElements);
        }
        return repositories;
    }

    private static List<CSVInputRow> getDistinctRowsByRepositoryAddressURI(List<CSVInputRow> rows) {
        return io.vavr.collection.List.ofAll(rows)
                .distinctBy(CSVInputRow::getRepositoryAddressURI)
                .toJavaList();
    }

}
