package com.techwork.kjc.mvp_project.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.techwork.kjc.mvp_project.R;

public class FRG10_MVP extends Fragment{
    private View viewLayout;
    private RecyclerView act_m;
    private RecyclerView act_v;
    private RecyclerView act_p;
    private Button mvp;
    public Requester requester;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act10_mvp, container,false);
        act_m = viewLayout.findViewById(R.id.act10_m);
        act_v = viewLayout.findViewById(R.id.act10_v);
        act_p = viewLayout.findViewById(R.id.act10_p);
        mvp = viewLayout.findViewById(R.id.mvp);


        mvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.getMVPprofileImage(); // 일등한놈 이미지 경로
                String path = null;
                requester.MvpPrintDialog(path); // 넘겨줘서 다이얼로그에서 이미지 셋해주셈
            }
        });


        return viewLayout;
    }
    public interface Requester{
        void setMrecycleView();
        void setVrecycleView();
        void setPrecycleView();
        void MvpPrintDialog(String path);
        void getMVPprofileImage();
    }

}
