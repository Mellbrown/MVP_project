package com.techwork.kjc.mvp_project.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.fragment.FRG10_Record;
import com.techwork.kjc.mvp_project.subview.CusCalView;

import java.util.Map;

public class Third_RecordController extends AppCompatActivity implements View.OnClickListener {
    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    }

    private void redering(Map<CusCalView.SimpleDate, FRG10_Record.Item> dataMap){
        FRG10_Record frg10_record = new FRG10_Record();
        frg10_record.dataMap =dataMap;
        frg10_record.onClickListener = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg10_record,"frg10_record");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this,Fourth_MVPController.class));
    }
}
