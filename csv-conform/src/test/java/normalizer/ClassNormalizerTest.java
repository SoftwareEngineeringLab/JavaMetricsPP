package normalizer;

import input.Row;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassNormalizerTest {

    private static Row classUUT;
    private static Row classWithOuterClassUUT;
    private static Row classWithOuterClassChainUUT;
    private static Row classWithGenericUUT;
    private static Row noPackageClassUUT;
    private static Row classWithStaticInnerClassUUT;

    private static List<Row> rowsUUT;

    @BeforeAll
    static void setUp() {
        classUUT = new Row(1, "class", "org.test.package.ClassName", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");
        classWithOuterClassUUT = new Row(2, "class", "org.test.package.OuterClass1.ClassName", "test@github.com", "1EC3", "https://test.com", "30", "35", "filePath");
        classWithOuterClassChainUUT = new Row(3, "class", "org.test.package.OuterClass1.OuterClass2.ClassName", "test@github.com", "1EC4", "https://test.com", "30", "35", "filePath");
        classWithGenericUUT = new Row(4, "class", "org.test.package.OuterClass1.OuterClass2.ClassName<T>", "test@github.com", "1EC4", "https://test.com", "30", "35", "filePath");
        noPackageClassUUT = new Row(9, "class", "ClassName", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");
        classWithStaticInnerClassUUT = new Row(10, "class", "Foo.Mumble", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");

        rowsUUT = Arrays.asList(classUUT, classWithOuterClassUUT, classWithOuterClassChainUUT, classWithGenericUUT);
        rowsUUT = CSVNormalizer.cleanseCSV(rowsUUT);
    }

    @Test
    void mapPackageForClassTest() {
        for (Row r : rowsUUT) {
            String codeName = r.getCodeName();
            assertEquals("org.test.package", ClassNormalizer.mapPackageForClass(codeName));
        }
    }

    @Test
    void mapClassNameForClassTest() {
        for (Row r : rowsUUT) {
            String codeName = r.getCodeName();
            assertEquals("ClassName", ClassNormalizer.mapClassNameForClass(codeName));
        }
    }

    @Test
    void mapPackageForClassNoPackageTest() {
        String codeName = noPackageClassUUT.getCodeName();
        assertEquals("", ClassNormalizer.mapPackageForClass(codeName));
    }

    @Test
    void mapPackageForClassWithStaticInnerClassTest() {
        String codeName = classWithStaticInnerClassUUT.getCodeName();
        assertEquals("", ClassNormalizer.mapPackageForClass(codeName));
    }

    @Test
    void mapClassNameForClassNoPackageTest() {
        String codeName = noPackageClassUUT.getCodeName();
        assertEquals("ClassName", ClassNormalizer.mapClassNameForClass(codeName));
    }

    @Test
    void maClassNameForClassWithStaticInnerClassTest() {
        String codeName = classWithStaticInnerClassUUT.getCodeName();
        assertEquals("Mumble", ClassNormalizer.mapClassNameForClass(codeName));
    }

    @Test
    void mapOuterClassForClassSingleOuterClassTest() {
        String codeName = classWithOuterClassUUT.getCodeName();
        assertEquals("OuterClass1", ClassNormalizer.mapOuterClassForClass(codeName));
    }

    @Test
    void mapOuterClassForClassChainOuterClassTest() {
        String codeName = classWithOuterClassChainUUT.getCodeName();
        assertEquals("OuterClass1.OuterClass2", ClassNormalizer.mapOuterClassForClass(codeName));
    }

    @Test
    void mapOuterClassForClassNoOuterClassTest() {
        String codeName = classUUT.getCodeName();
        assertEquals("", ClassNormalizer.mapOuterClassForClass(codeName));
    }

    @Test
    void maOuterClassForClassWithStaticInnerClassTest() {
        String codeName = classWithStaticInnerClassUUT.getCodeName();
        assertEquals("Foo", ClassNormalizer.mapOuterClassForClass(codeName));
    }

}
