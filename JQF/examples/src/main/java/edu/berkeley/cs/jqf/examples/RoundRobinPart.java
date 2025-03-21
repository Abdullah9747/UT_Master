package edu.berkeley.cs.jqf.examples;

public class RoundRobinPart {
public static int[] roundRobinPart(int objectCount, int count, int index) {
        if (objectCount < 0 || index < 0 || count < 1 || index >= count) {
            throw new IllegalArgumentException("objectCount=" + objectCount + ", count=" + count + ", index=" + index);
        }

        int[] res = new int[objectCount / count + (objectCount % count > index ? 1 : 0)];
        for (int i = 0, j = index; j < objectCount; i++, j += count) {
            res[i] = j;
        }
        return res;
    }
public static void main(String[] args) {


roundRobinPart(64, 72, 86);
}
}