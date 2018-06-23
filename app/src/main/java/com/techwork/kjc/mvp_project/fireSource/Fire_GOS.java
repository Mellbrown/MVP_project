package com.techwork.kjc.mvp_project.fireSource;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.techwork.kjc.mvp_project.controller.Third_VersusController;
import com.techwork.kjc.mvp_project.fireSource.fireclass.UserFire;
import com.techwork.kjc.mvp_project.fireSource.fireclass.mFClass;
import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Fire_GOS {
    FirebaseDatabase mDb;
    DatabaseReference mRf;
    String uid;
    UserFire uf;
    HashMap<UserFire,Bitmap> res1;
    HashMap<UserFire,Bitmap> res2;
    ArrayList<UserFire> userArray;
    UserFire myInfo;


    public Fire_GOS(){
        mDb = FirebaseDatabase.getInstance();
        mRf = mDb.getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        res1 = new HashMap<>();
        res2 = new HashMap<>();
    }

    public void setFM(HashMap<String,String> info, String date) {
        mFClass mF = new mFClass();
        mF.setData(info);

        FirebaseDatabase.getInstance().getReference().child("user").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uf = ((UserFire) dataSnapshot.getValue(UserFire.class));
                mRf.child(uf.school).child(uf.grade).child(uf.cls).child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseDatabase.getInstance().getReference().child("m").child(uf.school).child(uf.grade).child(uf.cls).child(uid).child(date).setValue(mF);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void getFM(FRG5_Measure act){
        mFClass mF = new mFClass();
        FirebaseDatabase.getInstance().getReference().child("user").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uf = ((UserFire) dataSnapshot.getValue(UserFire.class));
                FirebaseDatabase.getInstance().getReference().child("m").child(uf.school).child(uf.grade).child(uf.cls).child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<mFClass> res = new ArrayList<>();
                        for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                            mFClass mfc = messageSnapshot.getValue(mFClass.class);
                            res.add(mfc);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setFV(String winner, String loser){
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");

        FirebaseDatabase.getInstance().getReference().child("V").child(myInfo.school).child(myInfo.cls).child(winner).child(sdf.format(dt).toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    FirebaseDatabase.getInstance().getReference().child("V").child(myInfo.school).child(myInfo.cls).child(uid).child(sdf.format(dt).toString()).child("win").setValue(1);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("V").child(myInfo.school).child(myInfo.cls).child(uid).child(sdf.format(dt).toString()).child("win").setValue(Integer.valueOf(dataSnapshot.getValue().toString())+1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference().child("V").child(myInfo.school).child(myInfo.cls).child(loser).child(sdf.format(dt).toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()==null){
                    FirebaseDatabase.getInstance().getReference().child("V").child(myInfo.school).child(myInfo.cls).child(loser).child(sdf.format(dt).toString()).child("lose").setValue(1);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("V").child(myInfo.school).child(myInfo.cls).child(loser).child(sdf.format(dt).toString()).child("lose").setValue(Integer.valueOf(dataSnapshot.getValue().toString())+1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getFV(){

    }

    public void getFVUser(Third_VersusController act){

        FirebaseDatabase.getInstance().getReference().child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myInfo = new UserFire();
                userArray = new ArrayList<>();
                userArray.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getKey().equals(uid)) {
                        myInfo = ds.getValue(UserFire.class);
                    }else
                        userArray.add(ds.getValue(UserFire.class));
                }
                new Fire_GOS().getUserImage(myInfo,userArray,act);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    void setFP(){

    }

    void getFP(){

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //iv->image (User Image Only)
    public void getUserImage(UserFire myInfo , ArrayList<UserFire> uf, Third_VersusController act){
        ArrayList<byte[]> imageArr;
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://mvp-project-29e04.appspot.com");
        res1.clear();
        res2.clear();
        final long ONE_MEGABYTE = 1024 * 1024;
        storage.getReference().child("user").child("images").child(myInfo.uid).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {

            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                Bitmap cont = Bitmap.createScaledBitmap(bmp, 100,100 , false);
                res1.put(myInfo,cont);
                if(uf.size()==res2.size()&&res1.size()==1){
                    act.renderingFRG6_Versus(res1, res2);
                }
            }
        });
        for(UserFire x : uf ){
            storage.getReference().child("user").child("images").child(x.uid).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    Bitmap cont = Bitmap.createScaledBitmap(bmp, 100,100 , false);
                    res2.put(x,cont);
                    Log.w("testing",String.valueOf(res2.size()));
                    Log.w("testing",String.valueOf(res1.size()));
                    if(uf.size()==res2.size()&&res1.size()==1){
                        act.renderingFRG6_Versus(res1, res2);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }

    }

}
