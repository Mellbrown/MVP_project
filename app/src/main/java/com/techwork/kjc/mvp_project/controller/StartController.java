package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.fireSource.Fire_Auth;
import com.techwork.kjc.mvp_project.fragment.FRG1_Splash;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.fragment.FRG3_Login;
import com.techwork.kjc.mvp_project.fragment.FRG4_MenuMain;
import com.techwork.kjc.mvp_project.util.PhotoProcess;

import java.util.ArrayList;
import java.util.HashMap;

public class StartController extends AppCompatActivity implements FRG1_Splash.Requester, FRG4_MenuMain.Requester {

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

        //Start----------------------------------------로그인 여부에 따라
        //renderingFRG1_Slpash() 할지
        //rendingFRG4_MainMenu()할지

        //로그인 이벤트 발생하면, rendingFRG4_MainMenu해주고

        //로그아웃 이벤트 발생하면 rendringFRG1_Splash해주고
    }

    // 시작 스플레시 화면 떳음
    void rendingFRG1_Splash(){
        FRG1_Splash frg1_splash = new FRG1_Splash();
        frg1_splash.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg1_splash);
        fragmentTransaction.commit();
    }

    void rendingFRG4_MainMenu(){
        FRG4_MenuMain frg4_mainmenu = new FRG4_MenuMain();
        frg4_mainmenu.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg4_mainmenu);
        fragmentTransaction.commit();
    }

    @Override // 스플레시 화면에서 다른 화면 이동을 요구
    public void requestLinkTo(FRG1_Splash.REQUEST_LINK request_link) {
        switch (request_link){
            case SIGNIN:{
                startActivity(new Intent(StartController.this, Second_LoginController.class));
            } break;
            case SIGNUP:{
                startActivity(new Intent(StartController.this, Second_RegisterController.class));
            }break;
        }
    }

    // 메뉴 화면이 다른 화면을 이동을 요구
    @Override
    public void MeasureActivityStart() {

    }

    @Override
    public void VersusActivityStart() {

    }

    @Override
    public void PracticeDialogStart() {

    }

    @Override
    public void RecordActivityStart() {

    }
}