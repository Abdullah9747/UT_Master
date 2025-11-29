public final class MathOps {

    private MathOps() {
        // Prevent instantiation
    }

    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    // Example main (optional test)
    public static void main(String[] args) {
        System.out.println("Add 5 + 3 = " + add(5, 3));
        System.out.println("Subtract 5 - 3 = " + subtract(5, 3));
    }
}