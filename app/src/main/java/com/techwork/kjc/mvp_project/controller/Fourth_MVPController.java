package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.fragment.FRG10_MVP;

import java.util.List;

public class Fourth_MVPController extends AppCompatActivity implements FRG10_MVP.Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    List<FRG10_MVP.Item> items;

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

    private void rendering(){
        FRG10_MVP frg10_mvp = new FRG10_MVP();
        frg10_mvp.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg10_mvp,"frg10_mvp");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public List<FRG10_MVP.Item> requestInitMdataset() {
        return null;
    }

    @Override
    public void requestMdataset() {

    }

    @Override
    public void requestVdataset() {

    }

    @Override
    public void requestPdataset() {

    }

    @Override
    public void reqestShowMVP() {

    }
}
