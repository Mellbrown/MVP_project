package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.R;

import java.util.HashMap;
import java.util.Iterator;

public class FRG4_setMenu extends Fragment implements View.OnClickListener {
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.act_menu_set, container,false);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("user-public-info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iter = dataSnapshot.getChildren().iterator();

                while(iter.hasNext()){
                    if(iter.next().getKey()==uid){
                        iter.next().getValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return viewLayout;
    }

    @Override
    public void onClick(View view) {

    }
    //firebase set
    //firebase get =>

}
