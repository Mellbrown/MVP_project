package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.fragment.FRG8_0Traning;
import com.techwork.kjc.mvp_project.fragment.FRG8_Graph;
import com.techwork.kjc.mvp_project.fragment.FRG8_History;
import com.techwork.kjc.mvp_project.fragment.FRG8_Track;
import com.techwork.kjc.mvp_project.subview.CusSelDateView;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Graph;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Histrory;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Track;

import java.util.ArrayList;

public class Fourth_TrackController extends AppCompatActivity implements FRG8_0Traning.Requester {
    public static final String PART = "part";

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    String strTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerID = getResources().getIdentifier("container","id",getPackageName());
        frameLayout = new FrameLayout(this);
        frameLayout.setId(containerID);
        frameLayout.setLayoutParams(
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        setContentView(frameLayout);
        fragmentManager = getSupportFragmentManager();

        strTitle = getIntent().getStringExtra(PART);
        rederingFRG8_0Traning();
    }

    void rederingFRG8_0Traning(){
        FRG8_0Traning frg8_0Traning = new FRG8_0Traning();
        frg8_0Traning.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg8_0Traning,"frg8_0Traning");
        fragmentTransaction.commit();
    }

    @Override
    public String setTitle() {
        return strTitle;
    }

    @Override // 시작되었을때 Track 화면에서 데이터를 요청함
    public void onTrackRequestInitData() {
        ArrayList<SubFRG8_Track.Item> items = new ArrayList<>();
        // 구해서 아래 메서드로 집어 넣어주면됨
        getFragmentInstance().setTrackItemes(items);
    }

    @Override // Track화면에서 해당 아이템을 업로드 해달라고 요청 왔음
    public void onTrackReqeusteUploadItem(int level, int reps) {
        ArrayList<SubFRG8_Track.Item> items = new ArrayList<>();
        // 아이템 삽입된거 여기다 넣어주면 됨
        getFragmentInstance().setTrackItemes(items);
    }

    @Override // 히스토리 화면에서 해당 날짜의 데이터를 넘겨주면 됨
    public void onHistoryRequesterDataOfDate(CusSelDateView.SimpleDate requestDate) {
        ArrayList<SubFRG8_Histrory.Item> items = new ArrayList<>();


        getFragmentInstance().setHistoryItemes(items);
    }

    @Override
    public void onGraphRequesterDataOfDate(CusSelDateView.SimpleDate requestDate) {
        ArrayList<SubFRG8_Graph.Item> items = new ArrayList<>();

        getFragmentInstance().setGraphItemes(items);
    }

    private FRG8_0Traning getFragmentInstance(){
        return ((FRG8_0Traning) fragmentManager.findFragmentByTag("frg8_0Traning"));
    }
}
