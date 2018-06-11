package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techwork.kjc.mvp_project.activity.ACT3_Login;

public class TestController extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ACT3_Login.class);
        intent.putExtra(ACT3_Login.REQUEST_LISTNER, new ACT3_Login.RequestListener() {
            @Override
            public void onRequestLogin(String email, String password) {

            }
        });
        startActivity(intent);
    }
}
