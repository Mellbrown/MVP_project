package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG10_Record;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.MVPService;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.MVP_RecordAccBean;
import com.techwork.kjc.mvp_project.subview.CusCalView;
import com.techwork.kjc.mvp_project.util.DateKey;

import java.util.HashMap;
import java.util.Map;

public class Third_RecordController extends AppCompatActivity implements View.OnClickListener {

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


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 로드중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MVPService.selsetMVP_Record(uid, new MVPService.OnCompleteMVP_Record() {
            @Override
            public void onCompleteMVP_Record(Map<DateKey, MVP_RecordAccBean> mvpRecordAccBeanMap) {
                progressDialog.dismiss();
                Map<CusCalView.SimpleDate, FRG10_Record.Item> dataMap = new HashMap<>();
                for(DateKey dateKey : mvpRecordAccBeanMap.keySet()){
                    MVP_RecordAccBean bean = mvpRecordAccBeanMap.get(dateKey);
                    dataMap.put(dateKey.convert2SimDate(), new FRG10_Record.Item(bean.mVal,bean.vVal,bean.pVal));
                }
                redering(dataMap);
            }
        });
    }

    private void redering(Map<CusCalView.SimpleDate, FRG10_Record.Item> dataMap){
        FRG10_Record frg10_record = new FRG10_Record();
        frg10_record.dataMap =dataMap;
        frg10_record.onClickListener = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg10_record);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,Fourth_MVPController.class));
    }
}
