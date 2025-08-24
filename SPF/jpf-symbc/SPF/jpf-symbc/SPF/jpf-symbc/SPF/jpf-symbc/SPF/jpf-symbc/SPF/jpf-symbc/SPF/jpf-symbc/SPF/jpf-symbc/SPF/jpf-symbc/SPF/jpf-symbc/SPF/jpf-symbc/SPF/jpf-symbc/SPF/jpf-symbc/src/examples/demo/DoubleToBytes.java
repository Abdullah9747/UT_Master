package demo; import gov.nasa.jpf.symbc.Debug;public class DoubleToBytes {
public static byte[] doubleToBytes(double data) {
    byte[] bytes = new byte[8];
    long value = Double.doubleToLongBits(data);
    for (int i = 7; i >= 0; i--) {
      bytes[i] = new Long(value).byteValue();
      value = value >> 8;
    }
    return bytes;
  }
public static void main(String[] args){
double x0 = Debug.makeSymbolicReal("x0");
doubleToBytes(x0);
}}