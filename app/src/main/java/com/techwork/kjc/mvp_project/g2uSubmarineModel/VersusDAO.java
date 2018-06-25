package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.VesusBean;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.util.ArrayList;
import java.util.List;

public class VersusDAO {

    public static final String REMOTE_PATH = "versus";

    public static void addVersusBean(String you_uid, String rival_uid, VesusBean vesusBean, OnComplete onComplete){

        EventChain eventChain = new EventChain();
        eventChain.ready(you_uid);
        eventChain.ready(rival_uid);
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH)
                .child(you_uid).child(vesusBean.timestamp + "").setValue(vesusBean)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        eventChain.complete(you_uid);
                    }
                });

        FirebaseDatabase.getInstance().getReference(REMOTE_PATH)
                .child(rival_uid).child(vesusBean.timestamp + "").setValue(vesusBean)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        eventChain.complete(rival_uid);
                    }
                });

        eventChain.andthen(()->{
            onComplete.onComlete();
        },you_uid,rival_uid);
    }

    interface OnComplete{
        void onComlete();
    }

    public static void selectVersusBeans(String you_uid, OnSelectedBersusBeans onSelectedBersusBeans){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(you_uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<VesusBean> vesusBeanList = dataSnapshot.getValue(new GenericTypeIndicator<List<VesusBean>>());
                        if(vesusBeanList == null) vesusBeanList = new ArrayList<>();
                        onSelectedBersusBeans.OnSelectedBersusBeans(vesusBeanList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        onSelectedBersusBeans.OnSelectedBersusBeans(new ArrayList<>());
                    }
                });
    }

    interface OnSelectedBersusBeans{
        void OnSelectedBersusBeans(List<VesusBean> vesusBeans);
    }
}
