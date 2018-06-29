package com.techwork.kjc.mvp_project.controller;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.techwork.kjc.mvp_project.dialog.MvpPrintDialog;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG10_MVP;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.FocusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.MVPService;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.MeasureDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.RecursiveDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPhotoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPublicInfoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.VersusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;
import com.techwork.kjc.mvp_project.util.DateKey;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Fourth_MVPController extends AppCompatActivity implements FRG10_MVP.Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    List<FRG10_MVP.Item> items;

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
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        MVPService.ToolM_Top100(new DateKey(), new MVPService.OnCompleteTop100() {
            @Override
            public void onCompleteTop100(List<FRG10_MVP.Item> items) {
                progressDialog.dismiss();
                Fourth_MVPController.this.items = items;
                rendering();
            }
        });
    }

    private void rendering(){
        FRG10_MVP frg10_mvp = new FRG10_MVP();
        frg10_mvp.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg10_mvp,"frg10_mvp");
        fragmentTransaction.commitNow();
    }

    @Override
    public List<FRG10_MVP.Item> requestInitMdataset() {
        return items;
    }

    @Override
    public void requestMdataset() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        MVPService.ToolM_Top100(new DateKey(), new MVPService.OnCompleteTop100() {
            @Override
            public void onCompleteTop100(List<FRG10_MVP.Item> items) {
                progressDialog.dismiss();
                getinstance().responseDataset(items);
            }
        });
    }

    @Override
    public void requestVdataset() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        MVPService.ToolV_Top100(new DateKey(), new MVPService.OnCompleteTop100() {
            @Override
            public void onCompleteTop100(List<FRG10_MVP.Item> items) {
                progressDialog.dismiss();
                getinstance().responseDataset(items);
            }
        });
    }

    @Override
    public void requestPdataset() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        MVPService.ToolP_Top100(new DateKey(), new MVPService.OnCompleteTop100() {
            @Override
            public void onCompleteTop100(List<FRG10_MVP.Item> items) {
                progressDialog.dismiss();
                getinstance().responseDataset(items);
            }
        });
    }

    @Override
    public void reqestShowMVP() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("이달의 MVP...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        MVPService.selectTopMVP(new DateKey(), uid -> {
            if(uid == null){
                progressDialog.dismiss();
                Toast.makeText(this, "아직 이달의 MVP가 없음.", Toast.LENGTH_SHORT).show();
                return;
            }
            UserPublicInfoDAO.selectUserByUID(new ArrayList<String>() {{ add(uid); }}, new UserPublicInfoDAO.OnSelectedLisnter() {
                @Override
                public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                    if(success && userPublicInfoBeanMap.containsKey(uid)){
                        String photoID = userPublicInfoBeanMap.get(uid).photoID;
                        String name = userPublicInfoBeanMap.get(uid).name;
                        UserPhotoDAO.selectPhotoByPhotoID(photoID, new UserPhotoDAO.OnDownloadComplete() {
                            @Override
                            public void OnDownloadComplete(boolean success, Uri photoResource, Exception e) {
                                progressDialog.dismiss();
                                if(success){
                                    new MvpPrintDialog(Fourth_MVPController.this, photoResource, name).show();
                                } else {
                                    Toast.makeText(Fourth_MVPController.this, "프로필을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                                    Log.e("Fourth_MVPController", e.getMessage());
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Fourth_MVPController.this, "사용자를 불러올수 없습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("Fourth_MVPController", databaseError.getMessage());
                    }
                }
            });
        });
    }

    private FRG10_MVP getinstance(){
        return ((FRG10_MVP) fragmentManager.findFragmentByTag("frg10_mvp"));
    }
}
