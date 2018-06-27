package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.adapter.BaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubFRG6_SelectRival extends FrameLayout {
    private View viewLayout;

    private ImageView imgPhoto;
    private TextView txtName;
    private FloatingActionButton btnConfirm;

    private RecyclerView recyclerview;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerAdapter<RirvalItem, RirvalViewHolder> recyclerAdapter;

    private int selelcted = -1;
    Requester requester;

    public SubFRG6_SelectRival(@NonNull Context context, Bitmap photo, String name ,Requester requester) {
        super(context);

        this.requester = requester;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.subview_act6_select_rival, this, false);
        addView(viewLayout);

        imgPhoto = viewLayout.findViewById(R.id.imgPhoto);
        txtName = viewLayout.findViewById(R.id.txtName);
        btnConfirm = viewLayout.findViewById(R.id.btnConfirm);

        imgPhoto.setImageBitmap(photo);
        txtName.setText(name);
        btnConfirm.setOnClickListener((View v)->{
            if(selelcted == -1)return;
            RirvalItem rirvalItem = recyclerAdapter.dataList.get(selelcted);
            requester.selectedItem(rirvalItem);
        });

        recyclerview = viewLayout.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerAdapter = new BaseRecyclerAdapter<RirvalItem, RirvalViewHolder>(
                R.layout.item_select_rival, RirvalViewHolder.class
        ) {
            @Override
            public void onCreateAfterViewHolder(RirvalViewHolder holder) {
                holder.itemView.setOnClickListener((View v)-> {
                    int layoutPosition = holder.getLayoutPosition();
                    int prev = selelcted;
                    selelcted = layoutPosition;
                    recyclerAdapter.notifyItemChanged(prev);
                    recyclerAdapter.notifyItemChanged(selelcted);
                });
            }

            @Override
            public void dataConvertViewHolder(RirvalViewHolder holder, RirvalItem data) {
                holder.txtName.setText(data.name);
                holder.imgPhoto.setImageBitmap(data.photo);
                if(holder.getLayoutPosition() == selelcted)
                    holder.itemView.setBackgroundColor(Color.BLUE);
                else
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            }
        };
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(recyclerAdapter);

        List<RirvalItem> rirvalItems = requester.requestItems();

        recyclerAdapter.dataList.clear();
        recyclerAdapter.dataList.addAll(rirvalItems);
        recyclerAdapter.notifyDataSetChanged();;
    }

    public static class RirvalItem{
        public Bitmap photo;
        public String name;

        public RirvalItem(Bitmap photo, String name){
            this.photo = photo;
            this.name = name;
        }
    }

    public static class RirvalViewHolder extends RecyclerView.ViewHolder {

        public View itemView;
        public ImageView imgPhoto;
        public TextView txtName;

        public RirvalViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtName = itemView.findViewById(R.id.txtName);
        }
    }

    public interface Requester{
        List<RirvalItem> requestItems();
        void selectedItem(RirvalItem rirvalItem);
    }
}