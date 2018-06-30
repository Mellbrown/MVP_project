package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.MeasureBean;
import com.techwork.kjc.mvp_project.util.DateKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeasureDAO {
    public static final String REMOTE_PATH = "measure";

    public static void selectTop30(DateKey monthKey, OnSelelctedTop30 onSelelctedTop30){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Double> all = new HashMap<>();
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Map<String,MeasureBean> measureBeanMap = data.getValue(new GenericTypeIndicator<Map<String,MeasureBean>>(){});
                    if(measureBeanMap == null) measureBeanMap = new HashMap<>();
                    Double[] o =  new Double[]{0.0, 0.0, 0.0, 0.0};
                    for(MeasureBean bean : measureBeanMap.values()){
                        DateKey dateKey = new DateKey(bean.timestamp);
                        if(dateKey.year != monthKey.year || dateKey.month != monthKey.month) continue;
                        o[0] = o[0] > bean.arm ? o[0] : bean.arm;
                        o[1] = o[1] > bean.leg ? o[1] : bean.leg;
                        o[2] = o[2] > bean.back ? o[2] : bean.back;
                        o[3] = o[3] > bean.body ? o[3] : bean.body;
                    }
                    all.put(data.getKey(), o[0] + o[1] + o[2] + o[3]);
                }
                onSelelctedTop30.onSelctecteTop30(all);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelelctedTop30.onSelctecteTop30(new HashMap<>());
            }
        });
    }

    public interface OnSelelctedTop30{
        void onSelctecteTop30(Map<String, Double> map);
    }

    public static void selectMeasureBeanseByUID(String uid, OnSelectedMeasureBeans onSelectedMeasureBeans){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,MeasureBean> measureBeanMap = dataSnapshot.getValue(new GenericTypeIndicator<Map<String,MeasureBean>>(){});
                if(measureBeanMap == null) measureBeanMap = new HashMap<>();
                List<MeasureBean> measureBeans = new ArrayList<>();
                measureBeans.addAll(measureBeanMap.values());
                if (measureBeans == null) measureBeans = new ArrayList<>();
                onSelectedMeasureBeans.onSelectedMeasureBeans(true, measureBeans, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedMeasureBeans.onSelectedMeasureBeans(false, null, null);
            }
        });
    }

    public static void selectMeasureBeanseByUID(String uid, long startTimestamp, long endTimestamp, OnSelectedMeasureBeans onSelectedMeasureBeans){
        // 아직 미지원 ㅋㅋㅋㅋ
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<MeasureBean> measureBeans = dataSnapshot.getValue(new GenericTypeIndicator<List<MeasureBean>>());
                if (measureBeans == null) measureBeans = new ArrayList<>();
                onSelectedMeasureBeans.onSelectedMeasureBeans(true, measureBeans, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelectedMeasureBeans.onSelectedMeasureBeans(false, null, null);
            }
        });
    }

    public interface OnSelectedMeasureBeans{
        void onSelectedMeasureBeans(boolean success, List<MeasureBean> measureBeans, DatabaseError databaseError);
    }

    public static Task<Void> addMeasureBeanWithUID(String uid, MeasureBean measureBean){
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).child(measureBean.timestamp+"").setValue(measureBean);
    }

    public static Task<Void> removeMeasureBeanWithUID(String uid, MeasureBean measureBean){
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(uid).child(measureBean.timestamp+"").removeValue();
    }
}
