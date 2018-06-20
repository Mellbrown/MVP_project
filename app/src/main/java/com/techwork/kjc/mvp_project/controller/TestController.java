package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.bean.MeasureItemBean;
import com.techwork.kjc.mvp_project.dialog.InputMeasureRecordDialog;
import com.techwork.kjc.mvp_project.fragment.FRG1_Splash;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.fragment.FRG3_Login;
import com.techwork.kjc.mvp_project.fragment.FRG4_MenuMain;
import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;
import com.techwork.kjc.mvp_project.fragment.FRG7_Focus;
import com.techwork.kjc.mvp_project.util.PhotoProcess;

import java.lang.reflect.Array;
import java.util.ArrayList;

import resource.FirebaseResource;

public class TestController extends AppCompatActivity {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    int containerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
//        rendingFRG3_Login();
//        renderingFRG7_Focus();
        rendingFRG4_MainMenu();
    }

    void renderingFRG7_Focus(){
        FRG7_Focus act7_focus = new FRG7_Focus();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, act7_focus,"act7_focus");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    void rendingFRG1_Splash(){
        FRG1_Splash frg1_splash = new FRG1_Splash();
        frg1_splash.requester = new FRG1_Splash.Requester() {
            @Override
            public void requestLinkTo(FRG1_Splash.REQUEST_LINK request_link) {
                switch (request_link){
                    case SIGNIN: TestController.this.rendingFRG3_Login();break;
                    case SIGNUP: TestController.this.rendingFRG2_Register();break;
                }
            }
        };
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg1_splash);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
    }

    void rendingFRG2_Register(){
        FRG2_Register frg2_register = new FRG2_Register();
        frg2_register.requester = new FRG2_Register.Requester() {
            @Override
            public void requestSignup(String act2_id, String act2_pw, String act2_name, String act2_sex, String act2_school, String act2_grade, String act2_cls, String act2_num, String act2_tall, String act2_weight) {

            }

            @Override
            public void requestImagePath() {
                startActivityForResult(new Intent(TestController.this, PhotoProcess.class),0);
            }


        };
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID,frg2_register,"frg2_register");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
    }

    void rendingFRG3_Login(){
        FRG3_Login frg3_login = new FRG3_Login();
        frg3_login.requester = new FRG3_Login.Requester() {
            @Override
            public void onRequestLogin(ArrayList<String> info) {
                new FirebaseResource().goLogin(info,TestController.this);

            }
        };
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg3_login);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
    }

    void rendingFRG4_MainMenu(){
        FRG4_MenuMain frg4_mainmenu = new FRG4_MenuMain();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg4_mainmenu);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
    }

    public void rendingFRG5_Measure(){
        FRG5_Measure frg5_measure = new FRG5_Measure();
        frg5_measure.requester = new FRG5_Measure.Requester() {
            @Override
            public void requestMeasureItemBeans() {
                ArrayList<MeasureItemBean> measureItemBeans = new ArrayList<>();
                for (int i = 0 ; 30 > i ; i++)
                    measureItemBeans.add(new MeasureItemBean(true));

                ((FRG5_Measure) fragmentManager.findFragmentByTag("frg5_measure"))
                        .responseMeasureItemBeans(measureItemBeans);
            }

            @Override
            public void requestAddMeasureItem() {
                new InputMeasureRecordDialog(TestController.this, new InputMeasureRecordDialog.OnSaveListener() {
                    @Override
                    public void onSave(MeasureItemBean measureItemBean) {
                        ((FRG5_Measure) fragmentManager.findFragmentByTag("frg5_measure"))
                                .responseAddMeasureItem(measureItemBean);
                    }
                }).show();
            }

            @Override
            public void requestRemoveThisMeasureItem(MeasureItemBean measureItemBean) {
                ((FRG5_Measure) fragmentManager.findFragmentByTag("frg5_measure"))
                        .responseRemoveSuccessMeasureItem(measureItemBean);
            }
        };
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg5_measure,"frg5_measure");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStack();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == 0){
            String imagePath = data.getStringExtra(PhotoProcess.RES_IMAGE_PATH);
            FRG2_Register frg2_register = ((FRG2_Register) fragmentManager.findFragmentByTag("frg2_register"));
            frg2_register.responseImagePath(imagePath);

        }
    }
}
