import java.io.*;

public class DeserializeCorpus {
    public static void main(String[] args) {
        File file = new File("E:/FYP/UT_Master/TestValGen/JQF-wsl/java-fuzzing-example/fuzz-results/corpus/id_000000");

        try (FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {

            Object obj = in.readObject();
            System.out.println("Deserialized Object: " + obj.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
