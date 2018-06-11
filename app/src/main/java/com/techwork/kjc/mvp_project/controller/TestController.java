package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techwork.kjc.mvp_project.activity.ACT5_Measure;

public class TestController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, ACT5_Measure.class));
    }
}
