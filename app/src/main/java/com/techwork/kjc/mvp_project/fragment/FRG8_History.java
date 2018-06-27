package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.subview.CusSelDateView;

public class FRG8_History extends Fragment {

    private View viewLayout;
    private FrameLayout act8_date;
    private RecyclerView act8_history_view;

    // 아이템
    private TextView act8_item_num;
    private TextView act8_item_level;
    private TextView act8_item_reps;

    public Requester requester;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act8_focus_history, container,false);


        act8_date = viewLayout.findViewById(R.id.act8_date);
        act8_history_view = viewLayout.findViewById(R.id.act8_history_view);
        act8_item_num = viewLayout.findViewById(R.id.act8_item_num);
        act8_item_level = viewLayout.findViewById(R.id.act8_item_level);
        act8_item_reps = viewLayout.findViewById(R.id.act8_item_reps);


        CusSelDateView selectDate = new CusSelDateView(getContext(), new CusSelDateView.SimpleDate(), new CusSelDateView.OnChangedDateListener() {
            @Override
            public void onChangedDate(CusSelDateView.SimpleDate date) {

            }
        });
        act8_date.addView(selectDate);

        return viewLayout;
    }

    public interface Requester{
        void requesterSetDate();
        void requestRecyclerView(int date, int level, int reps);
    }
}
