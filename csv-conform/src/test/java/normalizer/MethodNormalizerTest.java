package normalizer;

import input.Row;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodNormalizerTest {

    private static Row methodNoArgsUUT;
    private static Row methodArgsUUT;
    private static Row methodArgsGenericUUT;
    private static Row noPackageMethodUUT;

    private static List<Row> rowsUUT;

    @BeforeAll
    static void setUp() {
        methodNoArgsUUT = new Row(1, "function", "org.test.package.ClassName#methodName", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");
        methodArgsUUT = new Row(2, "function", "org.test.package.OuterClass.ClassName#methodName arg1|arg2|arg3", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");
        methodArgsGenericUUT = new Row(3, "function", "org.test.package.ClassName#methodName arg1<Generic>|arg2|arg3<String, Integer>", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");
        noPackageMethodUUT = new Row(4, "function", "ClassName#methodName", "test@github.com", "1EC2", "https://test.com", "30", "35", "filePath");

        rowsUUT = Arrays.asList(methodNoArgsUUT, methodArgsUUT, methodArgsGenericUUT);
        rowsUUT = CSVNormalizer.cleanseCSV(rowsUUT);
    }

    @Test
    void mapPackageForMethodTest() {
        for (Row r : rowsUUT) {
            String codeName = r.getCodeName();
            assertEquals("org.test.package", MethodNormalizer.mapPackageForMethod(codeName));
        }
    }

    @Test
    void mapClassNameForMethodTest() {
        for (Row r : rowsUUT) {
            String codeName = r.getCodeName();
            assertEquals("ClassName", MethodNormalizer.mapClassNameForMethod(codeName));
        }
    }

    @Test
    void mapPackageForMethodNoPackageTest() {
        String codeName = noPackageMethodUUT.getCodeName();
        assertEquals("", MethodNormalizer.mapPackageForMethod(codeName));
    }

    @Test
    void mapClassNameForMethodNoPackageTest() {
        String codeName = noPackageMethodUUT.getCodeName();
        assertEquals("ClassName", MethodNormalizer.mapClassNameForMethod(codeName));
    }

    @Test
    void mapOuterClassForMethodNoPackageTest() {
        String codeName = noPackageMethodUUT.getCodeName();
        assertEquals("", MethodNormalizer.mapOuterClassForMethod(codeName));
    }

    @Test
    void mapOuterClassForMethodSingleOuterClassTest() {
        String codeName = methodArgsUUT.getCodeName();
        assertEquals("OuterClass", MethodNormalizer.mapOuterClassForMethod(codeName));
    }

    @Test
    void mapMethodNameForMethodTest() {
        for (Row r : rowsUUT) {
            String codeName = r.getCodeName();
            assertEquals("methodName", MethodNormalizer.mapMethodNameForMethod(codeName));
        }
    }

    @Test
    void mapParametersForMethodNoArgsTest() {
        String codeName = methodNoArgsUUT.getCodeName();
        assertEquals(0, MethodNormalizer.mapParametersForMethod(codeName).size());
    }

    @Test
    void mapParametersForMethodArgsTest() {
        String codeName = methodArgsUUT.getCodeName();
        List<String> params = MethodNormalizer.mapParametersForMethod(codeName);

        assertEquals(3, params.size());

        assertEquals("arg1", params.get(0));
        assertEquals("arg2", params.get(1));
        assertEquals("arg3", params.get(2));
    }

    @Test
    void mapParametersForMethodArgsGenericTest() {
        String codeName = methodArgsGenericUUT.getCodeName();
        List<String> params = MethodNormalizer.mapParametersForMethod(codeName);

        assertEquals(3, params.size());

        assertEquals("arg1", params.get(0));
        assertEquals("arg2", params.get(1));
        assertEquals("arg3", params.get(2));
    }

}
