package com.techwork.kjc.mvp_project.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.techwork.kjc.mvp_project.R;

import static com.techwork.kjc.mvp_project.util.g2u.PatchRouterFRG7.back;

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

    public static int convertPixelsToDp(float px, Context context) {

        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());

        return value;

    }

    public interface PatchRouterFRG7{
        int arm = 1;
        int leg = 2;
        int back = 3;
        int body = 4;
        void route(int where);
    }

    public static void PatchEventAttacherFRG7(View viewLayout, PatchRouterFRG7 patchRouterFRG7){
        viewLayout.findViewById(R.id.tabArm).setOnClickListener(v -> { patchRouterFRG7.route(PatchRouterFRG7.arm);});
        viewLayout.findViewById(R.id.tabLeg).setOnClickListener(v->{patchRouterFRG7.route(PatchRouterFRG7.leg);});
        viewLayout.findViewById(R.id.tabBack).setOnClickListener(v->{patchRouterFRG7.route(PatchRouterFRG7.back);});
        viewLayout.findViewById(R.id.tabAllBody).setOnClickListener(v->{patchRouterFRG7.route(PatchRouterFRG7.body);});
    }
}
