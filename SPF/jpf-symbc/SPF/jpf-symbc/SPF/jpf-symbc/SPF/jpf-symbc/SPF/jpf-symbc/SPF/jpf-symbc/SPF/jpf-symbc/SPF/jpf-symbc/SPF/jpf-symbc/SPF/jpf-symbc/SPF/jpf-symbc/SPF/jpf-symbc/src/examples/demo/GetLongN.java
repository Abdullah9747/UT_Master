package demo; import gov.nasa.jpf.symbc.Debug;public class GetLongN {
public static int getLongN(long data, int offset) {
    offset %= 64;
    if ((data & (1L << (offset))) != 0) {
      return 1;
    } else {
      return 0;
    }
  }
public static void main(String[] args){
long x0 = Debug.makeSymbolicLong("x0");
int x1 = Debug.makeSymbolicInteger("x1");
getLongN(x0,x1);
}}