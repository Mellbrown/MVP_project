package com.techwork.kjc.mvp_project.bean;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.techwork.kjc.mvp_project.util.g2u;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mlyg2 on 2018-06-11.
 */

@IgnoreExtraProperties
public class MeasureItemBean implements Comparable<MeasureItemBean>{
    private static final SimpleDateFormat mDateFormat = new SimpleDateFormat("MM월 dd일");

    public Long timestamp;
    public double armWeight;
    public double legWeight;
    public double backWeight;
    public double allBodyWeight;

    public MeasureItemBean(){}

    //테스를 위한 랜덤 아이템이넣는 것이다 데스
    public MeasureItemBean(boolean TestRand){
        if(!TestRand) return;

        timestamp = new Date().getTime();
        armWeight = g2u.rand(4,12);
        legWeight = g2u.rand(12,24);
        backWeight = g2u.rand(12,30);
        allBodyWeight = armWeight + legWeight + backWeight;
    }

    @Exclude
    public String getStringMdDate(){
        return mDateFormat.format(new Date(timestamp));
    }

    @Override
    public int compareTo(@NonNull MeasureItemBean o) {
        return timestamp.compareTo(o.timestamp);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MeasureItemBean){
            MeasureItemBean data = ((MeasureItemBean) obj);
            return g2u.NullEqual(timestamp, data.timestamp) &&
                    g2u.NullEqual(armWeight, data.armWeight) &&
                    g2u.NullEqual(legWeight, data.legWeight) &&
                    g2u.NullEqual(backWeight, data.backWeight) &&
                    g2u.NullEqual(allBodyWeight, data.allBodyWeight);
        }
        return false;
    }
}
