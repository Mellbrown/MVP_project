package com.techwork.kjc.mvp_project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;
import com.techwork.kjc.mvp_project.subview.CusCalView;
import com.techwork.kjc.mvp_project.util.g2u;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class FRG10_Record extends Fragment implements CusCalView.OnUpdateMonth {
    private View viewLayout;

    private Button btnShow;
    private FrameLayout frame;

    public CusCalView<Item,CalViewHolder> cusCalView;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerAdapter<Item,RecViewHolder> baseRecyclerAdapter;

    public Map<CusCalView.SimpleDate, Item> dataMap;
    public View.OnClickListener onClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act10_record, container, false);

        btnShow = viewLayout.findViewById(R.id.btnShow);
        btnShow.setOnClickListener(onClickListener);

        frame = viewLayout.findViewById(R.id.frame);
        cusCalView = new CusCalView<Item, CalViewHolder>(getContext()) {
            @Override
            public CusCalViewHolder<Item> getNewInstanceViewHolder() {
                CalViewHolder calViewHolder = new CalViewHolder(getContext());
                return calViewHolder;
            }
        };
        cusCalView.setOnUpdateMonth(this);
        frame.addView(cusCalView,-1,-1);

        recyclerView = viewLayout.findViewById(R.id.recylerView);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        baseRecyclerAdapter = new BaseRecyclerAdapter<Item, RecViewHolder>(
                R.layout.item_record, RecViewHolder.class
        ) {
            @Override
            public void dataConvertViewHolder(RecViewHolder holder, Item data) {
                holder.dataBind(data.title, data);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(baseRecyclerAdapter);

        cusCalView.dataMap.putAll(dataMap);
        cusCalView.notifyChangedDataSet();


        return viewLayout;
    }

    @Override
    public void onUpdateMonth(Calendar selectedDate) {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);

        Calendar cal = Calendar.getInstance();
        cal.set(year,month,1);



        Item iMonth = new Item("월간",0,0,0);
        List<Item> iWeek = new ArrayList<>();
        int actualMaximum = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
        for(int i = 0 ;  actualMaximum > i; i++)
            iWeek.add(new Item(i+"주",0,0,0));

        int prev = cal.get(Calendar.WEEK_OF_MONTH);
        while (cal.get(Calendar.MONTH) != month){
            Item item = dataMap.get(new CusCalView.SimpleDate(year, month, cal.get(Calendar.DAY_OF_MONTH)));
            if(item != null){
                Item acc = iWeek.remove(cal.get(Calendar.WEEK_OF_MONTH));
                acc.mVal += item.mVal;
                acc.vVal += item.vVal;
                acc.pVal += item.pVal;

                iMonth.mVal += item.mVal;
                iMonth.vVal += item.vVal;
                iMonth.pVal += item.pVal;
                iWeek.add(cal.get(Calendar.WEEK_OF_MONTH), acc);
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        iWeek.add(iMonth);
        baseRecyclerAdapter.dataList.clear();
        baseRecyclerAdapter.dataList.addAll(iWeek);
    }

    public static class Item {
        public String title;
        public double mVal;
        public int vVal;
        public int pVal;

        public Item(double mVal, int vVal, int pVal){
            this.mVal = mVal;
            this.vVal = vVal;
            this.pVal = pVal;
        }

        public Item(String title, double mVal, int vVal, int pVal){
            this.title = title;
            this.mVal = mVal;
            this.vVal = vVal;
            this.pVal = pVal;
        }
    }

    public static class RecViewHolder extends RecyclerView.ViewHolder {

        public View viewLayout;

        public TextView txtTitle;
        public TextView txtM;
        public TextView txtV;
        public TextView txtP;

        public RecViewHolder(View itemView) {
            super(itemView);
            viewLayout = itemView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    g2u.convertPixelsToDp(60f, viewLayout.getContext()),
                    g2u.convertPixelsToDp(70f, viewLayout.getContext())
            );
            layoutParams.setMargins(
                    g2u.convertPixelsToDp(8f, viewLayout.getContext()),
                    g2u.convertPixelsToDp(8f, viewLayout.getContext()),
                    g2u.convertPixelsToDp(8f, viewLayout.getContext()),
                    g2u.convertPixelsToDp(8f, viewLayout.getContext())
            );
            viewLayout.setLayoutParams(layoutParams);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtTitle.setVisibility(View.VISIBLE);
            txtM = itemView.findViewById(R.id.txtM);
            txtV = itemView.findViewById(R.id.txtV);
            txtP = itemView.findViewById(R.id.txtP);
        }

        public void dataBind(String title, Item item) {
            txtTitle.setText(title);
            txtM.setText(String.format("M %.0fKG", item.mVal));
            txtV.setText(String.format("V %d승", item.vVal));
            txtP.setText(String.format("P %d회", item.pVal));
        }
    }
//-------------------------------------------------------------------------------------------------
    public static class CalViewHolder extends CusCalView.CusCalViewHolder<Item> {

        public View viewLayout;

        public TextView txtP;
        public TextView txtM;
        public TextView txtV;

        public CalViewHolder(@NonNull Context context) {
            super(context);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup contener) {
            viewLayout = inflater.inflate(R.layout.item_record, this, false);

            txtP = viewLayout.findViewById(R.id.txtP);
            txtM = viewLayout.findViewById(R.id.txtM);
            txtV = viewLayout.findViewById(R.id.txtV);

            return viewLayout;
        }

        @Override
        public void onDataBind(Item data) {
            if(data == null)
                viewLayout.setVisibility(INVISIBLE);
            else{
                viewLayout.setVisibility(VISIBLE);
                txtM.setText(String.format("M %.0fKG", data.mVal));
                txtV.setText(String.format("V %d승", data.vVal));
                txtP.setText(String.format("P %d회", data.pVal));
            }
        }
    }
}
