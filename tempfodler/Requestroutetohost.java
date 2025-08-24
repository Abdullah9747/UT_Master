package demo; import gov.nasa.jpf.symbc.Debug;public class Requestroutetohost {

    public static boolean requestroutetohost(int hostAddress) {
        return false;
    }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
requestroutetohost(x0);
}}