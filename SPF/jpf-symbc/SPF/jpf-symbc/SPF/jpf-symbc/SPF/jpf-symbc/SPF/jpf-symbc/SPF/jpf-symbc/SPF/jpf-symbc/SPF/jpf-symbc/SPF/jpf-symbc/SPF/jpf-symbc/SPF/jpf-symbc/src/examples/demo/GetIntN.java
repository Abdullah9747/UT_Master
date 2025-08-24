package demo; import gov.nasa.jpf.symbc.Debug;public class GetIntN {
public static int getIntN(int data, int offset) {
    offset %= 32;
    if ((data & (1 << (offset))) != 0) {
      return 1;
    } else {
      return 0;
    }
  }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
int x1 = Debug.makeSymbolicInteger("x1");
getIntN(x0,x1);
}}