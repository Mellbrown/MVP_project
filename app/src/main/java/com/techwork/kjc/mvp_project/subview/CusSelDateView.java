package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

import java.util.Calendar;
import java.util.Date;

public class CusSelDateView extends FrameLayout implements View.OnClickListener {
    private View viewLayout;

    private TextView txtDate;
    private FloatingActionButton btnPrevMonth;
    private FloatingActionButton btnNextMonth;
    private CardView conIptDate;
    private EditText iptDate;

    private SimpleDate curDate;

    private OnChangedDateListener onChangedDateListener;

    public CusSelDateView(@NonNull Context context, SimpleDate date, OnChangedDateListener onChangedDateListener) {
        super(context);

        this.onChangedDateListener = onChangedDateListener;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.custview_sel_date_view,this,false);
        addView(viewLayout);

        txtDate = viewLayout.findViewById(R.id.txtDate);
        btnPrevMonth = viewLayout.findViewById(R.id.btnPrevMonth);
        btnNextMonth = viewLayout.findViewById(R.id.btnNextMonth);
        conIptDate = viewLayout.findViewById(R.id.conIptDate);
        iptDate = viewLayout.findViewById(R.id.iptDate);

        txtDate.setOnClickListener(this);
        btnPrevMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);

        this.curDate = date;
        iptDate.setText(String.format("%04d.%02d.%02d",curDate.year, curDate.month, curDate.date));

        viewLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(on) DissmissPopupDateEditor();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPrevMonth:{
                if(on) break;
                curDate = new SimpleDate(curDate.year, curDate.month, curDate.date -1);
                txtDate.setText(String.format("%04d.%02d.%02d",curDate.year, curDate.month, curDate.date));
                onChangedDateListener.onChangedDate(curDate);
            }break;
            case R.id.btnNextMonth:{
                if(on) break;
                curDate = new SimpleDate(curDate.year, curDate.month, curDate.date +1);
                txtDate.setText(String.format("%04d.%02d.%02d",curDate.year, curDate.month, curDate.date));
                onChangedDateListener.onChangedDate(curDate);
            }break;
            case R.id.txtDate:{
                ShowPopupDateEditor();
            }
        }
    }

    boolean on = false;
    private void ShowPopupDateEditor(){
        on = true;
        iptDate.setText(String.format("%04d.%02d.%02d",curDate.year, curDate.month, curDate.date));
        conIptDate.setVisibility(VISIBLE);
        btnNextMonth.setVisibility(INVISIBLE);
        btnPrevMonth.setVisibility(INVISIBLE);
    }

    private void DissmissPopupDateEditor(){
        on = false;
        conIptDate.setVisibility(GONE);
        btnPrevMonth.setVisibility(VISIBLE);
        btnNextMonth.setVisibility(VISIBLE);
        if(readFromIpt() != null){
            curDate = readFromIpt();
            txtDate.setText(String.format("%04d.%02d.%02d",curDate.year, curDate.month, curDate.date));
            onChangedDateListener.onChangedDate(curDate);
        }
    }

    private SimpleDate readFromIpt(){
        String strDate = iptDate.getText().toString();
        int year, month, day;
        try {
            year =  Integer.valueOf(strDate.split("\\.")[0]);
            month = Integer.valueOf(strDate.split("\\.")[1]);
            day = Integer.valueOf(strDate.split("\\.")[2]);
        }catch (Exception e){
            return null;
        }

        return new SimpleDate(year, month, day);
    }

    public static class SimpleDate implements Comparable<SimpleDate> {
        public int year, month, date;

        public SimpleDate(){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            this.year = calendar.get(Calendar.YEAR);
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.date = calendar.get(Calendar.DAY_OF_MONTH);
        }
        public SimpleDate(int year, int month, int date){
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month - 1,date);
            this.year = calendar.get(Calendar.YEAR);
            this.month = calendar.get(Calendar.MONTH) + 1;
            this.date = calendar.get(Calendar.DAY_OF_MONTH);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof SimpleDate){
                SimpleDate simpleDate = (SimpleDate) obj;
                if(
                        year == simpleDate.year &&
                                month == simpleDate.month &&
                                date == simpleDate.date
                        ) return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return String.format("%d년 %d월 %d일", year, month, date);
        }

        @Override
        public int compareTo(@NonNull SimpleDate o) {
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

    public interface OnChangedDateListener{
        void onChangedDate(SimpleDate date);
    }
}
