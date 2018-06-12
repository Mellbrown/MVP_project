package com.techwork.kjc.mvp_project.bean;

import com.techwork.kjc.mvp_project.util.g2u;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class MeasureItemBean {
    public String date;
    public double armWeight;
    public double legWeight;
    public double backWeight;
    public double allBodyWeight;

    public MeasureItemBean(){}
    public MeasureItemBean(boolean TestRand){
        if(!TestRand) return;

        date = (int)g2u.rand(1,12)+ "월" + (int)g2u.rand(1,31) + "일";
        armWeight = g2u.rand(4,12);
        legWeight = g2u.rand(12,24);
        backWeight = g2u.rand(12,30);
        allBodyWeight = armWeight + legWeight + backWeight;
    }
}
