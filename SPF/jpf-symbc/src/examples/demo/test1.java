
public class test1 {

    // Target method for symbolic execution
    public static void test(int a, int b, int c) {
        int arr[] = new int[3];

        if (a > b) {
            if (a > c) {
                arr[0] = a;
                if (b > c) {
                    arr[1] = b;
                    arr[2] = c;
                } else {
                    arr[1] = c;
                    arr[2] = b;
                }
            } else {
                arr[0] = c;
                arr[1] = a;
                arr[2] = b;
            }
        } else {
            if (b > c) {
                arr[0] = b;
                if (a > c) {
                    arr[1] = a;
                    arr[2] = c;
                } else {
                    arr[1] = c;
                    arr[2] = a;
                }
            } else {
                arr[0] = c;
                arr[1] = b;
                arr[2] = a;
            }
        }

        // Print the array to show the order
        System.out.println("Order: " + arr[0] + ", " + arr[1] + ", " + arr[2]);
    }

    public static void main(String[] args) {
        test(2, 1, 0);

    }
}
