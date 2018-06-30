package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.FocusBean;
import com.techwork.kjc.mvp_project.util.DateKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FocusDAO {

    public static final String REMOTE_PATH = "focus";

    public static void selectTop30(DateKey monthKey,OnSelelctedTop30 OnSelelctedTop30){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Long> all = new HashMap<>();
                for(DataSnapshot data1 : dataSnapshot.getChildren()){
                    long cnt = 0;
                    for (DataSnapshot data : data1.getChildren()){
                        Map<String, FocusBean> focusBeanMap = data.getValue(new GenericTypeIndicator<Map<String, FocusBean>>() {});
                        if (focusBeanMap == null) focusBeanMap = new HashMap<>();

                        for(FocusBean bean : focusBeanMap.values()){
                            Log.i("FocuseDao", bean.toString());
                            DateKey dateKey = new DateKey(bean.timestamp);
                            if(dateKey.year != monthKey.year || dateKey.month != monthKey.month) continue;
                            cnt += bean.reps;
                        }
                    }
                    all.put(data1.getKey(), cnt);
                }
                OnSelelctedTop30.onSelctecteTop30(all);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                OnSelelctedTop30.onSelctecteTop30(new HashMap<>());
            }
        });
    }
    public interface OnSelelctedTop30{
        void onSelctecteTop30(Map<String, Long> map);
    }


    public static Task<Void> addFocusBean(String uid, String part, FocusBean focusBean){
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH)
                .child(uid).child(part).child(focusBean.timestamp+"").setValue(focusBean);
    }

    public static void selectAllFocusBeans(String uid, String part,OnSelectedFocusBeans onSelectedFocusBeans){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH)
                .child(uid).child(part).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, FocusBean> focusBeanMap = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, FocusBean>>() {});
                if (focusBeanMap == null) focusBeanMap = new HashMap<>();
                List<FocusBean> focusBeans = new ArrayList<>();
                focusBeans.addAll(focusBeanMap.values());
                onSelectedFocusBeans.OnSelectedFocusBeans(true, focusBeans, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedFocusBeans.OnSelectedFocusBeans(false, null, databaseError);
            }
        });
    }

    public static void selectAllFocusBeans(String uid, OnSelectedFocusBeans onSelectedFocusBeans){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH)
                .child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<FocusBean> focusBeans = new ArrayList<>();
                for(DataSnapshot o : dataSnapshot.getChildren()){
                    Map<String, FocusBean> focusBeanMap = o.getValue(new GenericTypeIndicator<Map<String, FocusBean>>() {});
                    if (focusBeanMap == null) focusBeanMap = new HashMap<>();
                    focusBeans.addAll(focusBeanMap.values());
                }
                onSelectedFocusBeans.OnSelectedFocusBeans(true, focusBeans, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedFocusBeans.OnSelectedFocusBeans(false, null, databaseError);
            }
        });
    }

    public interface OnSelectedFocusBeans{
        void OnSelectedFocusBeans(boolean success, List<FocusBean> focusBeans,DatabaseError databaseError );
    }
}
