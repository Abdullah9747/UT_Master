import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Types;

public class GetPreferredJavaTypeTest {

    @Test
    public void testCase1() {
        assertEquals("java.lang.String", getPreferredJavaType(Types.VARCHAR, 50, 0));
    }

    @Test
    public void testCase2() {
        assertEquals("java.lang.String", getPreferredJavaType(Types.CHAR, 1, 0));
    }

    @Test
    public void testCase3() {
        assertEquals("java.lang.String", getPreferredJavaType(Types.LONGVARCHAR, 2000, 0));
    }

    @Test
    public void testCase4() {
        assertEquals("java.lang.Integer", getPreferredJavaType(Types.INTEGER, 5, 0));
    }

    @Test
    public void testCase5() {
        assertEquals("java.lang.Long", getPreferredJavaType(Types.INTEGER, 10, 0));
    }

    @Test
    public void testCase6() {
        assertEquals("java.lang.Integer", getPreferredJavaType(Types.SMALLINT, 4, 0));
    }

    @Test
    public void testCase7() {
        assertEquals("java.lang.Integer", getPreferredJavaType(Types.TINYINT, 3, 0));
    }

    @Test
    public void testCase8() {
        assertEquals("java.lang.Long", getPreferredJavaType(Types.BIGINT, 19, 0));
    }

    @Test
    public void testCase9() {
        assertEquals("java.math.BigDecimal", getPreferredJavaType(Types.DECIMAL, 10, 2));
    }

    @Test
    public void testCase10() {
        assertEquals("java.lang.Long", getPreferredJavaType(Types.DECIMAL, 10, 0));
    }

    @Test
    public void testCase11() {
        assertEquals("java.math.BigDecimal", getPreferredJavaType(Types.NUMERIC, 5, 3));
    }

    @Test
    public void testCase12() {
        assertEquals("java.lang.Long", getPreferredJavaType(Types.NUMERIC, 5, 0));
    }

    @Test
    public void testCase13() {
        assertEquals("java.lang.Float", getPreferredJavaType(Types.FLOAT, 7, 0));
    }

    @Test
    public void testCase14() {
        assertEquals("java.lang.Float", getPreferredJavaType(Types.REAL, 7, 0));
    }

    @Test
    public void testCase15() {
        assertEquals("java.lang.Double", getPreferredJavaType(Types.DOUBLE, 15, 0));
    }

    @Test
    public void testCase16() {
        assertEquals("java.lang.Boolean", getPreferredJavaType(Types.BOOLEAN, 1, 0));
    }

    @Test
    public void testCase17() {
        assertEquals("java.sql.Date", getPreferredJavaType(Types.DATE, 0, 0));
    }

    @Test
    public void testCase18() {
        assertEquals("java.sql.Time", getPreferredJavaType(Types.TIME, 0, 0));
    }

    @Test
    public void testCase19() {
        assertEquals("java.sql.Timestamp", getPreferredJavaType(Types.TIMESTAMP, 0, 0));
    }

    @Test
    public void testCase20() {
        assertEquals("java.sql.Blob", getPreferredJavaType(Types.BLOB, 0, 0));
    }

    @Test
    public void testCase21() {
        assertEquals("java.sql.Clob", getPreferredJavaType(Types.CLOB, 0, 0));
    }

    @Test
    public void testCase22() {
        assertEquals("java.lang.Object", getPreferredJavaType(-99, 0, 0));
    }

    private String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
        if (sqlType == Types.VARCHAR || sqlType == Types.CHAR || sqlType == Types.LONGVARCHAR) {
            return "java.lang.String";
        } else if (sqlType == Types.INTEGER) {
            if (size > 9) {
                return "java.lang.Long";
            } else {
                return "java.lang.Integer";
            }
        } else if (sqlType == Types.SMALLINT || sqlType == Types.TINYINT) {
            return "java.lang.Integer";
        } else if (sqlType == Types.BIGINT) {
            return "java.lang.Long";
        } else if (sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) {
            if (decimalDigits > 0) {
                return "java.math.BigDecimal";
            } else {
                return "java.lang.Long";
            }
        } else if (sqlType == Types.FLOAT || sqlType == Types.REAL) {
            return "java.lang.Float";
        } else if (sqlType == Types.DOUBLE) {
            return "java.lang.Double";
        } else if (sqlType == Types.BOOLEAN) {
            return "java.lang.Boolean";
        } else if (sqlType == Types.DATE) {
            return "java.sql.Date";
        } else if (sqlType == Types.TIME) {
            return "java.sql.Time";
        } else if (sqlType == Types.TIMESTAMP) {
            return "java.sql.Timestamp";
        } else if (sqlType == Types.BLOB) {
            return "java.sql.Blob";
        } else if (sqlType == Types.CLOB) {
            return "java.sql.Clob";
        }
        return "java.lang.Object";
    }
}
