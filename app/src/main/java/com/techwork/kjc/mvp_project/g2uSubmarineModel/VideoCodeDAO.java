package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideoCodeDAO {

    public static final String REMOTE_PATH = "videocode";

    public static void selectVideoCode(String path, OnSelected onSelected){
        FirebaseDatabase.getInstance().getReference(REMOTE_PATH).child(path).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                onSelected.onSelected(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onSelected.onSelected(null);
            }
        });
    }

    public interface OnSelected{
        void onSelected(String videoCode);
    }
}
