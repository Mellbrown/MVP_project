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

/**
 * Created by kjc on 2018-06-12.
 */

public class FRG7_Focus extends Fragment {
    private View viewLayout;
    private ImageView act7_title;
    private ImageView act7_sub1;
    private ImageView act7_sub2;
    private ImageView act7_sub3;
    private ImageView act7_sub4;
    private ImageView act7_sub5;
    private TextView act7_arm;
    private TextView act7_leg;
    private TextView act7_body;
    private TextView act7_abody;
    private int page = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act7__focus, container, false);
        act7_title = viewLayout.findViewById(R.id.act7_title);
        act7_sub1 = viewLayout.findViewById(R.id.act7_sub1);
        act7_sub2 = viewLayout.findViewById(R.id.act7_sub2);
        act7_sub3 = viewLayout.findViewById(R.id.act7_sub3);
        act7_sub4 = viewLayout.findViewById(R.id.act7_sub4);
        act7_sub5 = viewLayout.findViewById(R.id.act7_sub5);
        act7_arm = viewLayout.findViewById(R.id.act7_arm);
        act7_leg = viewLayout.findViewById(R.id.act7_leg);
        act7_body = viewLayout.findViewById(R.id.act7_body);
        act7_abody = viewLayout.findViewById(R.id.act7_abody);

//        act7_arm.setOnClickListener(
//                Requester.requestPageChange();
//        );
        return viewLayout;
    }
    public interface Requester{
        // 팔, 다리 등 클릭시 페이지변경 및 데이터변경
        void requestPageChange();
    }
    public void responsePageChange(){

    }

}