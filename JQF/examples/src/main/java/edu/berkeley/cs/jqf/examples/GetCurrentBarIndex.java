package edu.berkeley.cs.jqf.examples;

public class GetCurrentBarIndex {
public static int getCurrentBarIndex(int barCount, int currentBar, int index){
        switch (barCount){
            case 1:
                return 0;
            case 2:
                if(index < 4){
                    return 0;
                }else if(index >=4 && index <8){
                    return 1;
                }else if(index >=8 && index <12){
                    return 0;
                }else if(index >= 12 && index <16){
                    return 1;
                }else{
                    throw new RuntimeException("ERROR getCurrentBarIndex out of bound index : " + index);
                }
            case 4:
                if(index < 4){
                    return 0;
                }else if(index >=4 && index <8){
                    return 1;
                }else if(index >=8 && index <12){
                    return 2;
                }else if(index >= 12 && index <16){
                    return 3;
                }else{
                    throw new RuntimeException("ERROR getCurrentBarIndex out of bound index : " + index);
                }
            default:
                throw new RuntimeException("ERROR getCurrentBarIndex bar count - " + barCount);
        }
    }
public static void main(String[] args) {


getCurrentBarIndex(56, 87, 51);
}
}