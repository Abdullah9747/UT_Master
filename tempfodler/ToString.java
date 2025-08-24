package demo; import gov.nasa.jpf.symbc.Debug;public class ToString {
public static String toString(int value) {
        if (value == 0) {
            return "0";
        }

        int count = 2, j = value;
        boolean negative = value < 0;
        if (!negative) {
            count = 1;
            j = -value;
        }
        while ((value /= 10) != 0) {
            count++;
        }

        char[] buffer = new char[count];
        do {
            int ch = 0 - (j % 10);
            ch += '0';
            buffer[--count] = (char) ch;
        } while ((j /= 10) != 0);
        if (negative) {
            buffer[0] = '-';
        }
        return new String(buffer);
    }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
toString(x0);
}}