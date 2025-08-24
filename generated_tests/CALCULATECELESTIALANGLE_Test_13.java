import org.junit.Assert;
import org.junit.Test;

public class CALCULATECELESTIALANGLETest {

    @Test
    public void testCase1() {
        float result = CALCULATECELESTIALANGLE.calculateCelestialAngle(0, 0.0f);
        Assert.assertEquals(0.0f, result, 0.0f);
    }

    @Test
    public void testCase2() {
        float result = CALCULATECELESTIALANGLE.calculateCelestialAngle(1, 1.0f);
        Assert.assertEquals(0.0f, result, 0.0f);
    }
}