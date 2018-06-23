package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG9_Recursive;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.RecordDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.RecursiveDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.RecursiveBean;
import com.techwork.kjc.mvp_project.subview.CusCalView;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Third_Recursive extends AppCompatActivity implements FRG9_Recursive.Requester {
    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    Map<Long, RecursiveBean> recursiveBeanMap;

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
        progressDialog.setTitle("데이터 불러오는 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        RecursiveDAO.selectRecursiveMap(uid, new RecursiveDAO.OnSelectedRecursiveMap() {
            @Override
            public void OnSelectedRecursiveMap(boolean success, Map<Long, RecursiveBean> recursiveBeanMap, DatabaseError databaseError) {
                progressDialog.dismiss();
                if(success){
                    Third_Recursive.this.recursiveBeanMap = recursiveBeanMap;
                    renderingFRG9_Recursive();
                } else {
                    Toast.makeText(Third_Recursive.this, "데이터를 불러오는데 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Third_Recursive", databaseError.getMessage());
                    finish();
                }
            }
        });



    }

    void renderingFRG9_Recursive(){
        FRG9_Recursive frg9_recursive = new FRG9_Recursive();
        frg9_recursive.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg9_recursive,"frg9_recursive");
        fragmentTransaction.commit();
    }


    @Override
    public Map<CusCalView.SimpleDate, FRG9_Recursive.Item> requestInitData() {
        Map<CusCalView.SimpleDate, FRG9_Recursive.Item> itemMap = new HashMap<>();
        for(Long timestamp : recursiveBeanMap.keySet()){
            RecursiveBean recursiveBean = recursiveBeanMap.get(timestamp);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(timestamp));
            CusCalView.SimpleDate simpleDate = new CusCalView.SimpleDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
            if(!itemMap.containsKey(simpleDate)){
                itemMap.put(simpleDate,new FRG9_Recursive.Item());
            }
            Log.i("로딩자", String.format("year %d, month %d, date %d",simpleDate.year, simpleDate.month ,simpleDate.date));
            List<String> list = new ArrayList<>();
            list.add(recursiveBean.first);
            list.add(recursiveBean.second);
            list.add(recursiveBean.thrid);
            list.add(recursiveBean.fourth);
            itemMap.get(simpleDate).records.add(new FRG9_Recursive.Record(timestamp,list,Integer.valueOf(recursiveBean.reps+"")));
        }
        return itemMap;
    }

    @Override
    public void requestUpload(long timestamp, List<String> queue, int reps) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 삽입 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        EventChain eventChain = new EventChain();
        eventChain.ready("데이터");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        RecursiveDAO.addRecursivBean(uid,timestamp,new RecursiveBean(
                queue.get(0),
                queue.get(1),
                queue.get(2),
                queue.get(3),(long)reps)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    eventChain.complete("데이터");
                } else {
                    Toast.makeText(Third_Recursive.this, "데이터를 삽입하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Third_Recursive", task.getException().getMessage());
                    progressDialog.dismiss();
                }
            }
        });
        eventChain.andthen(()->{
            RecursiveDAO.selectRecursiveMap(uid, new RecursiveDAO.OnSelectedRecursiveMap() {
                @Override
                public void OnSelectedRecursiveMap(boolean success, Map<Long, RecursiveBean> recursiveBeanMap, DatabaseError databaseError) {
                    progressDialog.dismiss();
                    if(success){
                        Map<CusCalView.SimpleDate, FRG9_Recursive.Item> itemMap = new HashMap<>();
                        for(Long timestamp : recursiveBeanMap.keySet()){
                            RecursiveBean recursiveBean = recursiveBeanMap.get(timestamp);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date(timestamp));
                            CusCalView.SimpleDate simpleDate = new CusCalView.SimpleDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
                            if(!itemMap.containsKey(simpleDate)){
                                itemMap.put(simpleDate,new FRG9_Recursive.Item());
                            }
                            Log.i("로딩자", String.format("year %d, month %d, date %d",simpleDate.year, simpleDate.month ,simpleDate.date));
                            List<String> list = new ArrayList<>();
                            list.add(recursiveBean.first);
                            list.add(recursiveBean.second);
                            list.add(recursiveBean.thrid);
                            list.add(recursiveBean.fourth);
                            itemMap.get(simpleDate).records.add(new FRG9_Recursive.Record(timestamp,list,Integer.valueOf(recursiveBean.reps+"")));
                            ((FRG9_Recursive) fragmentManager.findFragmentByTag("frg9_recursive")).responseDataSetChange(itemMap);
                        }
                    } else {
                        Toast.makeText(Third_Recursive.this, "데이터를 불러오는데 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("Third_Recursive", databaseError.getMessage());
                    }
                }
            });
        },"데이터");
    }
}
