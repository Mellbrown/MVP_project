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
import com.techwork.kjc.mvp_project.fragment.FRG8_0Traning;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.FocusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.FocusBean;
import com.techwork.kjc.mvp_project.subview.CusSelDateView;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Graph;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Histrory;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Track;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fourth_TrackController extends AppCompatActivity implements FRG8_0Traning.Requester {
    public static final String PART = "part";

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    String strTitle;

    List<FocusBean> focusBeans;

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

        strTitle = getIntent().getStringExtra(PART);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 불러오는 중");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FocusDAO.selectAllFocusBeans(uid, strTitle, new FocusDAO.OnSelectedFocusBeans() {
            @Override
            public void OnSelectedFocusBeans(boolean success, List<FocusBean> focusBeans, DatabaseError databaseError) {
                progressDialog.dismiss();
                if(success){
                    Fourth_TrackController.this.focusBeans = focusBeans;
                    rederingFRG8_0Traning();
                } else {
                    Toast.makeText(Fourth_TrackController.this, "데이터를 불러 올수 없습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Fourth_TrackController", databaseError.getMessage());
                    finish();
                }
            }
        });

    }

    void rederingFRG8_0Traning(){
        FRG8_0Traning frg8_0Traning = new FRG8_0Traning();
        frg8_0Traning.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg8_0Traning,"frg8_0Traning");
        fragmentTransaction.commit();
    }

    @Override
    public String setTitle() {
        return strTitle;
    }

    @Override // 시작되었을때 Track 화면에서 데이터를 요청함
    public ArrayList<SubFRG8_Track.Item> onTrackRequestInitData() {
        ArrayList<SubFRG8_Track.Item> items = new ArrayList<>();
        // 구해서 아래 메서드로 집어 넣어주면됨
        int i = 1;
        for(FocusBean focusBean : focusBeans){
            Calendar ical = Calendar.getInstance();
            ical.setTime(new Date(focusBean.timestamp));

            Calendar ncal = Calendar.getInstance();
            ncal.setTime(new Date());

            if(
                ical.get(Calendar.YEAR) == ncal.get(Calendar.YEAR)
                        && ical.get(Calendar.MONTH) == ncal.get(Calendar.MONTH)
                        && ical.get(Calendar.DAY_OF_MONTH) == ncal.get(Calendar.DAY_OF_MONTH)
                ){} else continue;

            SubFRG8_Track.Item item = new SubFRG8_Track.Item();
            item.number = i++;
            item.level = Integer.valueOf(focusBean.level+"");
            item.reps = Integer.valueOf(focusBean.reps+"");
            items.add(item);
        }
        return items;
    }

    @Override
    public ArrayList<SubFRG8_Histrory.Item> onHistoryRequesterInitDataOfDate(CusSelDateView.SimpleDate requestDate) {
        ArrayList<SubFRG8_Histrory.Item> items = new ArrayList<>();

        int i = 1;
        for(FocusBean focusBean : focusBeans){

            Calendar ical = Calendar.getInstance();
            ical.setTime(new Date(focusBean.timestamp));

            if(
                    ical.get(Calendar.YEAR) == requestDate.year
                            && ical.get(Calendar.MONTH) == requestDate.month -1
                            && ical.get(Calendar.DAY_OF_MONTH) == requestDate.date
                    ){} else continue;

            SubFRG8_Histrory.Item item = new SubFRG8_Histrory.Item();
            item.number = i++;
            item.level = Integer.valueOf(focusBean.level+"");
            item.reps = Integer.valueOf(focusBean.reps+"");
            items.add(item);
        }
        return items;
    }

    @Override
    public ArrayList<SubFRG8_Graph.Item> onGraphRequesterInitDataOfDate(CusSelDateView.SimpleDate requestDate) {
        ArrayList<SubFRG8_Graph.Item> items = new ArrayList<>();
        int i = 1;
        for(FocusBean focusBean : focusBeans){

            Calendar ical = Calendar.getInstance();
            ical.setTime(new Date(focusBean.timestamp));

            if(
                    ical.get(Calendar.YEAR) == requestDate.year
                            && ical.get(Calendar.MONTH) == requestDate.month -1
                            && ical.get(Calendar.DAY_OF_MONTH) == requestDate.date
                    ){} else continue;

            SubFRG8_Graph.Item item = new SubFRG8_Graph.Item();
            item.number = i++;
            item.level = Integer.valueOf(focusBean.level+"");
            item.reps = Integer.valueOf(focusBean.reps+"");
            items.add(item);
        }
        return items;
    }


    @Override // Track화면에서 해당 아이템을 업로드 해달라고 요청 왔음
    public void onTrackReqeusteUploadItem(long timestamp, int level, int reps) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 삽입 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FocusBean iptFocusBean = new FocusBean();
        iptFocusBean.timestamp = timestamp;
        iptFocusBean.level = (long)level;
        iptFocusBean.reps = (long)reps;
        final EventChain eventChain = new EventChain();
        eventChain.ready("업로드");
        FocusDAO.addFocusBean(uid,strTitle,iptFocusBean).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    eventChain.complete("업로드");
                } else {
                    Toast.makeText(Fourth_TrackController.this, "서버에 저장하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Fourth_TrackController", task.getException().getMessage());
                    progressDialog.dismiss();
                }
            }
        });

        final EventChain eventChain1 = new EventChain();
        eventChain1.ready("다운로드");
        eventChain.andthen(()->{
            FocusDAO.selectAllFocusBeans(uid, strTitle, new FocusDAO.OnSelectedFocusBeans() {
                @Override
                public void OnSelectedFocusBeans(boolean success, List<FocusBean> focusBeans, DatabaseError databaseError) {
                    if(success){
                        Fourth_TrackController.this.focusBeans = focusBeans;
                        eventChain1.complete("다운로드");
                    } else {
                        Toast.makeText(Fourth_TrackController.this, "데이터를 불러 올수 없습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("Fourth_TrackController", databaseError.getMessage());
                        progressDialog.dismiss();
                    }
                }
            });
        },"업로드");

        eventChain1.andthen(()->{
            progressDialog.dismiss();
            ArrayList<SubFRG8_Track.Item> items = new ArrayList<>();
            int i = 1;
            for(FocusBean focusBean : focusBeans){

                Calendar ical = Calendar.getInstance();
                ical.setTime(new Date(focusBean.timestamp));

                Calendar ncal = Calendar.getInstance();
                ncal.setTime(new Date());

                if(
                        ical.get(Calendar.YEAR) == ncal.get(Calendar.YEAR)
                                && ical.get(Calendar.MONTH) == ncal.get(Calendar.MONTH)
                                && ical.get(Calendar.DAY_OF_MONTH) == ncal.get(Calendar.DAY_OF_MONTH)
                        ){} else continue;

                SubFRG8_Track.Item item = new SubFRG8_Track.Item();
                item.number = i++;
                item.level = Integer.valueOf(focusBean.level+"");
                item.reps = Integer.valueOf(focusBean.reps+"");
                items.add(item);
            }
            getFragmentInstance().setTrackItemes(items);
        }, "다운로드");
    }

    @Override // 히스토리 화면에서 해당 날짜의 데이터를 넘겨주면 됨
    public void onHistoryRequesterDataOfDate(CusSelDateView.SimpleDate requestDate) {
        ArrayList<SubFRG8_Histrory.Item> items = new ArrayList<>();

        int i = 1;
        for(FocusBean focusBean : focusBeans){

            Calendar ical = Calendar.getInstance();
            ical.setTime(new Date(focusBean.timestamp));

            if(
                    ical.get(Calendar.YEAR) == requestDate.year
                            && ical.get(Calendar.MONTH) == requestDate.month -1
                            && ical.get(Calendar.DAY_OF_MONTH) == requestDate.date
                    ){} else continue;

            SubFRG8_Histrory.Item item = new SubFRG8_Histrory.Item();
            item.number = i++;
            item.level = Integer.valueOf(focusBean.level+"");
            item.reps = Integer.valueOf(focusBean.reps+"");
            items.add(item);
        }
        getFragmentInstance().setHistoryItemes(items);
    }

    @Override
    public void onGraphRequesterDataOfDate(CusSelDateView.SimpleDate requestDate) {
        ArrayList<SubFRG8_Graph.Item> items = new ArrayList<>();
        int i = 1;
        for(FocusBean focusBean : focusBeans){
            Calendar ical = Calendar.getInstance();
            ical.setTime(new Date(focusBean.timestamp));

            if(
                    ical.get(Calendar.YEAR) == requestDate.year
                            && ical.get(Calendar.MONTH) == requestDate.month -1
                            && ical.get(Calendar.DAY_OF_MONTH) == requestDate.date
                    ){} else continue;

            SubFRG8_Graph.Item item = new SubFRG8_Graph.Item();
            item.number = i++;

            item.level = Integer.valueOf(focusBean.level+"");
            item.reps = Integer.valueOf(focusBean.reps+"");
            items.add(item);

        }
        getFragmentInstance().setGraphItemes(items);
    }

    private FRG8_0Traning getFragmentInstance(){
        return ((FRG8_0Traning) fragmentManager.findFragmentByTag("frg8_0Traning"));
    }
}
