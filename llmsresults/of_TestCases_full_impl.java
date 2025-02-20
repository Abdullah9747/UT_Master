import org.junit.Test;
import static org.junit.Assert.*;

public class OrdinalTest {

    private static final Ordinal[] FIRST = {
            new Ordinal(1), new Ordinal(2), new Ordinal(3), new Ordinal(4), new Ordinal(5)
    };

    @Test
    public void testOf_positive() {
        assertEquals(FIRST[0], Ordinal.of(1));
    }

    @Test
    public void testOf_upperBoundary() {
        assertEquals(FIRST[FIRST.length - 1], Ordinal.of(FIRST.length));
    }

    @Test
    public void testOf_outOfBoundsPositive() {
        assertEquals(new Ordinal(FIRST.length + 1), Ordinal.of(FIRST.length + 1));
    }

    @Test
    public void testOf_zero() {
        assertEquals(new Ordinal(0), Ordinal.of(0));
    }

    @Test
    public void testOf_negative() {
        assertEquals(new Ordinal(-1), Ordinal.of(-1));
    }

    @Test
    public void testOf_emptyArray() {
        Ordinal[] emptyFirst = {};
        try {
            Ordinal.FIRST = emptyFirst; // Directly modify the static field for this test
            assertEquals(new Ordinal(1), Ordinal.of(1));
        } finally {
            // Restore the original value of FIRST to avoid affecting other tests
            Ordinal.FIRST = new Ordinal[]{
                    new Ordinal(1), new Ordinal(2), new Ordinal(3), new Ordinal(4), new Ordinal(5)
            };
        }
    }

    // Dummy Ordinal class for compilation
    private static class Ordinal {
        private final int value;

        public Ordinal(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Ordinal ordinal = (Ordinal) o;

            return value == ordinal.value;
        }

        @Override
        public int hashCode() {
            return value;
        }
    }

    // Dummy Ordinal class and static FIRST field for compilation
    private static class Ordinal {
        private final int value;

        public Ordinal(int value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Ordinal ordinal = (Ordinal) o;

            return value == ordinal.value;
        }

        @Override
        public int hashCode() {
            return value;
        }

        public static Ordinal of(int oneBased) {
            return oneBased > 0 && oneBased <= FIRST.length ? FIRST[oneBased - 1] : new Ordinal(oneBased);
        }

        public static Ordinal[] FIRST = {
                new Ordinal(1), new Ordinal(2), new Ordinal(3), new Ordinal(4), new Ordinal(5)
        };
    }
}
