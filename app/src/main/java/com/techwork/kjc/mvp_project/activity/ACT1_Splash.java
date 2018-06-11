package com.techwork.kjc.mvp_project.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.techwork.kjc.mvp_project.R;

import java.io.Serializable;

public class ACT1_Splash extends AppCompatActivity implements View.OnClickListener {

    // 필요한 UI
    private Button linkSignup;
    private Button linkSignin;

    // 요청할 링크 식별자 모음
    public enum REQUEST_LINK{ SIGNUP, SIGNIN }

    //request listner을 위한 intent 파라메터 식별자
    public static final String REQUEST_LISTNER = "REQUEST_LISTNER";
    //request listner
    RequestListener requestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act1_splash);
        // UI 로드
        linkSignup = findViewById(R.id.linkSignup);
        linkSignin = findViewById(R.id.linkSignin);

        linkSignup.setOnClickListener(this);
        linkSignin.setOnClickListener(this);

        //requestlisner 받아오기
        requestListener = ((RequestListener) getIntent().getSerializableExtra(REQUEST_LISTNER));
        if (requestListener == null)
            Log.e("ACT1_Splash", "not receive requestListener");
        //현 액티비티 인스턴스 넘겨주기
        requestListener.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linkSignin:
                requestListener.onRequestLinkTo(REQUEST_LINK.SIGNIN);
                break;
            case R.id.linkSignup:
                requestListener.onRequestLinkTo(REQUEST_LINK.SIGNUP);
                break;
        }
    }

    interface RequestListener extends Serializable{
        void getInstance(ACT1_Splash instance);
        void onRequestLinkTo(REQUEST_LINK request_link);
    }
}
