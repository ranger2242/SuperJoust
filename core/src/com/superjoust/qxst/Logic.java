package com.superjoust.qxst;

import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 1/26/2017.
 */
public class Logic {
    public static boolean or(boolean b1,boolean b2){
        return b1||b2;
    }
    public static boolean and(boolean b1,boolean b2){
        return b1&&b2;
    }
    public static boolean xor(boolean b1, boolean b2) {
        if (b1 && b2) {
            return false;
        } else if (!b1 && !b2) {
            return false;
        } else return true;
    }
    public static boolean imp(boolean b1, boolean b2){
        if (!b1 && b2) {
            return false;
        } else {
            return true;
        }

    }
    public static boolean bicon(boolean b1, boolean b2){
        return !xor(b1,b2);
    }
    public static boolean neg(boolean b1) {
        return !b1;
    }
    public static boolean equ(boolean b1,boolean b2){
        return b1==b2;
    }
    public static void all(){}

    public static ArrayList<Boolean> or(ArrayList<Boolean> b1, ArrayList<Boolean> b2){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                b3.add(b1.get(i)||b2.get(i));
            }catch (IndexOutOfBoundsException e){}
        }
        return b3;
    }
    public static boolean equ(ArrayList<Boolean> b1, ArrayList<Boolean> b2){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                b3.add(b1.get(i)==b2.get(i));
            }catch (IndexOutOfBoundsException e){}
        }
        boolean ret=true;
        for(Boolean b :b3){
            ret=and(ret,b);
        }
        return ret;
    }
    public static ArrayList<Boolean> and(ArrayList<Boolean> b1, ArrayList<Boolean> b2){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                b3.add(b1.get(i)&&b2.get(i));
            }catch (IndexOutOfBoundsException e){}
        }
        return b3;
    }
    public static ArrayList<Boolean> xor(ArrayList<Boolean> b1, ArrayList<Boolean> b2){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                boolean g;
                if(b1.get(i)&&b2.get(i)){
                    g=false;
                }else if(!b1.get(i)&& !b2.get(i)){
                    g=false;
                }else g=true;
                b3.add(g);
            }catch (IndexOutOfBoundsException e){

            }
        }
        return b3;
    }
    public static ArrayList<Boolean> imp(ArrayList<Boolean> b1, ArrayList<Boolean> b2){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                boolean g;
                if(!b1.get(i) && b2.get(i)){
                    g=false;
                }else{
                    g=true;
                }
                b3.add(g);
            }catch (IndexOutOfBoundsException e){

            }
        }
        return b3;
    }
    public static ArrayList<Boolean> bicon(ArrayList<Boolean> b1, ArrayList<Boolean> b2){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                boolean g;
                if(b1.get(i)&&b2.get(i)){
                    g=false;
                }else if(!b1.get(i)&& !b2.get(i)){
                    g=false;
                }else g=true;
                g=!g;
                b3.add(g);
            }catch (IndexOutOfBoundsException e){

            }
        }
        return b3;
    }
    public static ArrayList<Boolean> neg(ArrayList<Boolean> b1){
        ArrayList<Boolean> b3=new ArrayList<>();
        for(int i=0;i<b1.size();i++){
            try{
                b3.add(!b1.get(i));
            }catch (IndexOutOfBoundsException e){

            }
        }
        return b3;
    }
}
