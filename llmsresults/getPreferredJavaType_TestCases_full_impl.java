import org.junit.Test;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class PreferredJavaTypeTest {

    private static final Map<Integer, String> _preferredJavaTypeForSqlType = new HashMap<>();

    static {
        _preferredJavaTypeForSqlType.put(Types.VARCHAR, "java.lang.String");
        _preferredJavaTypeForSqlType.put(Types.INTEGER, "java.lang.Integer");
        _preferredJavaTypeForSqlType.put(Types.NUMERIC, "java.lang.Double");
        _preferredJavaTypeForSqlType.put(Types.DECIMAL, "java.math.BigDecimal");
    }

    @Test
    public void testCase1() {
        assertEquals("java.lang.Boolean", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 1, 0));
    }

    @Test
    public void testCase2() {
        assertEquals("java.lang.Byte", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 2, 0));
    }

    @Test
    public void testCase3() {
        assertEquals("java.lang.Short", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 4, 0));
    }

    @Test
    public void testCase4() {
        assertEquals("java.lang.Integer", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 9, 0));
    }

    @Test
    public void testCase5() {
        assertEquals("java.lang.Long", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 18, 0));
    }

    @Test
    public void testCase6() {
        assertEquals("java.math.BigDecimal", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 20, 0));
    }

    @Test
    public void testCase7() {
        assertEquals("java.lang.Double", PreferredJavaType.getPreferredJavaType(Types.NUMERIC, 10, 2));
    }

    @Test
    public void testCase8() {
        assertEquals("java.lang.String", PreferredJavaType.getPreferredJavaType(Types.VARCHAR, 50, 0));
    }

    @Test
    public void testCase9() {
        assertEquals("java.lang.Object", PreferredJavaType.getPreferredJavaType(9999, 10, 0));
    }

    @Test
    public void testCase10() {
        assertEquals("java.math.BigDecimal", PreferredJavaType.getPreferredJavaType(Types.DECIMAL, 10, 1));
    }

    @Test
    public void testCase11() {
        assertEquals("java.lang.Boolean", PreferredJavaType.getPreferredJavaType(Types.NUMERIC, 1, 0));
    }
}
