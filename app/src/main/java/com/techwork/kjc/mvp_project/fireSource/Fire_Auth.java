package com.techwork.kjc.mvp_project.fireSource;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.techwork.kjc.mvp_project.controller.TestController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Fire_Auth {
    private ArrayList<String> info;
    FirebaseDatabase mDB;
    DatabaseReference mRf;
    String uid;

    public Fire_Auth(){
        info = new ArrayList<>();
        info.add("null");
        info.add("null");
    }

    public void setInfo(ArrayList<String> info){
        this.info = info;
    }
    public void testLogin(Activity act){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword("kkddgg1001@gmail.com","11111111").addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()==true) {
                    Log.w("testing","Login");
                }else {

                }
            }
        });
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //Login
    public void goLogin(Activity act){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(this.info.get(0),this.info.get(1)).addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()==true) {
                    TestController cur = (TestController) act;
                    cur.rendingFRG4_MainMenu();
                }else {

                }
            }
        });
    }
    // save storage
    void upLoadStorage(Activity act, ImageView imageView, HashMap<String, String> mInfo) {

        StorageReference mStorage = FirebaseStorage.getInstance("gs://mvp-project-29e04.appspot.com").getReference();
        mStorage = mStorage.child("user").child("images").child(uid);

        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mStorage.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                userInsertDB(mInfo);
                Log.w("testing","Storage Upload");
            }
        });
    }
    // Create Auth
    public void createUserAuth(Activity act, ImageView imageView, HashMap<String, String> mInfo){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(mInfo.get("id"),mInfo.get("pw")).addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()==true){
                    upLoadStorage(act,imageView,mInfo);
                    Log.w("testing","Auth create");
                }
            }
        });
    }
    // DB Insert
    void userInsertDB(HashMap<String, String> mInfo){
        mDB = FirebaseDatabase.getInstance();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRf = mDB.getReference().child("user").child(uid);
        mRf.setValue(mInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.w("testing","DB realTime");
            }
        });
    }
}
