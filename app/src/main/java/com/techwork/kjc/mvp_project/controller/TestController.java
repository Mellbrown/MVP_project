package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
<<<<<<< HEAD
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techwork.kjc.mvp_project.activity.ACT2_Register;
=======
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techwork.kjc.mvp_project.activity.ACT3_Login;
>>>>>>> d6e6edab821d8e44d593b167aad41098bfdfda55

public class TestController extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

<<<<<<< HEAD
        Intent intent = new Intent(this, ACT2_Register.class);
        intent.putExtra(ACT2_Register.REQUEST_LISTNER, new ACT2_Register.RequestListener() {
            @Override
            public void getInstance(ACT2_Register instance) {

            }

            @Override
            public void requestSignup(String act2_id, String act2_pw, String act2_name, String act2_sex, String act2_school, String act2_grade, String act2_cls, String act2_num, String act2_tall, String act2_weight) {

            }

            @Override
            public void getimagePath(String imagePath) {

            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
=======
        Intent intent = new Intent(this, ACT3_Login.class);
        intent.putExtra(ACT3_Login.REQUEST_LISTNER, new ACT3_Login.RequestListener() {
            @Override
            public void onRequestLogin(String email, String password) {
>>>>>>> d6e6edab821d8e44d593b167aad41098bfdfda55

            }
        });
        startActivity(intent);
    }
}
