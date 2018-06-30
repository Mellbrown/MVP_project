package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
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
            Toast.makeText(Second_LoginController.this, "로그인 정보를 올바르게 입력해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로그인 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(id,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Second_LoginController.this, "로그인 되었습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Second_LoginController.this, "로그인에 실패 하엿습니다.\n" + task.getException().getMessage() , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
