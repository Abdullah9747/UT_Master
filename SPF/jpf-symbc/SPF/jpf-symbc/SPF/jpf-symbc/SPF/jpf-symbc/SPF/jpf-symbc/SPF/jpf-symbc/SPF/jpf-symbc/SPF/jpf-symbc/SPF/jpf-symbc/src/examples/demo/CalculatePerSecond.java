package demo; import gov.nasa.jpf.symbc.Debug;public class CalculatePerSecond {
public static double calculatePerSecond(long countA, double countPerSecA, long countB, double countPerSecB) {
        double totalCount = countA + countB;
        double totalTime = ((double)countA / countPerSecA) + ((double)countB / countPerSecB);
        return totalCount / totalTime;
    }
public static void main(String[] args){
long x0 = Debug.makeSymbolicLong("x0");
double x1 = Debug.makeSymbolicReal("x1");
long x2 = Debug.makeSymbolicLong("x2");
double x3 = Debug.makeSymbolicReal("x3");
calculatePerSecond(x0,x1,x2,x3);
}}