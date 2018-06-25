package com.techwork.kjc.mvp_project.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;
import com.techwork.kjc.mvp_project.subview.SubFRG10_RecordCal;

import java.util.List;

public class FRG10_MVP extends Fragment implements View.OnClickListener {
    private View viewLayout;

    private Button btnM;
    private Button btnV;
    private Button btnP;
    private Button btnShowMVP;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerAdapter<Item,ViewHodler> baseRecyclerAdapter;

    public Requester requester;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act10_record, container, false);

        btnM = viewLayout.findViewById(R.id.btnM);
        btnV = viewLayout.findViewById(R.id.btnV);
        btnP = viewLayout.findViewById(R.id.btnP);
        btnShowMVP = viewLayout.findViewById(R.id.btnShowMVP);

        btnM.setOnClickListener(this);
        btnV.setOnClickListener(this);
        btnP.setOnClickListener(this);
        btnShowMVP.setOnClickListener(this);

        recyclerView = viewLayout.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        baseRecyclerAdapter = new BaseRecyclerAdapter<Item, ViewHodler>(
                R.layout.item_mvp, ViewHodler.class
        ) {
            @Override
            public void dataConvertViewHolder(ViewHodler holder, Item data) {
                holder.txtNum.setText(data.num);
                holder.txtName.setText(data.name);
                switch (curpage){
                    case R.id.btnM : {
                        holder.txtVal.setText(String.format("%.2f", data.mVal));
                    }break;
                    case R.id.btnV : {
                        holder.txtVal.setText(String.format("%d승 %d패",data.vWin, data.vLoose));
                    }break;
                    case R.id.btnP : {
                        holder.txtVal.setText(String.format("%d회",data.pVal));
                    }break;
                }
            }
        };

        responseDataset(requester.requestInitMdataset());

        return viewLayout;
    }

    int curpage = R.id.btnM;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnM : {
                if(curpage == R.id.btnM) break;
                curpage = R.id.btnM;
                requester.requestMdataset();
            }break;
            case R.id.btnV : {
                if(curpage == R.id.btnV) break;
                curpage = R.id.btnV;
                requester.requestVdataset();
            }break;
            case R.id.btnP : {
                if(curpage == R.id.btnP) break;
                curpage = R.id.btnP;
                requester.requestPdataset();
            }break;

            case R.id.btnShowMVP:{
                requester.reqestShowMVP();
            }
        }
    }

    public interface Requester{
        List<Item> requestInitMdataset();
        void requestMdataset();
        void requestVdataset();
        void requestPdataset();
        void reqestShowMVP();
    }

    public void responseDataset(List<Item> items){
        baseRecyclerAdapter.dataList.clear();
        baseRecyclerAdapter.dataList.addAll(items);
        baseRecyclerAdapter.notifyDataSetChanged();
    }

    public static class Item{
        public int num;
        public String name;

        public double mVal;

        public int vWin;
        public int vLoose;

        public int pVal;

        public Item(int num, String name, double mVal){
            this.num = num;
            this.name = name;
            this.mVal = mVal;
        }

        public Item(int num, String name, int vWin, int vLoose){
            this.num = num;
            this.name = name;
            this.vWin = vWin;
            this.vLoose = vLoose;
        }

        public Item(int num, String name, int pVal){
            this.num = num;
            this.name = name;
            this.pVal = pVal;
        }
    }

    public static class ViewHodler extends RecyclerView.ViewHolder{

        public TextView txtNum;
        public TextView txtName;
        public TextView txtVal;

        public ViewHodler(View itemView) {
            super(itemView);

            txtNum = itemView.findViewById(R.id.txtNum);
            txtName = itemView.findViewById(R.id.txtName);
            txtVal = itemView.findViewById(R.id.txtVal);
        }
    }
}
