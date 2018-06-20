package com.techwork.kjc.mvp_project.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.dialog.InputMeasureRecordDialog;
import com.techwork.kjc.mvp_project.dialog.ShowPreScriptionDialog;
import com.techwork.kjc.mvp_project.fragment.FRG1_Splash;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.fragment.FRG3_Login;
import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;
import com.techwork.kjc.mvp_project.fragment.FRG6_Versus;
import com.techwork.kjc.mvp_project.fragment.FRG7_Focus;
import com.techwork.kjc.mvp_project.subview.CusCalView;
import com.techwork.kjc.mvp_project.util.PhotoProcess;

import java.util.ArrayList;

public class TestController extends AppCompatActivity {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    int containerID;

    public static class Some{
        public boolean on = false;

        Some(boolean on){
            this.on = on;
        }
    }

    public static class SomeViewHolder extends CusCalView.CusCalViewHolder<Some>{

        private View viewLayout;
        private Button button;

        public SomeViewHolder(@NonNull Context context) {
            super(context);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup contener) {
            viewLayout = inflater.inflate(R.layout.item_cuscal_example,contener,false);
            button = viewLayout.findViewById(R.id.button);
            return viewLayout;
        }

        @Override
        public void onDataBind(Some data) {
            if(data != null && data.on)
                button.setBackgroundColor(Color.BLUE);
            else
                button.setBackgroundColor(Color.RED);
        }
    }
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
        CusCalView<Some, SomeViewHolder> cusCalView = new CusCalView<Some, SomeViewHolder>(this) {
            @Override
            public SomeViewHolder getNewInstanceViewHolder() {
                final SomeViewHolder someViewHolder = new SomeViewHolder(getContext());
                someViewHolder.button.setOnClickListener((View v)->{
                    Some some = dataMap.get(someViewHolder.getCurBinded());
                    if(some != null && some.on){
                        dataMap.remove(someViewHolder.getCurBinded());
                    }else{
                        dataMap.put(someViewHolder.getCurBinded(),new Some(true));
                    }
                    notifyChangeItem(someViewHolder.getCurBinded());
                });
                return someViewHolder;
            }
        };
        frameLayout.addView(cusCalView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);


//        rendingFRG3_Login();
//        renderingFRG7_Focus();
//        rendingFRG5_Measure();
//        renderingFRG6_Versus();
    }



    void renderingFRG6_Versus(){
        FRG6_Versus frg6_versus = new FRG6_Versus();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg6_versus,"frg6_versus");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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

            }
        };
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg3_login);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Log.i("count : ",fragmentManager.getBackStackEntryCount()+"");
    }

    public void rendingFRG5_Measure(){
        FRG5_Measure frg5_measure = new FRG5_Measure();
        frg5_measure.requester = new FRG5_Measure.Requester() {
            @Override
            public void requestMeasureItemBeans() {
                ArrayList<FRG5_Measure.MeasureItemBean> measureItemBeans = new ArrayList<>();
                for (int i = 0 ; 30 > i ; i++)
                    measureItemBeans.add(new FRG5_Measure.MeasureItemBean(true));

                ((FRG5_Measure) fragmentManager.findFragmentByTag("frg5_measure"))
                        .responseMeasureItemBeans(measureItemBeans);
            }

            @Override
            public void requestAddMeasureItem() {
                new InputMeasureRecordDialog(TestController.this, new InputMeasureRecordDialog.OnSaveListener() {
                    @Override
                    public void onSave(FRG5_Measure.MeasureItemBean measureItemBean) {
                        ((FRG5_Measure) fragmentManager.findFragmentByTag("frg5_measure"))
                                .responseAddMeasureItem(measureItemBean);
                    }
                }).show();
            }

            @Override
            public void requestShowPrescription(FRG5_Measure.MeasureItemBean measureItemBean) {
                new ShowPreScriptionDialog(TestController.this, new ShowPreScriptionDialog.Prescription(
                    measureItemBean.allBodyWeight + "라? 초 돼지임 ㅇㅇ. 한강물 체크하고 오셈",
                        new ShowPreScriptionDialog.EachPrescription(ShowPreScriptionDialog.EachPrescription.BOTTOM_CLASS, "하타치야!!" ),
                        new ShowPreScriptionDialog.EachPrescription(ShowPreScriptionDialog.EachPrescription.MIDDLE_CLASS, "중타치야!!!"),
                        new ShowPreScriptionDialog.EachPrescription(ShowPreScriptionDialog.EachPrescription.TOP_CLASS, "상타치야!!!"),
                        new ShowPreScriptionDialog.EachPrescription(ShowPreScriptionDialog.EachPrescription.BOTTOM_CLASS, "결국엔 넌 하타치였어. \n  하타치야!!")
                )).show();
            }

            @Override
            public void requestRemoveThisMeasureItem(FRG5_Measure.MeasureItemBean measureItemBean) {
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
