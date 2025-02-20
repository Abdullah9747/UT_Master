import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MyClassTest {

    @Test
    public void testGetPreferredJavaType1() {
        MyClass myClass = new MyClass();
        assertEquals("java.sql.Time", myClass.getPreferredJavaType(12, 8, 0));
    }

    @Test
    public void testGetPreferredJavaType2() {
        MyClass myClass = new MyClass();
        assertEquals("java.lang.String", myClass.getPreferredJavaType(12, 9, 0));
    }

    @Test
    public void testGetPreferredJavaType3() {
        MyClass myClass = new MyClass();
        assertEquals("java.sql.Date", myClass.getPreferredJavaType(91, 0, 0));
    }

    @Test
    public void testGetPreferredJavaType4() {
        MyClass myClass = new MyClass();
        assertEquals("java.sql.Timestamp", myClass.getPreferredJavaType(93, 0, 0));
    }

    @Test
    public void testGetPreferredJavaType5() {
        MyClass myClass = new MyClass();
        assertEquals("java.io.InputStream", myClass.getPreferredJavaType(1, 0, 1));
    }

    @Test
    public void testGetPreferredJavaType6() {
        MyClass myClass = new MyClass();
        assertEquals("java.lang.String", myClass.getPreferredJavaType(1, 0, 0));
    }
}
