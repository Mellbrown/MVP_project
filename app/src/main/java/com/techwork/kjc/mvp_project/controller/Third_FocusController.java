package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.fragment.FRG7_Arm;
import com.techwork.kjc.mvp_project.fragment.FRG7_Leg;
import com.techwork.kjc.mvp_project.fragment.FRG7_aBody;
import com.techwork.kjc.mvp_project.fragment.FRG7_back;
import com.techwork.kjc.mvp_project.util.FRG7_Requester;
import com.techwork.kjc.mvp_project.util.g2u;

public class Third_FocusController extends AppCompatActivity implements g2u.PatchRouterFRG7, FRG7_Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

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

        // 시작부터는 팔 페이지를 보여줍니다.ㅇ
        renderingArm();
        curPage = arm;
    }

    @Override //화면에서
    public void TrackActivityStart(String part) {

    }

    // 팔 페이지 렌더링
    private void renderingArm(){
        FRG7_Arm frg7_arm = new FRG7_Arm();
        frg7_arm.requester = this;
        frg7_arm.tabPatch = this; // 탭 버튼 이벤트 패치.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg7_arm,"frg7_arm");
        fragmentTransaction.commit();
    }

    // 다리 페이지 렌더링
    private void rederingLeg(){
        FRG7_Leg frg7_leg = new FRG7_Leg();
        frg7_leg.requester = this;
        frg7_leg.tabPatch = this; // 탭 버튼 이벤트 패치.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg7_leg,"frg7_leg");
        fragmentTransaction.commit();
    }

    // 등 페이지 렌더링
    private void rederingBack(){
        FRG7_back frg7_back = new FRG7_back();
        frg7_back.requester = this;
        frg7_back.tabPatch = this; // 탭 버튼 이벤트 패치.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg7_back,"frg7_back");
        fragmentTransaction.commit();
    }

    // 전신 페이지 렌더링
    private void rederinBody(){
        FRG7_aBody frg7_aBody = new FRG7_aBody();
        frg7_aBody.requester = this;
        frg7_aBody.tabPatch = this; // 탭 버튼 이벤트 패치.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg7_aBody,"frg7_aBody");
        fragmentTransaction.commit();
    }

    private int curPage; // 현재 보이는 페이지
    @Override // 페이지 이동
    public void route(int where) {
        switch (where){
            case arm:{
                if(curPage == arm) break;
                renderingArm();
                curPage = arm;
            }break;
            case leg:{
                if(curPage == leg) break;
                rederingLeg();
                curPage = leg;
            }break;
            case back:{
                if(curPage == back) break;
                rederingBack();
                curPage = leg;
            }break;
            case body:{
                if(curPage == body) break;
                rederinBody();
                curPage = body;
            }break;
        }
    }
}
