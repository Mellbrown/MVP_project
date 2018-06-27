package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

public class FRG8_FocusHistory extends Fragment{
    View viewLayout;
    TextView act8_date;
    ImageView act8_leftdate;
    ImageView act8_rightdate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act8_focus_register_history, container,false);
        act8_date  = viewLayout.findViewById(R.id.act8_date );
        act8_leftdate = viewLayout.findViewById(R.id.act8_leftdate);
        act8_rightdate = viewLayout.findViewById(R.id.act8_rightdate);

        act8_leftdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // act8_date.setText(date-1) 이전날짜
            }
        });
        act8_rightdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // act_date.setText(date+1) 다음날짜
            }
        });

        return viewLayout;
    }
    public interface Requester{
        // RecycleView
        void getDatedata();
    }

    void getDatadata(){
        //date, num, level, Reps
    }

}
