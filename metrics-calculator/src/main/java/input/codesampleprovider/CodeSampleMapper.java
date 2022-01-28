package input.codesampleprovider;

import domain.code.CodeSample;
import domain.git.Repository;
import input.CSVInputRow;

public interface CodeSampleMapper {

    CodeSample from(CSVInputRow row, Repository repository);

}
