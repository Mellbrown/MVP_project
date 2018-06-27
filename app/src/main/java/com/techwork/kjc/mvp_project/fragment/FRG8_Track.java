package com.techwork.kjc.mvp_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.bean.MeasureItemBean;
import com.techwork.kjc.mvp_project.subview.CusInputUpdownCounter;

import java.util.ArrayList;

public class FRG8_Track extends Fragment{
   private View viewLayout;
   private TextView txtTitle;
   private TextView act8_track;
   private TextView act8_history;
   private TextView act8_graph;
   private FrameLayout act8_level;
   private FrameLayout act8_reps;
   private Button act8_regist;
   private Button act8_clear;
   private RecyclerView act8_track_view;

   // 아이템
   private TextView act8_item_num;
   private TextView act8_item_level;
   private TextView act8_item_reps;
   public int level;
   public int reps;


    public Requester requester;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewLayout = inflater.inflate(R.layout.act8_focus_track, container,false);

        txtTitle = viewLayout.findViewById(R.id.txtTitle);

        act8_track = viewLayout.findViewById(R.id.act8_track);
        act8_history = viewLayout.findViewById(R.id.act8_history);
        act8_graph = viewLayout.findViewById(R.id.act8_graph);

        act8_level = viewLayout.findViewById(R.id.act8_level);
        act8_reps = viewLayout.findViewById(R.id.act8_reps);
        act8_regist = viewLayout.findViewById(R.id.act8_regist);
        act8_clear = viewLayout.findViewById(R.id.act8_clear);
        act8_track_view = viewLayout.findViewById(R.id.act8_track_view);
        // 아이템
        act8_item_num = viewLayout.findViewById(R.id.act8_item_num);
        act8_item_level = viewLayout.findViewById(R.id.act8_item_level);
        act8_item_reps = viewLayout.findViewById(R.id.act8_item_reps);

        CusInputUpdownCounter cusInputUpdownCounter1 = new CusInputUpdownCounter(getContext(), "강도(Level)", "", 0, new CusInputUpdownCounter.OnChangedValue() {
            @Override
            public void onChangedValue(int value) {
                level = value;
            }
        });
        act8_level.addView(cusInputUpdownCounter1,-1,-1);

        CusInputUpdownCounter cusInputUpdownCounter2 = new CusInputUpdownCounter(getContext(), "횟수(Reps)", "개", 0, new CusInputUpdownCounter.OnChangedValue() {
            @Override
            public void onChangedValue(int value) {
                reps = value;
            }
        });
        act8_reps.addView(cusInputUpdownCounter2,-1,-1);



        act8_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(FRG8_History);
            }
        });

        act8_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // startActivity(FRG8_Graph);
            }
        });

        act8_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.requestFocus_register(level, reps);
            }
        });

        act8_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.requestFocus_clear();
            }
        });


        return viewLayout;
    }


    public interface Requester{
        void requestFocus_register(int level, int reps);
        void requestFocus_clear();
        void requestRecyclerView(int date, int level, int reps);
    }

    public void requestFocus_register(int level, int reps) {

        // 파이어베이스 등록 화이팅

    }
}
