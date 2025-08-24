import coral.util.Range;
import coral.solvers.rand.NumberGenerator;

public class TestNumberGenerator {
    public static void main(String[] args) {
        try {
            Range range = new Range(1, 100);  // Range uses ints
            NumberGenerator ng = new NumberGenerator(range);

            int randomInt = ng.genInt();  // Random int from full range
            double randomDouble = ng.genDouble(0.0, 1.0);  // From specified range

            System.out.println(" Random Int: " + randomInt);
            System.out.println(" Random Double: " + randomDouble);
        } catch (Exception e) {
            System.err.println(" Failed to use Coral NumberGenerator:");
            e.printStackTrace();
        }
    }
}
