package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.fireSource.Fire_Auth;
import com.techwork.kjc.mvp_project.fragment.FRG3_Login;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.util.ArrayList;

public class Second_LoginController extends AppCompatActivity implements FRG3_Login.Requester {

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

        //이 컨트롤러는 로그인 담당만 합니다. 렌더링 될것인 이 것 뿐입니다.
        rendingFRG3_Login();
    }

    // 로그인 화면 렌더링
    void rendingFRG3_Login(){
        FRG3_Login frg3_login = new FRG3_Login();
        frg3_login.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg3_login);
        fragmentTransaction.commit();
    }

    @Override // 화면에서 로그인 요청이 있네요
    public void onRequestLogin(ArrayList<String> info) {
        // 덕구짱이 info에 대강 알아서 로그인 아이디와 패스워드를 넘겼을 겁니다.
        String id = info.get(0);
        String pw = info.get(1);
        if(id.equals("") || pw.equals("")){

        }
        //빠베로 대강 로그인 처리를 해줍시다.

        // 뭥 로그인 실패에 대한 처리는 여기서 대강 토스트로 처리해도 되겠져?

        // 로그인 성공하면 역시 이 컨트롤러는 수명을 다합니다.
        // 로그인 성공하면 다음
        EventChain.complete("로그인 되었어여");
        finish();
        //코드를 박아 줍시다.
    }
}
