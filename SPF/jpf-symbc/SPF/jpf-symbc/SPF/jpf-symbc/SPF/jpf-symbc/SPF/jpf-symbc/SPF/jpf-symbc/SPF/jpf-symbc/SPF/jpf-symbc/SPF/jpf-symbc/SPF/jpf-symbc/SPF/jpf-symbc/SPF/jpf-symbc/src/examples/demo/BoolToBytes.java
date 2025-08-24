package demo; import gov.nasa.jpf.symbc.Debug;public class BoolToBytes {
public static byte[] boolToBytes(boolean x) {
    byte[] b = new byte[1];
    if (x) {
      b[0] = 1;
    } else {
      b[0] = 0;
    }
    return b;
  }
public static void main(String[] args){
boolean x0 = Debug.makeSymbolicBoolean("x0");
boolToBytes(x0);
}}