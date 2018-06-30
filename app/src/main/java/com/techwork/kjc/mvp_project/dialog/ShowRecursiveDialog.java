package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;

import java.util.List;

public class ShowRecursiveDialog extends Dialog{

    private FloatingActionButton btnAdd;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerAdapter<Item, ViewHolder> recyclerAdapter;

    public ShowRecursiveDialog(@NonNull Context context, List<Item> items,View.OnClickListener onClickListener) {
        super(context);
        setContentView(R.layout.dialog_show_recursive);

        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);

        layoutManager = new LinearLayoutManager(context);
        recyclerAdapter = new BaseRecyclerAdapter<Item, ViewHolder>(
                R.layout.item_show_recursive,ViewHolder.class
        ) {
            @Override
            public void dataConvertViewHolder(ViewHolder holder, Item data) {
                holder.dataBind(data);
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.dataList.addAll(items);
        recyclerAdapter.notifyDataSetChanged();

        btnAdd.setOnClickListener(v -> {
            dismiss();
            onClickListener.onClick(v);
        });
    }

    public static class Item{
        public int num;
        public String first;
        public String second;
        public String third;
        public String fourth;

        public int reps;

        public Item(
                int num,
                String first,
                String second,
                String third,
                String fourth,
                int reps
        ){
            this.num = num;
            this.first = first;
            this.second = second;
            this.third = third;
            this.fourth = fourth;
            this.reps = reps;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtNum;
        public TextView txtFirst;
        public TextView txtSecond;
        public TextView txtThird;
        public TextView txtFourth;
        public TextView txtReps;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNum = itemView.findViewById(R.id.txtNum);
            txtFirst = itemView.findViewById(R.id.txtFirst);
            txtSecond = itemView.findViewById(R.id.txtSecond);
            txtThird = itemView.findViewById(R.id.txtThird);
            txtFourth = itemView.findViewById(R.id.txtFourth);
            txtReps = itemView.findViewById(R.id.txtReps);
        }

        void dataBind(Item item){
            txtNum.setText(item.num+"");
            txtFirst.setText(item.first);
            txtSecond.setText(item.second);
            txtThird.setText(item.third);
            txtFourth.setText(item.fourth);
            txtReps.setText(item.reps + "");
        }
    }
}
