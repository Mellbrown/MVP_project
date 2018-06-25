package com.techwork.kjc.mvp_project.util;

import android.support.annotation.NonNull;

import com.techwork.kjc.mvp_project.subview.CusCalView;

import java.util.Calendar;
import java.util.Date;

public class DateKey implements Comparable<DateKey> {
    public int year;
    public int month;
    public int date;

    public DateKey(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.date = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public DateKey(int year, int month, int date){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month - 1,date);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.date = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public DateKey(CusCalView.SimpleDate simpleDate){
        this(simpleDate.year, simpleDate.month, simpleDate.date);
    }

    public CusCalView.SimpleDate convert2SimDate(){
        return new CusCalView.SimpleDate(year, month, date);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DateKey){
            DateKey dateKey = (DateKey) obj;
            if(
                    year == dateKey.year &&
                            month == dateKey.month &&
                            date == dateKey.date
                    ) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("%d년 %d월 %d일", year, month, date);
    }

    @Override
    public int compareTo(@NonNull DateKey o) {
        if(year == o.year){
            if(month == o.month){
                return date - date;
            } return month - o.month;
        } return year - o.year;
    }

    @Override
    public int hashCode() {
        return year * 10000 + month * 100 + date;
    }
}
