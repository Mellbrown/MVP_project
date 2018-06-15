package com.techwork.kjc.mvp_project.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;
import com.techwork.kjc.mvp_project.bean.MeasureItemBean;
import com.techwork.kjc.mvp_project.viewholder.MeasureItemViewHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class FRG5_Measure extends Fragment implements View.OnClickListener {

    private FloatingActionButton btnAdd;
    private FloatingActionButton btnRemove;
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
        void requestRemoveThisMeasureItem(MeasureItemBean measureItemBean);
    }

    public void responseMeasureItemBeans(ArrayList<MeasureItemBean> measureItemBeans){
        Collections.sort(measureItemBeans);
        recAdapter.dataList = measureItemBeans;
        recAdapter.notifyDataSetChanged();
    }

    public void responseAddMeasureItem(MeasureItemBean measureItemBean){
        recAdapter.dataList.add(measureItemBean);
        Collections.sort(recAdapter.dataList);
        recAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(),"추가 완료", Toast.LENGTH_SHORT);
    }

    public void responseRemoveSuccessMeasureItem(MeasureItemBean measureItemBean){
        selected = -1;
        btnCancle.setVisibility(View.GONE);

        int index = recAdapter.dataList.indexOf(measureItemBean);
        recAdapter.dataList.remove(index);
        recAdapter.notifyItemRemoved(index);
        Toast.makeText(getContext(),"삭제 완료", Toast.LENGTH_SHORT);
    }
}
