package input.codesampleprovider;

import domain.code.investigatedcallable.InvestigatedConstructor;
import domain.git.Repository;
import input.CSVInputRow;

class InvestigatedConstructorMapper implements CodeSampleMapper {

    @Override
    public InvestigatedConstructor from(CSVInputRow row,
                                        Repository repository) {
        return InvestigatedConstructor.builder()
                .id(row.getSampleId())
                .repository(repository)
                .fileRelativePath(row.getFileRelativePath())
                .fileAbsolutePath(row.getFileAbsolutePath())
                .startLine(row.getStartLine())
                .endLine(row.getEndLine())
                .build();
    }

}
