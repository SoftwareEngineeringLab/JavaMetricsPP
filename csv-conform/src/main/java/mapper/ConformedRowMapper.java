package mapper;


import input.Row;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import normalizer.ClassNormalizer;
import normalizer.ConstructorNormalizer;
import normalizer.MethodNormalizer;
import output.ConformedRow;

import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConformedRowMapper {

    private static final Logger LOGGER = Logger.getLogger(ConformedRowMapper.class.getName());

    public static ConformedRow from(Row row) {
        ConformedRow c = new ConformedRow();

        if (row.getType().equals(Row.CLASS_TYPE)) {
            c.setType(ConformedRow.CLASS_TYPE);
            c.setPackageName(ClassNormalizer.mapPackageForClass(row.getCodeName()));
            c.setOuterClassName(ClassNormalizer.mapOuterClassForClass(row.getCodeName()));
            c.setClassName(ClassNormalizer.mapClassNameForClass(row.getCodeName()));
        } else if (row.getType().equals(Row.METHOD_TYPE)) {
            if (row.getCodeName().contains("#")) { // logic for methods
                c.setType(ConformedRow.METHOD_TYPE);
                c.setPackageName(MethodNormalizer.mapPackageForMethod(row.getCodeName()));
                c.setOuterClassName(MethodNormalizer.mapOuterClassForMethod(row.getCodeName()));
                c.setClassName(MethodNormalizer.mapClassNameForMethod(row.getCodeName()));

                c.setMethodName(MethodNormalizer.mapMethodNameForMethod(row.getCodeName()));
                c.setParameters(MethodNormalizer.mapParametersForMethod(row.getCodeName()));
            } else { // logic for constructors
                c.setType(ConformedRow.CONSTRUCTOR_TYPE);
                c.setPackageName(ConstructorNormalizer.mapPackageForConstructor(row.getCodeName()));
                c.setOuterClassName(ConstructorNormalizer.mapOuterClassForConstructor(row.getCodeName()));
                c.setClassName(ConstructorNormalizer.mapClassNameForConstructor(row.getCodeName()));

                c.setMethodName(ConstructorNormalizer.mapMethodNameForConstructor(row.getCodeName()));
                c.setParameters(ConstructorNormalizer.mapParametersForConstructor(row.getCodeName()));
            }
        } else {
            if (LOGGER.isLoggable(Level.WARNING)) {
                LOGGER.warning("Unknown type " + c.getType() + " for input.row: " + row.toString());
            }
        }

        c.setSampleId(row.getSampleId());
        c.setRepository(row.getRepository());
        c.setCommitHash(row.getCommitHash());
        c.setGitSourceFileUrl(row.getLink());
        c.setStartLine(row.getStartLine());
        c.setEndLine(row.getEndLine());
        c.setFilePath(row.getFilePath());

        return c;
    }

}
