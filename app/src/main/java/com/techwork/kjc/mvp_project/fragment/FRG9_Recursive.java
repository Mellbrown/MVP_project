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
import com.techwork.kjc.mvp_project.dialog.InputRecursiveDialog;
import com.techwork.kjc.mvp_project.dialog.ShowRecursiveDialog;
import com.techwork.kjc.mvp_project.subview.CusCalView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
                        Item item = dataMap.get(viewHolder.getCurBinded());
                        if(item == null) item = new Item();
                        List<ShowRecursiveDialog.Item> itemList = new ArrayList<>();
                        int i = 1;
                        for(Record record : item.records){
                            itemList.add(new ShowRecursiveDialog.Item(i++,
                                    record.queue.get(0),
                                    record.queue.get(1),
                                    record.queue.get(2),
                                    record.queue.get(3), record.reps));
                        }
                        new ShowRecursiveDialog(getContext(), itemList, v1 -> {
                            new InputRecursiveDialog(getContext(), (long timestamp, List<String> queue, int reps) -> {
                                SimpleDate curBinded = viewHolder.getCurBinded();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new Date(timestamp));
                                Log.i("삽입자", String.format("year %d, month %d, date %d",curBinded.year, curBinded.month,curBinded.date));
                                cal.set(curBinded.year, curBinded.month - 1,curBinded.date);
                                requester.requestUpload(cal.getTime().getTime(),queue,reps);
                            }).show();
                        }).show();
                    }
                });
                return viewHolder;
            }
        };
        frame.addView(cusCalView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        responseDataSetChange(requester.requestInitData());
        return viewLayout;
    }

    public static class Record{
        public long timestamp = 0;

        public static final String arm = "팔";
        public static final String leg = "다리";
        public static final String back = "등(배)";
        public static final String body = "전신";

        final List<String> queue = new ArrayList<>();
        private int reps = 0;

        public Record(long timestamp, List<String> queue, int reps){
            this.timestamp = timestamp;
            this.queue.addAll(queue);
            this.reps = reps;
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
            if(data != null && data.records.size() > 0){
                icon.setVisibility(VISIBLE);
                txtCnt.setVisibility(VISIBLE);
                int sum = 0;
                for(Record record : data.records) sum += record.reps;
                txtCnt.setText(sum + " 회");
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
        Map<CusCalView.SimpleDate, Item> requestInitData();
        void requestUpload(long timestamp, List<String> queue, int reps);
    }
}
