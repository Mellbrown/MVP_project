package com.techwork.kjc.mvp_project.fireSource;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewParent;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.techwork.kjc.mvp_project.controller.TestController;
import com.techwork.kjc.mvp_project.fireSource.fireclass.UserFire;
import com.techwork.kjc.mvp_project.fireSource.fireclass.mFClass;
import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Fire_GOS {
    FirebaseDatabase mDb;
    DatabaseReference mRf;
    String uid;
    UserFire uf;
    boolean ready,rInfo;

    public Fire_GOS(){
        mDb = FirebaseDatabase.getInstance();
        mRf = mDb.getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public void setFM(HashMap<String,String> info, String date, FRG5_Measure act) {
        mFClass mF = new mFClass();
        mF.setData(info);
        FirebaseDatabase.getInstance().getReference().child("user").child(uid).child(date).setValue(mF);
    }
    public void getFM(String date,FRG5_Measure act){
        mFClass mF = new mFClass();
        FirebaseDatabase.getInstance().getReference().child("user").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                uf = ((UserFire) dataSnapshot.getValue(UserFire.class));
                mRf.child(uf.school).child(uf.grade).child(uf.cls).child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dataSnapshot.getValue();
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
//        if(op==0) {
//
//        }else{
//            FirebaseDatabase.getInstance().getReference().child("m").
//        }
    }

    void requestFV(int op){

    }

    void requestFP(int dv,int op){


    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //iv->image (User Image Only)
    void getUserImage(String user,ImageView iv){
        ArrayList<byte[]> imageArr;
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://mvp-project-29e04.appspot.com");
        StorageReference storageRef = storage.getReference().child("user").child("images").child(user);

        final long ONE_MEGABYTE = 1024 * 1024;

        storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                iv.setImageBitmap(Bitmap.createScaledBitmap(bmp, iv.getWidth(),
                        iv.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

}
