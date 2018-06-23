package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;

public class SubFRG8_Histrory extends FrameLayout {

    private View viewLayout;

    private FrameLayout sel_date;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerAdapter<Item, ViewHolder> recyclerAdapter;

    private CusSelDateView cusSelDateView;
    private CusSelDateView.SimpleDate curDate = new CusSelDateView.SimpleDate();

    private OnRequesterDataOfDate onRequesterDataOfDate;

    public SubFRG8_Histrory(@NonNull Context context, OnRequesterDataOfDate onRequesterDataOfDate) {
        super(context);

        this.onRequesterDataOfDate = onRequesterDataOfDate;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.subview_act8_track, this, false);
        addView(viewLayout);

        sel_date = viewLayout.findViewById(R.id.sel_date);
        recyclerView = viewLayout.findViewById(R.id.recyclerView);

        cusSelDateView = new CusSelDateView(getContext(), curDate, new CusSelDateView.OnChangedDateListener() {
            @Override
            public void onChangedDate(CusSelDateView.SimpleDate date) {
                curDate = date;
                onRequesterDataOfDate.onRequesterDataOfDate(date);
            }
        });
        sel_date.addView(cusSelDateView, -1, -2);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new BaseRecyclerAdapter<Item, ViewHolder>(R.layout.item_focus, ViewHolder.class) {
            @Override
            public void dataConvertViewHolder(ViewHolder holder, Item data) {
                holder.dataBind(data);
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        onRequesterDataOfDate.onRequesterDataOfDate(curDate);
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

    public interface OnRequesterDataOfDate{
        void onRequesterDataOfDate(CusSelDateView.SimpleDate requestDate);
    }
}
