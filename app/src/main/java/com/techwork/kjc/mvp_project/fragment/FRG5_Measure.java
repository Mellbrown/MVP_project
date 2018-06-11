package com.techwork.kjc.mvp_project.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;
import com.techwork.kjc.mvp_project.bean.MeasureItemBean;
import com.techwork.kjc.mvp_project.viewholder.MeasureItemViewHolder;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class FRG5_Measure extends Fragment{

    private FloatingActionButton btnAdd;
    private FloatingActionButton btnRemove;

    private RecyclerView recyclerView;
    private LinearLayoutManager recLayoutMgr;
    private BaseRecyclerAdapter<MeasureItemBean, MeasureItemViewHolder> recAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.act5_measure, container,false);

        btnAdd = viewLayout.findViewById(R.id.btnAdd);
        btnRemove = viewLayout.findViewById(R.id.btnRemove);
        recyclerView = viewLayout.findViewById(R.id.recyclerView);

        recLayoutMgr = new LinearLayoutManager(getActivity());
        recAdapter = new BaseRecyclerAdapter<MeasureItemBean, MeasureItemViewHolder>(
                R.layout.item_measure, MeasureItemViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(MeasureItemViewHolder holder) {

                //holder.btnShowPrescribe.setOnClickListener();
            }

            @Override
            public void dataConvertViewHolder(MeasureItemViewHolder holder, MeasureItemBean data) {
                holder.dataBind(data);
            }
        };
        recyclerView.setLayoutManager(recLayoutMgr);
        recyclerView.setAdapter(recAdapter);

        return viewLayout;
    }
}
