package demo; import gov.nasa.jpf.symbc.Debug;public class IntToBytes {
public static byte[] intToBytes(int i) {
    return new byte[]{(byte) ((i >> 24) & 0xFF), (byte) ((i >> 16) & 0xFF),
        (byte) ((i >> 8) & 0xFF),
        (byte) (i & 0xFF)};
  }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
intToBytes(x0);
}}