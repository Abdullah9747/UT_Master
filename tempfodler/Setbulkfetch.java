package demo; import gov.nasa.jpf.symbc.Debug;public class Setbulkfetch {

    public static boolean setbulkfetch(boolean bulkFetch)
    {
    	return false;
    }
public static void main(String[] args){
boolean x0 = Debug.makeSymbolicBoolean("x0");
setbulkfetch(x0);
}}