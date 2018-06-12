package com.techwork.kjc.mvp_project.util;

/**
 * Created by mlyg2 on 2018-06-12.
 */

public class g2u {
    static public double rand(int start, int end){
        return Math.random()*(end-start) + start;
    }

    static public boolean NullEqual(Object o1, Object o2){
        if (o1 == null && o2 != null) {
            return false;
        } else if(o1 != null && o2 == null){
            return false;
        }else if(o1 == null && o2 == null){
            return true;
        }else{
            return o1.equals(o2);
        }
    }

    static public int NullComapareTo(Comparable o1, Comparable o2){
        if (o1 == null && o2 != null) {
            return -1;
        } else if(o1 != null && o2 == null){
            return 1;
        }else if(o1 == null && o2 == null){
            return 0;
        }else{
            return o1.compareTo(o2);
        }
    }

}
