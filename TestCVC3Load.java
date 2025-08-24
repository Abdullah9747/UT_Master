public class TestCVC3Load {
    public static void main(String[] args) {
        try {
            System.loadLibrary("cvc3jni");
            System.out.println(" cvc3jni loaded successfully!");
        } catch (UnsatisfiedLinkError e) {
            System.err.println(" Failed to load cvc3jni:");
            e.printStackTrace();
        }
    }
}
