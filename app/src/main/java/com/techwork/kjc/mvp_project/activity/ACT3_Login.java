package com.techwork.kjc.mvp_project.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.techwork.kjc.mvp_project.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ACT3_Login extends AppCompatActivity{

    // 필요한 UI
    private Button linkLogin;

    //request listner을 위한 intent 파라메터 식별자
    public static final String REQUEST_LISTNER = "REQUEST_LISTNER";

    //request listner
    ACT3_Login.RequestListener requestListener;
    //ownFunction
    private ArrayList<String> goLogin(){
        ArrayList<String> info = null;
        info.add(((EditText)findViewById(R.id.act3_id)).getText().toString());
        info.add(((EditText)findViewById(R.id.act3_pw)).getText().toString());
        return info;
    }

    void onClick(View v){
        switch (v.getId()){
            case R.id.act3_login:
                ArrayList<String> info = goLogin();
                requestListener.onRequestLogin(info.get(0),info.get(1));
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act3_login);

        // UI 로드
        linkLogin = findViewById(R.id.linkSignup);
        requestListener = ((RequestListener) getIntent().getSerializableExtra(REQUEST_LISTNER));

        if (requestListener == null) {
            Log.e("ACT3_Login","not recieve requestListner");
        }
    }

    public interface RequestListener extends Serializable {
        void onRequestLogin(String email, String password);
    }
}
