package com.techwork.kjc.mvp_project.fragment;

import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;
import com.techwork.kjc.mvp_project.fireSource.Fire_GOS;
import com.techwork.kjc.mvp_project.util.g2u;
import com.techwork.kjc.mvp_project.viewholder.MeasureItemViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class FRG5_Measure extends Fragment implements View.OnClickListener {

    private TextView btnAdd;
    private TextView btnRemove;
    private FloatingActionButton btnReload;
    private FloatingActionButton btnCancle;

    private RecyclerView recyclerView;
    private LinearLayoutManager recLayoutMgr;
    private BaseRecyclerAdapter<MeasureItemBean, MeasureItemViewHolder> recAdapter;

    public Requester requester;

    int selected = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.act5_measure, container,false);

        if(requester == null) Log.e("FRG5_Measure", "리퀘스터 할당 안해줄꺼애오?");

        btnAdd = viewLayout.findViewById(R.id.btnAdd);
        btnRemove = viewLayout.findViewById(R.id.btnRemove);
        btnReload = viewLayout.findViewById(R.id.btnReload);
        btnCancle = viewLayout.findViewById(R.id.btnCancle);
        recyclerView = viewLayout.findViewById(R.id.recyclerView);

        btnAdd.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        btnReload.setOnClickListener(this);
        btnCancle.setOnClickListener(this);

        btnCancle.setVisibility(View.GONE);
        recLayoutMgr = new LinearLayoutManager(getActivity());
        recAdapter = new BaseRecyclerAdapter<MeasureItemBean, MeasureItemViewHolder>(
                R.layout.item_measure, MeasureItemViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(final MeasureItemViewHolder holder) {

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if(selected == -1){
                            btnCancle.setVisibility(View.VISIBLE);
                            int index = holder.getLayoutPosition();
                            selected = index;
                            recAdapter.notifyItemChanged(selected);
                            return true;
                        }
                        return false;
                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selected != -1){
                            int index = holder.getLayoutPosition();
                            int prev = selected;
                            selected = index;

                            recAdapter.notifyItemChanged(prev);
                            recAdapter.notifyItemChanged(selected);
                        }
                    }
                });

                holder.btnShowPrescribe.setOnClickListener((View v)->{
                    int index = holder.getLayoutPosition();
                    MeasureItemBean measureItemBean = recAdapter.dataList.get(index);
                    requester.requestShowPrescription(measureItemBean);
                });
            }

            @Override
            public void dataConvertViewHolder(MeasureItemViewHolder holder, MeasureItemBean data) {
                holder.dataBind(data);
                if(holder.getLayoutPosition() == selected)
                    holder.itemView.setBackgroundColor(Color.RED);
                else
                    holder.itemView.setBackgroundColor(Color.WHITE);
            }
        };
        recyclerView.setLayoutManager(recLayoutMgr);
        recyclerView.setAdapter(recAdapter);

        requester.requestMeasureItemBeans();

        return viewLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAdd:{
                requester.requestAddMeasureItem();
            }break;
            case R.id.btnRemove:{
                if(selected == -1) break;
                MeasureItemBean measureItemBean = recAdapter.dataList.get(selected);
                requester.requestRemoveThisMeasureItem(measureItemBean);
            }break;
            case R.id.btnReload:{
                requester.requestMeasureItemBeans();
            }break;

            case R.id.btnCancle:{
                int prev = selected;
                selected = -1;
                btnCancle.setVisibility(View.GONE);
                recAdapter.notifyItemChanged(prev);
            }break;
        }
    }

    public interface Requester{
        void requestMeasureItemBeans();
        void requestAddMeasureItem();
        void requestShowPrescription(MeasureItemBean measureItemBean);
        void requestRemoveThisMeasureItem(MeasureItemBean measureItemBean);
    }

    //서버에서 모든 아이템 목록을 가져다 달라는 요청의 결과로 목록을 받습니다.
    public void responseMeasureItemBeans(ArrayList<MeasureItemBean> measureItemBeans){
        Collections.sort(measureItemBeans);
        recAdapter.dataList = measureItemBeans;
        recAdapter.notifyDataSetChanged();
    }
    //서버에 아이템 하나 넣어달라는 요청을 결과로 아이템 하나를 받습니다.
    public void responseAddMeasureItem(MeasureItemBean measureItemBean){
        recAdapter.dataList.add(measureItemBean);
        Collections.sort(recAdapter.dataList);
        recAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(),"추가 완료", Toast.LENGTH_SHORT);
    }

    //서버에 해당 아이템을 하나 삭제 요청을합니다.
    public void responseRemoveSuccessMeasureItem(MeasureItemBean measureItemBean){
        selected = -1;
        btnCancle.setVisibility(View.GONE);

        int index = recAdapter.dataList.indexOf(measureItemBean);
        recAdapter.dataList.remove(index);
        recAdapter.notifyItemRemoved(index);
        Toast.makeText(getContext(),"삭제 완료", Toast.LENGTH_SHORT);
    }

    @IgnoreExtraProperties
    public static class MeasureItemBean implements Comparable<MeasureItemBean>{
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
}
