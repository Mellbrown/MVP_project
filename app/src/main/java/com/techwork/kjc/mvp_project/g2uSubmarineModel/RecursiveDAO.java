package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.RecursiveBean;
import com.techwork.kjc.mvp_project.util.DateKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecursiveDAO {
    public static final String REMOTE_PATH = "recursive";

    public static void selectTop30(DateKey monthKey,OnSelelctedTop30 OnSelelctedTop30){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Long> all = new HashMap<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Map<String, RecursiveBean> focusBeanMap = data.getValue(new GenericTypeIndicator<Map<String, RecursiveBean>>() {});
                    if (focusBeanMap == null) focusBeanMap = new HashMap<>();
                    int cnt = 0;
                    for(RecursiveBean bean : focusBeanMap.values()){
                        DateKey dateKey = new DateKey(bean.timestamp);
                        if(dateKey.year != monthKey.year || dateKey.month != monthKey.month) continue;
                        cnt += bean.reps;
                    }
                }
                OnSelelctedTop30.onSelctecteTop30(all);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public interface OnSelelctedTop30{
        void onSelctecteTop30(Map<String, Long> map);
    }

    public static void selectRecursiveMap(String uid,OnSelectedRecursiveMap onSelectedRecursiveMap){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, RecursiveBean> beanMap = dataSnapshot.getValue(new GenericTypeIndicator<Map<String, RecursiveBean>>() {});
                if (beanMap == null) {
                    beanMap = new HashMap<>();
                }

                Map<Long, RecursiveBean> recursiveBeanMap = new HashMap<>();
                for(String key : beanMap.keySet()){
                    recursiveBeanMap.put(Long.valueOf(key), beanMap.get(key));
                }
                onSelectedRecursiveMap.OnSelectedRecursiveMap(true,recursiveBeanMap,null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedRecursiveMap.OnSelectedRecursiveMap(false,null, databaseError);
            }
        });
    }

    public interface OnSelectedRecursiveMap{
        void OnSelectedRecursiveMap(boolean success, Map<Long, RecursiveBean> recursiveBeanMap, DatabaseError databaseError);
    }

    public static Task<Void> addRecursivBean(String uid, Long timstamp, RecursiveBean recursiveBean){
        recursiveBean.timestamp = new Date().getTime();
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).child(timstamp+"").setValue(recursiveBean);
    }
}
