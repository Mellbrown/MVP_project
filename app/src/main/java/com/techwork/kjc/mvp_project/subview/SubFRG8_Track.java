package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SubFRG8_Track extends FrameLayout implements View.OnClickListener {

    private View viewLayout;

    private FrameLayout sel_date;
    private FrameLayout level_frame;
    private FrameLayout reps_frame;
    private FloatingActionButton btnUpload;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerAdapter<Item,ViewHolder> recyclerAdapter;

    private CusSelDateView cusSelDateView;
    private CusInputUpdownCounter levelCusInputUpdownCounter;
    private CusInputUpdownCounter repsCusInputUpdownCounter;

    private int valueLevel = 0;
    private int valueReps = 0;
    private CusSelDateView.SimpleDate simpleDate;

    private OnRequesterUploadItem onRequesterUploadItem;

    public SubFRG8_Track(@NonNull Context context, OnRequesterUploadItem onRequesterUploadItem) {
        super(context);
        this.onRequesterUploadItem = onRequesterUploadItem;
        //레이아웃 준비
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.subview_act8_track, this, false);
        addView(viewLayout);
        // Ui
        sel_date = viewLayout.findViewById(R.id.sel_date);
        level_frame = viewLayout.findViewById(R.id.level_frame);
        reps_frame = viewLayout.findViewById(R.id.reps_frame);
        btnUpload = viewLayout.findViewById(R.id.btnUpload);
        recyclerView = viewLayout.findViewById(R.id.recyclerView);
        // UI 프레임에 박기
        cusSelDateView = new CusSelDateView(getContext(), new CusSelDateView.SimpleDate(), date -> simpleDate = date);
        sel_date.addView(cusSelDateView);
        levelCusInputUpdownCounter = new CusInputUpdownCounter(getContext(),
                "강도(Level)", "", 0, (int value)->valueLevel=value);
        level_frame.addView(levelCusInputUpdownCounter, -2, -2);

        repsCusInputUpdownCounter = new CusInputUpdownCounter(getContext(),
                "횟수(Reps)", "개", 0, (int value)->valueReps=value);
        reps_frame.addView(repsCusInputUpdownCounter, -2, -2);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new BaseRecyclerAdapter<Item, ViewHolder>(R.layout.item_focus, ViewHolder.class) {
            @Override
            public void dataConvertViewHolder(ViewHolder holder, Item data) {
                holder.dataBind(data);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        btnUpload.setOnClickListener(this);

        setData(onRequesterUploadItem.onRequestInitdata());
    }

    @Override
    public void onClick(View v) {
        if(valueLevel == 0 && valueReps == 0) return;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(simpleDate.year,simpleDate.month-1, simpleDate.date);
        onRequesterUploadItem.onReqeusteUploadItem(cal.getTimeInMillis(),valueLevel, valueReps);
    }

    public static class Item{
        public int number;
        public int level;
        public int reps;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView act8_item_num;
        public TextView act8_item_level;
        public TextView act8_item_reps;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;

            act8_item_num = itemView.findViewById(R.id.act8_item_num);
            act8_item_level = itemView.findViewById(R.id.act8_item_level);
            act8_item_reps = itemView.findViewById(R.id.act8_item_reps);
        }

        public void dataBind(Item item){
            act8_item_num.setText(item.number + "");
            act8_item_level.setText(item.level + "");
            act8_item_reps.setText(item.reps + "");
        }
    }

    public void setData(ArrayList<Item> items){
        recyclerAdapter.dataList.clear();
        recyclerAdapter.dataList.addAll(items);
        recyclerAdapter.notifyDataSetChanged();
    }

    public interface OnRequesterUploadItem{
        ArrayList<Item> onRequestInitdata();
        void onReqeusteUploadItem(long timestamp, int level, int reps);
    }
}
