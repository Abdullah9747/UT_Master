package edu.berkeley.cs.jqf.examples;

public class TValue {
public static double tValue(int runs) {
                if(runs == 1) throw new IllegalArgumentException("A t-value with 0 degrees of freedom cannot be computed");
                else if(runs == 2) return 12.706;//df= 1, p=0.05, two-tailed
                else if(runs == 3) return 4.303;// df=2, p=0.05, two-tailed
                else if(runs == 4) return 3.182;// df=3, p=0.05, two-tailed
                else if(runs == 5) return 2.776;// df=4, p=0.05, two-tailed
                else if(runs == 6) return 2.571;// df=5, p=0.05, two-tailed
                else if(runs == 7) return 2.447;// df=6, p=0.05, two-tailed
                else if(runs == 8) return 2.365;// df=7, p=0.05, two-tailed
                else if(runs == 9) return 2.306;// df=8, p=0.05, two-tailed
                else if(runs == 10) return 2.262;// df=9, p=0.05, two-tailed
                else if(runs == 11) return 2.228;// df=10, p=0.05, two-tailed
                else if(runs == 12) return 2.201;// df=11, p=0.05, two-tailed
                else if(runs == 13) return 2.179;// df=12, p=0.05, two-tailed
                else if(runs == 14) return 2.160;// df=13, p=0.05, two-tailed
                else if(runs == 15) return 2.145;// df=14, p=0.05, two-tailed
                else if(runs == 16) return 2.131;// df=15, p=0.05, two-tailed
                else if(runs == 17) return 2.120;// df=16, p=0.05, two-tailed
                else if(runs == 18) return 2.110;// df=17, p=0.05, two-tailed
                else if(runs == 19) return 2.101;// df=18, p=0.05, two-tailed
                else if(runs == 20) return 2.093;// df=19, p=0.05, two-tailed
                else if(runs == 21) return 2.086;// df=20, p=0.05, two-tailed
                else if(runs == 22) return 2.080;// df=21, p=0.05, two-tailed
                else if(runs == 23) return 2.074;// df=22, p=0.05, two-tailed
                else if(runs == 24) return 2.069;// df=23, p=0.05, two-tailed
                else if(runs == 25) return 2.064;// df=24, p=0.05, two-tailed
                else if(runs == 26) return 2.060;// df=25, p=0.05, two-tailed
                else if(runs == 27) return 2.056;// df=26, p=0.05, two-tailed
                else if(runs == 28) return 2.052;// df=27, p=0.05, two-tailed
                else if(runs == 29) return 2.048;// df=28, p=0.05, two-tailed
                else if(runs == 30) return 2.045;// df=29, p=0.05, two-tailed
                else throw new UnsupportedOperationException("Still need to expand tValue method to support different values of N: " + runs);
    }
public static void main(String[] args) {


tValue(35);
}
}