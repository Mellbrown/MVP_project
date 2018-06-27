package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

import java.lang.reflect.InvocationTargetException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CusCalView<T, VH extends CusCalView.CusCalViewHolder> extends FrameLayout implements View.OnClickListener {
    private  View viewLayout;

    // 보이는 월 UI
    private TextView txtCurMonth;
    private FloatingActionButton btnPrevMonth;
    private FloatingActionButton btnNextMonth;
    // 월 선택 팝업
    private ImageView btnDismiss;
    private CardView conIptDate;
    private EditText iptDate;
    // 달력 Frame
    private TableLayout frame;

    // 선택된 달력
    private Calendar selectedDate;

    private Class<VH> cusCalViewHolderClass;
    private ArrayList<CusCalViewHolder> cusCalViewHolders = new ArrayList<>();

    public HashMap<SimpleDate,T> dataMap = new HashMap<>();

    public CusCalView(@NonNull Context context, Class<VH> viewHolderClass) {
        super(context);


        cusCalViewHolderClass = viewHolderClass;
        // set Content View
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.custview_cus_cal_view,this,false);
        addView(viewLayout);
        // 보이는 월 UI 불러오기
        txtCurMonth = viewLayout.findViewById(R.id.txtCurMonth);
        btnPrevMonth = viewLayout.findViewById(R.id.btnPrevMonth);
        btnNextMonth = viewLayout.findViewById(R.id.btnNextMonth);
        // 보이는 월 UI 이벤트 등록하기
        txtCurMonth.setOnClickListener(this);
        btnPrevMonth.setOnClickListener(this);
        btnNextMonth.setOnClickListener(this);
        // 월 선택 팝업 UI 불러오기
        btnDismiss = viewLayout.findViewById(R.id.btnDismiss);
        conIptDate = viewLayout.findViewById(R.id.conIptDate);
        iptDate = viewLayout.findViewById(R.id.iptDate);
        // 월 선택 팝업 UI 이벤트 등록
        btnDismiss.setOnClickListener(this);
        // 달력 내용 Frame UI 불러오기
        frame = viewLayout.findViewById(R.id.frame);
        // 달력 내용 UI 사전 준비
        PrePrepareCalandarViewContent();

        selectedDate = Calendar.getInstance();
        selectedDate.setTime(new Date());
        selectedDate.set(Calendar.DAY_OF_MONTH,1);

        UpdateMonthCalanderUIfromSelectedDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtCurMonth:{
                ShowPopupMonthEditor();
            }break;
            case R.id.btnPrevMonth:{
                selectedDate.add(Calendar.MONTH,-1);
                UpdateMonthCalanderUIfromSelectedDate();
            }break;
            case R.id.btnNextMonth:{
                selectedDate.add(Calendar.MONTH, 1);
                UpdateMonthCalanderUIfromSelectedDate();
            }break;
            case R.id.btnDismiss:{
                DissmissPopupMonthEditor();
            }break;
        }
    }

    private void ShowPopupMonthEditor(){
        btnDismiss.setVisibility(VISIBLE);
        conIptDate.setVisibility(VISIBLE);
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        iptDate.setText(String.format("%04d.%02d",year,month+1));
    }

    private void DissmissPopupMonthEditor(){
        String strValue = String.format("%04.2f",Double.valueOf(iptDate.getText().toString()));
        iptDate.setText("0000.00");
        btnDismiss.setVisibility(GONE);
        conIptDate.setVisibility(GONE);
        String strYear = strValue.split("\\.")[0];
        String strMonth = strValue.split("\\.")[1];
        strMonth = strMonth.length() >= 2  && strMonth.charAt(1) == '0' ? strMonth.charAt(0)+"" : strMonth;
        Integer year = Integer.valueOf(strYear);
        Integer month = Integer.valueOf(strMonth);
        selectedDate.set(Calendar.YEAR,year);
        selectedDate.set(Calendar.MONTH,month-1);

        UpdateMonthCalanderUIfromSelectedDate();
    }

    private void PrePrepareCalandarViewContent(){
        for(int i = 0; 6 > i; i++){
            TableRow tableRow = new TableRow(getContext());
            tableRow.setWeightSum(7);
            for(int j = 0; 7 > j; j++){
                CusCalViewHolder cusCalViewHolder = null;
                try {
                    cusCalViewHolder = cusCalViewHolderClass
                            .getDeclaredConstructor(Context.class)
                            .newInstance(getContext());
                }
                catch (IllegalAccessException e) {e.printStackTrace();}
                catch (InstantiationException e) { e.printStackTrace(); }
                catch (NoSuchMethodException e) { e.printStackTrace(); }
                catch (InvocationTargetException e) { e.printStackTrace(); }

                if(j==0) cusCalViewHolder.setColor(Color.RED);
                if(j==6) cusCalViewHolder.setColor(Color.BLUE);
                cusCalViewHolder.setTitle("");

                cusCalViewHolders.add(cusCalViewHolder);
                tableRow.addView(cusCalViewHolder,new TableRow.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
            }

            frame.addView(tableRow,new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
        }
    }

    private void UpdateMonthCalanderUIfromSelectedDate(){
        txtCurMonth.setText(String.format("%04d.%02d",selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH)+1));

        int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK) - 1;
        int maxDaiesOfMonth = selectedDate.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i = 0; cusCalViewHolders.size() > i; i++){
            CusCalViewHolder cusCalViewHolder = cusCalViewHolders.get(i);
            if (dayOfWeek <= i && i <= dayOfWeek + maxDaiesOfMonth) {
                cusCalViewHolder.setVisibility(VISIBLE);
                cusCalViewHolder.setDayOfMonth(i - dayOfWeek + 1);

            } else { //Dead Date
                cusCalViewHolder.setVisibility(INVISIBLE);
            }
        }
    }

    private VH getViewholderFromDate(SimpleDate simpleDate){
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH) + 1;
        if(simpleDate.year == year && simpleDate.month == month){
            int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK) - 1;
            return (VH) cusCalViewHolders.get(dayOfWeek + simpleDate.date);
        }
        else {
            return null;
        }
    }
    
    public void notifyChangeItem(SimpleDate date){
        T t = dataMap.get(date);
        ((CusCalViewHolder<T>) getViewholderFromDate(date)).setDataBind(date,t);
    }

    public void notifyChangedDataSet(){
        UpdateMonthCalanderUIfromSelectedDate();
    }

    public static class SimpleDate{
        int year, month, date;
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
    }
    public static abstract class CusCalViewHolder<T> extends FrameLayout{

        private View viewLayout;
        private View contentView;

        private TextView txtDate;
        private TextView txtTitle;
        private FrameLayout frame;

        private SimpleDate curBinded;

        public CusCalViewHolder(@NonNull Context context) {
            super(context);
            // set Content View
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewLayout = inflater.inflate(R.layout.item_cus_cal_view,this,false);
            addView(viewLayout,new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            txtDate = viewLayout.findViewById(R.id.txtDate);
            txtTitle = viewLayout.findViewById(R.id.txtTitle);
            frame = viewLayout.findViewById(R.id.frame);

            contentView = onCreateView(inflater,frame);
            frame.addView(contentView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        void setDayOfMonth(int dayOfMonth){
            txtDate.setText(dayOfMonth+"");
        }
        void setColor(int color){
            txtDate.setTextColor(color);
        }
        public void setTitle(String title){
            txtTitle.setText(title);
        }

        void setDataBind(SimpleDate date,T data){
            curBinded = date;
            onDataBind(data);
        }

        public SimpleDate getCurBinded(){ return curBinded; }

        public abstract View onCreateView(LayoutInflater inflater, ViewGroup contener);
        public abstract void onDataBind(T data);
    }
}
