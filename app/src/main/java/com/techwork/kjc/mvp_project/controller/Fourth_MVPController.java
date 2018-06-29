package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseError;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG10_MVP;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.FocusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.MeasureDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.RecursiveDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPublicInfoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.VersusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;
import com.techwork.kjc.mvp_project.util.DateKey;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        MeasureDAO.selectTop30(new DateKey(), new MeasureDAO.OnSelelctedTop30() {
            @Override
            public void onSelctecteTop30(Map<String, Double> map) {
                UserPublicInfoDAO.selectUserByUID(new ArrayList<>(map.keySet()), new UserPublicInfoDAO.OnSelectedLisnter() {
                    @Override
                    public void onSelected(boolean success, Map<String, UserPublicInfoBean> userMap, DatabaseError databaseError) {
                        progressDialog.dismiss();
                        items = new ArrayList<>();
                        for(String uid : map.keySet()){
                            items.add(new FRG10_MVP.Item(0, userMap.get(uid).name, map.get(uid)));
                        }
                        Collections.sort(items, new Comparator<FRG10_MVP.Item>() {
                            @Override
                            public int compare(FRG10_MVP.Item o1, FRG10_MVP.Item o2) {
                                return (int)(o2.mVal * 100 - o1.mVal * 100);
                            }
                        });
                        for(int i = 0 ; items.size() > i; i++) items.get(i).num = i +1;
                        rendering();
                    }
                });
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
        MeasureDAO.selectTop30(new DateKey(), new MeasureDAO.OnSelelctedTop30() {
            @Override
            public void onSelctecteTop30(Map<String, Double> map) {
                UserPublicInfoDAO.selectUserByUID(new ArrayList<>(map.keySet()), new UserPublicInfoDAO.OnSelectedLisnter() {
                    @Override
                    public void onSelected(boolean success, Map<String, UserPublicInfoBean> userMap, DatabaseError databaseError) {
                        progressDialog.dismiss();
                        items = new ArrayList<>();
                        for(String uid : map.keySet()){
                            items.add(new FRG10_MVP.Item(0, userMap.get(uid).name, map.get(uid)));
                        }
                        Collections.sort(items, new Comparator<FRG10_MVP.Item>() {
                            @Override
                            public int compare(FRG10_MVP.Item o1, FRG10_MVP.Item o2) {
                                return (int)(o2.mVal * 100 - o1.mVal * 100);
                            }
                        });
                        for(int i = 0 ; items.size() > i; i++) items.get(i).num = i +1;
                        getinstance().responseDataset(items);
                    }
                });
            }
        });
    }

    @Override
    public void requestVdataset() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        VersusDAO.selectTop30(new DateKey(), new VersusDAO.OnSelelctedTop30() {
            @Override
            public void onSelctecteTop30(Map<String, Integer[]> map) {
                UserPublicInfoDAO.selectUserByUID(new ArrayList<>(map.keySet()), new UserPublicInfoDAO.OnSelectedLisnter() {
                    @Override
                    public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                        progressDialog.dismiss();
                        items = new ArrayList<>();
                        for(String uid : map.keySet()){
                            items.add(new FRG10_MVP.Item(0, userPublicInfoBeanMap.get(uid).name, map.get(uid)[0], map.get(uid)[1]));
                        }
                        Collections.sort(items, new Comparator<FRG10_MVP.Item>() {
                            @Override
                            public int compare(FRG10_MVP.Item o1, FRG10_MVP.Item o2) {
                                return o2.vWin - o1.vWin;
                            }
                        });
                        for(int i = 0 ; items.size() > i; i++) items.get(i).num = i +1;
                        getinstance().responseDataset(items);
                    }
                });
            }
        });
    }

    @Override
    public void requestPdataset() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        EventChain eventChain = new EventChain();

        eventChain.ready("포커즈");
        Map<String, Long> focusMap = new HashMap<>();
        FocusDAO.selectTop30(new DateKey(), new FocusDAO.OnSelelctedTop30() {
            @Override
            public void onSelctecteTop30(Map<String, Long> map) {
                focusMap.putAll(map);
                eventChain.complete("포커즈");
            }
        });

        eventChain.ready("리커시브");
        Map<String, Long> recursiveMap = new HashMap<>();
        RecursiveDAO.selectTop30(new DateKey(), new RecursiveDAO.OnSelelctedTop30() {
            @Override
            public void onSelctecteTop30(Map<String, Long> map) {
                recursiveMap.putAll(map);
                eventChain.complete("리커시브");
            }
        });

        eventChain.andthen(()->{
            // 데이터 병합하기
            Map<String, Long> map = new HashMap<>(focusMap);
            for(String key : recursiveMap.keySet()){
                if(map.containsKey(key)){
                    map.put(key, map.get(key) + recursiveMap.get(key));
                } else map.put(key, recursiveMap.get(key));
            }

            UserPublicInfoDAO.selectUserByUID(new ArrayList<>(map.keySet()), new UserPublicInfoDAO.OnSelectedLisnter() {
                @Override
                public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                    progressDialog.dismiss();
                    items = new ArrayList<>();
                    for(String uid: map.keySet()){
                        items.add(new FRG10_MVP.Item(0, userPublicInfoBeanMap.get(uid).name, (int)(0 + map.get(uid)) ));
                    }
                    Collections.sort(items, new Comparator<FRG10_MVP.Item>() {
                        @Override
                        public int compare(FRG10_MVP.Item o1, FRG10_MVP.Item o2) {
                            return o2.pVal - o1.pVal;
                        }
                    });
                    for(int i = 0 ; items.size() > i; i++) items.get(i).num = i +1;
                    getinstance().responseDataset(items);
                }
            });
        },"포커즈","리커시브");
    }

    @Override
    public void reqestShowMVP() {

    }

    private FRG10_MVP getinstance(){
        return ((FRG10_MVP) fragmentManager.findFragmentByTag("frg10_mvp"));

    }
}
