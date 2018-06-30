package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.layoutModel.RingView;

import java.util.ArrayList;

public class FRG4_MenuMain extends Fragment implements  RingView.OnClickListner{

    private View viewLayout;
    public Requester requester;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        viewLayout = inflater.inflate(R.layout.act4_mainmenu, container,false);
        RingView rv = (RingView)viewLayout.findViewById(R.id.act4_menu);

        String[] str = {"M", "V", "P","R"};
        int[] colors = {R.color.arc1, R.color.arc2, R.color.arc3, R.color.arc21};

        rv.setTotalSection(4);
        rv.setSelectPosition(1);
        rv.initDash(str,colors);
        rv.startAnim(800);

        rv.setOnClickListener(this);

        Button bt = viewLayout.findViewById(R.id.act4_logout);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.mypage();
            }
        });

        viewLayout.findViewById(R.id.chk).setOnClickListener(v -> requester.Logout());

        return viewLayout;
    }

    @Override
    public void onClick(int i) {
        switch (i){
            case 0:{
                requester.MeasureActivityStart();
            }break;
            case 1:{
                requester.VersusActivityStart();
            }break;
            case 2:{
                requester.PracticeDialogStart();
            }break;
            case 3:{
                requester.RecordActivityStart();
            }break;
        }
    }

    public interface Requester{

        void MeasureActivityStart();
        void VersusActivityStart();
        void PracticeDialogStart();
        void RecordActivityStart();
        void mypage();
        void Logout();

    }
}
