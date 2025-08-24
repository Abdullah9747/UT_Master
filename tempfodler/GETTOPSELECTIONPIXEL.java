package demo; import gov.nasa.jpf.symbc.Debug;public class GETTOPSELECTIONPIXEL {

    private int getTopSelectionPixel(int childrenTop, int fadingEdgeLength, int rowStart) {
        // first pixel we can draw the selection into
        int topSelectionPixel = childrenTop;
        if (rowStart > 0) {
            topSelectionPixel += fadingEdgeLength;
        }
        return topSelectionPixel;
    }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
int x1 = Debug.makeSymbolicInteger("x1");
int x2 = Debug.makeSymbolicInteger("x2");
gETTOPSELECTIONPIXEL(x0,x1,x2);
}}