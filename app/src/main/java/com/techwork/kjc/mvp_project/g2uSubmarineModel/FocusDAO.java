package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.FocusBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FocusDAO {

    public static final String REMOTE_PATH = "focus";

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

    public interface OnSelectedFocusBeans{
        void OnSelectedFocusBeans(boolean success, List<FocusBean> focusBeans,DatabaseError databaseError );
    }
}
