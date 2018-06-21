package com.techwork.kjc.mvp_project.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.subview.CusCalView;

import java.util.ArrayList;
import java.util.Map;

public class FRG9_Recursive extends Fragment {

    private View viewLayout;

    private FrameLayout frame;

    private CusCalView<Item, ViewHolder> cusCalView;

    public Requester requester;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act9_recursive, container,false);

        if (requester == null)
            Log.e("FRG9_Recursive", "리퀘스터 할당안했는 뎁셩");

        frame = viewLayout.findViewById(R.id.frame);
        cusCalView = new CusCalView<Item, ViewHolder>(getContext()) {
            @Override
            public CusCalViewHolder<Item> getNewInstanceViewHolder() {
                ViewHolder viewHolder = new ViewHolder(getContext());
                viewHolder.viewLayout.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requester.onItemClick(viewHolder.getCurBinded());
                    }
                });
                return viewHolder;
            }
        };
        frame.addView(cusCalView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        return viewLayout;
    }

    public static class Record{
        public static final int none = -1;
        public static final int arm = 1;
        public static final int leg = 2;
        public static final int back = 3;
        public static final int body = 4;

        public final int[] courseOrder =  {none, none, none,none};
        public final int[] courceLepcount = {0,0,0,0};

        public Record put(int idx, int courseType, int courseLeps){
            courseOrder[idx] = courseType;
            courceLepcount[idx] = courseLeps;
            return this;
        }
    }

    public static class Item {
        public final ArrayList<Record> records = new ArrayList<>();
    }

    public static class ViewHolder extends CusCalView.CusCalViewHolder<Item> {

        public View viewLayout;
        public ImageView icon;
        public TextView txtCnt;

        public ViewHolder(@NonNull Context context) {
            super(context);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup contener) {
            viewLayout = inflater.inflate(R.layout.item_recursive_item, contener, false);
            icon = viewLayout.findViewById(R.id.icon);
            txtCnt = viewLayout.findViewById(R.id.txtCnt);
            return viewLayout;
        }

        @Override
        public void onDataBind(Item data) {
            if(data != null && data.records.size() > 1){
                icon.setVisibility(VISIBLE);
                txtCnt.setVisibility(VISIBLE);
                txtCnt.setText(data.records.size() + " 회");
            }else{
                icon.setVisibility(INVISIBLE);
                txtCnt.setVisibility(INVISIBLE);
            }
        }
    }

    public Item getItme(CusCalView.SimpleDate date){
        return cusCalView.dataMap.get(date);
    }

    public void responseDataSetChange(Map<CusCalView.SimpleDate, Item> itemMap){
        cusCalView.dataMap.clear();
        cusCalView.dataMap.putAll(itemMap);
        cusCalView.notifyChangedDataSet();
    }

    public void responseSetItem(CusCalView.SimpleDate date, Item item){
        cusCalView.dataMap.put(date,item);
        cusCalView.notifyChangeItem(date);
    }

    public void responseClearItem(CusCalView.SimpleDate date){
        cusCalView.dataMap.remove(date);
        cusCalView.notifyChangeItem(date);
    }

    public interface Requester{
        void onItemClick(CusCalView.SimpleDate date);
    }
}
