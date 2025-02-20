import org.junit.Test;
import static org.junit.Assert.*;
import com.google.common.primitives.Ints;
import java.nio.ByteBuffer;

public class MyClassTest { // Replace MyClass with the actual class name

    public static int hash(int data, int seed) {
        return hash(ByteBuffer.wrap(Ints.toByteArray(data)), seed);
    }

    public static int hash(ByteBuffer buf, int seed) {
        // Simulate the actual hash function that takes a ByteBuffer and seed.
        // Replace this with the actual implementation!
        int m = 0x5bd1e995;
        int r = 24;

        int h = seed ^ buf.remaining();

        while (buf.remaining() >= 4) {
            int k = buf.getInt();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h *= m;
            h ^= k;
        }

        switch (buf.remaining()) {
            case 3:
                h ^= (buf.get(2) & 0xff) << 16;
            case 2:
                h ^= (buf.get(1) & 0xff) << 8;
            case 1:
                h ^= (buf.get(0) & 0xff);
                h *= m;
        }

        h ^= h >>> 13;
        h *= m;
        h ^= h >>> 15;

        return h;
    }

    @Test
    public void testHash_Zero() {
        hash(0, 0);
    }

    @Test
    public void testHash_One() {
        hash(1, 1);
    }

    @Test
    public void testHash_MaxInt() {
        hash(Integer.MAX_VALUE, 0);
    }

    @Test
    public void testHash_MinInt() {
        hash(Integer.MIN_VALUE, 0);
    }

    @Test
    public void testHash_NegativeOne() {
        hash(-1, 0);
    }

    @Test
    public void testHash_Prime() {
        hash(101, 0);
    }

    @Test
    public void testHash_LargePositive() {
        hash(1000000, 0);
    }

    @Test
    public void testHash_LargeNegative() {
        hash(-1000000, 0);
    }

    @Test
    public void testHash_DifferentSeeds() {
        hash(10, 5);
        hash(10, 10);
        hash(10, -5);
    }
}
