package demo; import gov.nasa.jpf.symbc.Debug;public class GetColumnName {
public static String getColumnName(int columnIndex) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder columnName = new StringBuilder();

        int index = columnIndex;
        int overflowIndex = -1;
        while (index > 25) {
            overflowIndex++;
            index -= 26;
        }

        if (overflowIndex >= 0) {
            columnName.append(alphabet.toCharArray()[overflowIndex]);
        }

        columnName.append(alphabet.toCharArray()[index]);

        return columnName.toString();
    }
public static void main(String[] args){
int x0 = Debug.makeSymbolicInteger("x0");
getColumnName(x0);
}}