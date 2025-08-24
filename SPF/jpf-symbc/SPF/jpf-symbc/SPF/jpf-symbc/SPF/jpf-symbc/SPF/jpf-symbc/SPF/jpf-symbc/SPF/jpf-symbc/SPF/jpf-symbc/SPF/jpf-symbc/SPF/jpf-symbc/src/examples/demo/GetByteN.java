package demo; import gov.nasa.jpf.symbc.Debug;public class GetByteN {
public static int getByteN(byte data, int offset) {
    offset %= 8;
    if ((data & (1 << (7 - offset))) != 0) {
      return 1;
    } else {
      return 0;
    }
  }
public static void main(String[] args){
int x1 = Debug.makeSymbolicInteger("x1");
getByteN(x1);
}}