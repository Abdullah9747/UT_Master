package demo; import gov.nasa.jpf.symbc.Debug;public class ConvertBase {
public static String convertBase(int number, int base) {
    // special case
    if (number == 0)
      return "0";

    if (base > 62)
      throw new IllegalArgumentException("base must be 62 or less: base=" + base);
    if (base <= 0)
      throw new IllegalArgumentException("base must be greater than zero: base=" + base);

    StringBuffer conversion = new StringBuffer();

    if (base == 1) {
      for (int i = 0; i < number; i++)
        conversion.append("1");
      return conversion.toString();
    }

    String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    while (number > 0) {
      int digitIndex = number % base;
      conversion.insert(0, digits.charAt(digitIndex));
      number = number / base;
    }
    return conversion.toString();
  }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
int x1 = Debug.makeSymbolicInteger("x1");
convertBase(x0,x1);
}}