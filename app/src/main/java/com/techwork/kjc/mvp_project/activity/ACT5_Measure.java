package com.techwork.kjc.mvp_project.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;
import com.techwork.kjc.mvp_project.bean.MeasureItemBean;
import com.techwork.kjc.mvp_project.viewholder.MeasureItemViewHolder;

public class ACT5_Measure extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private FloatingActionButton btnRemove;

    private RecyclerView recyclerView;
    private LinearLayoutManager recLayoutMgr;
    private BaseRecyclerAdapter<MeasureItemBean, MeasureItemViewHolder> recAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act5_measure);

        btnAdd = findViewById(R.id.btnAdd);
        btnRemove = findViewById(R.id.btnRemove);

        recyclerView = findViewById(R.id.recyclerView);
        recLayoutMgr = new LinearLayoutManager(this);
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
    }
}
