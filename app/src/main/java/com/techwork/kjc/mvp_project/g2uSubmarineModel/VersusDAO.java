package com.techwork.kjc.mvp_project.g2uSubmarineModel;

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

    public static final String REMOTE_PATH = "g2u-submarine/versus";

    public static Task<Void> addVersusBean(String you_uid, String rival_uid, VesusBean vesusBean){
        return FirebaseDatabase.getInstance().getReference(REMOTE_PATH)
                .child(you_uid+"&"+rival_uid).child(vesusBean.timestamp + "").setValue(vesusBean);
    }

    public static void selectVersusBeans(String you_uid, String rival_uid, OnSelectedBersusBeans onSelectedBersusBeans){
        final EventChain eventChain = new EventChain();
        eventChain.ready(you_uid+"&"+rival_uid);
        eventChain.ready(rival_uid+"&"+you_uid);
        final List<VesusBean> vesusBeans = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(you_uid+"&"+rival_uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<VesusBean> vesusBeanList = dataSnapshot.getValue(new GenericTypeIndicator<List<VesusBean>>());
                        vesusBeans.addAll(vesusBeanList);
                        eventChain.complete(you_uid+"&"+rival_uid);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        eventChain.complete(you_uid+"&"+rival_uid);
                    }
                });
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(rival_uid+"&"+you_uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<VesusBean> vesusBeanList = dataSnapshot.getValue(new GenericTypeIndicator<List<VesusBean>>());
                        vesusBeans.addAll(vesusBeanList);
                        eventChain.complete(rival_uid+"&"+you_uid);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        eventChain.complete(rival_uid+"&"+you_uid);
                    }
                });
        eventChain.andthen(new EventChain.CallBack() {
            @Override
            public void run() {
                onSelectedBersusBeans.OnSelectedBersusBeans(vesusBeans);
            }
        }, you_uid+"&"+rival_uid, rival_uid+"&"+you_uid);
    }

    interface OnSelectedBersusBeans{
        void OnSelectedBersusBeans(List<VesusBean> vesusBeans);
    }
}
