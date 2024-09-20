public class test1 {

    // Target method for symbolic execution
    public static void test(int a, int b) {
        int denominator = b + a - 2;
        
        if (denominator == 0) {
            System.out.println("Error: Division by zero");
            return;
        }

        int c = a / denominator;
        if (c > 0)
            System.out.println(">0");
        else
            System.out.println("<=0");
    }

    public static void main(String[] args) {
        test(0, 0);  // Will handle the division by zero case
    }
}
