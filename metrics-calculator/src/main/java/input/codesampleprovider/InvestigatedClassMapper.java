package input.codesampleprovider;

import domain.code.InvestigatedClass;
import domain.git.Repository;
import input.CSVInputRow;

class InvestigatedClassMapper implements CodeSampleMapper {

    @Override
    public InvestigatedClass from(CSVInputRow row,
                                  Repository repository) {
        return InvestigatedClass.builder()
                .id(row.getSampleId())
                .repository(repository)
                .fileRelativePath(row.getFileRelativePath())
                .fileAbsolutePath(row.getFileAbsolutePath())
                .startLine(row.getStartLine())
                .endLine(row.getEndLine())
                .build();
    }

}
