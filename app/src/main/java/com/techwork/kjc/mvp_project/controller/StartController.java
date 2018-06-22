package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.techwork.kjc.mvp_project.dialog.PracticeMenuDialog;
import com.techwork.kjc.mvp_project.fireSource.Fire_Auth;
import com.techwork.kjc.mvp_project.fragment.FRG1_Splash;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.fragment.FRG3_Login;
import com.techwork.kjc.mvp_project.fragment.FRG4_MenuMain;
import com.techwork.kjc.mvp_project.util.EventChain;
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
        new Fire_Auth().checkLogin(this); // 이코드로 초기화 화면은 정해진다.

        //로그인 이벤트 발생하면, rendingFRG4_MainMenu해주고
        //로그아웃 이벤트 발생하면 rendringFRG1_Splash해주고
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override // 히힝 잠시 로그인 이벤트 쓰려고 잠시 썻어영. 로그인 아웃되거나 인되면, 화면 바까줍니당
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                new Fire_Auth().checkLogin(StartController.this);
            }
        });
    }

    static final int splash = 1;
    static final int main = 2;
    private int curpage = -1;
    // 시작 스플레시 화면 떳음
    public void rendingFRG1_Splash(){
        if(curpage == splash) return;
        curpage = splash;
        FRG1_Splash frg1_splash = new FRG1_Splash();
        frg1_splash.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg1_splash);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void rendingFRG4_MainMenu(){
        if(curpage == main) return;
        curpage = main;
        FRG4_MenuMain frg4_mainmenu = new FRG4_MenuMain();
        frg4_mainmenu.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg4_mainmenu);
        fragmentTransaction.commitAllowingStateLoss();
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
        // 측정 컨트롤러로 가라!
        startActivity(new Intent(StartController.this, Third_MeauserController.class));
    }

    @Override
    public void VersusActivityStart() {
        // 대결 컨트롤러로 가라!
        startActivity(new Intent(StartController.this, Third_VersusController.class));
    }

    @Override
    public void RecordActivityStart() {
        // 앙 아직 만들어졌다 띄
        Toast.makeText(StartController.this, "앙 아직 안들어졌다 띄 앙앙!", Toast.LENGTH_SHORT).show();
    }


    @Override // 냥 냥 나중에 수정된 내용!
    public void Logout() {

    }

    @Override //메뉴중하나는 다이얼로그 띄워서 세부 메뉴를 씁니다.
    public void PracticeDialogStart() {
        final PracticeMenuDialog practiceMenuDialog[] = new PracticeMenuDialog[1];
        practiceMenuDialog[0] = new PracticeMenuDialog(StartController.this, new PracticeMenuDialog.Requester() {
            @Override
            public void FocusActivityStart() {
                //포커즈 컨트롤러로 가라
                startActivity(new Intent(StartController.this, Third_FocusController.class));
                practiceMenuDialog[0].dismiss();
            }

            @Override
            public void CycleActivityStart() {
                // 순환 컨트롤러로 가라
                startActivity(new Intent(StartController.this, Third_Recursive.class));
                practiceMenuDialog[0].dismiss();
            }
        });
        practiceMenuDialog[0].show();
    }
}
