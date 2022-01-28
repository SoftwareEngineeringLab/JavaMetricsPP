package input.codesampleprovider;

import domain.code.investigatedcallable.InvestigatedMethod;
import domain.git.Repository;
import input.CSVInputRow;

class InvestigatedMethodMapper implements CodeSampleMapper {

    @Override
    public InvestigatedMethod from(CSVInputRow row,
                                   Repository repository) {
        return InvestigatedMethod.builder()
                .id(row.getSampleId())
                .repository(repository)
                .fileRelativePath(row.getFileRelativePath())
                .fileAbsolutePath(row.getFileAbsolutePath())
                .startLine(row.getStartLine())
                .endLine(row.getEndLine())
                .build();
    }

}
