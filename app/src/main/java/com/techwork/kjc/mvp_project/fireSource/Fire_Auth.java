package com.techwork.kjc.mvp_project.fireSource;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.techwork.kjc.mvp_project.controller.StartController;
import com.techwork.kjc.mvp_project.fireSource.fireclass.UserFire;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Fire_Auth {
    private ArrayList<String> info;
    FirebaseDatabase mDB;
    DatabaseReference mRf;
    String uid;
    static boolean rLogin;

    public Fire_Auth(){
        info = new ArrayList<>();
        info.add("null");
        info.add("null");
    }

    public void checkLogin(Activity act){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            ((StartController)act).rendingFRG4_MainMenu();
        else
            ((StartController)act).rendingFRG1_Splash();
    }

    public void aLogout(Activity act){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        ((StartController)act).rendingFRG1_Splash();
    }

    public void testLogin(Activity act){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword("kkddgg1001@gmail.com","11111111").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()==true) {
                    checkLogin(act);
                }else {
                    Log.w("teL","Fail");
                }
            }
        });
    }
    //Login
    public void goLogin(Activity act,ArrayList<String> info){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(info.get(0).length()==0) {
            testLogin(act);
            return;
        }
        auth.signInWithEmailAndPassword(this.info.get(0),this.info.get(1)).addOnCompleteListener(act, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()==true) {
                    Toast.makeText(act,"반갑습니다.",Toast.LENGTH_LONG);
                    ((StartController)act).rendingFRG4_MainMenu();
                }else {
                    Toast.makeText(act,"ID 혹은 PW 오류",Toast.LENGTH_LONG);
                }
            }
        });
    }
    // save storage
    void upLoadStorage(Activity act, ImageView imageView, HashMap<String, String> mInfo) {
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                Log.w("testing","Storage Fail");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                HashMap<String, String> info = mInfo;
                info.put("path",taskSnapshot.getDownloadUrl().toString());
                info.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                userInsertDB(info,act);
                Log.w("testing","Storage Upload");
            }
        });
    }
    // Create Auth
    public void createUserAuth(Activity act, ImageView imageView, HashMap<String, String> mInfo){
        Log.w("testing","guwiefhiuw");
        Log.w("testing",mInfo.toString());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(mInfo.get("id"),mInfo.get("pw")).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()==true){
                    Log.w("testing","Auth create");
                    upLoadStorage(act,imageView,mInfo);
                }else{
                    Log.w("testing","Auth Fail");
                }
            }
        });
    }
    // User DB Insert
    void userInsertDB(HashMap<String, String> mInfo, Activity act){
        UserFire goInsert = new UserFire();
        goInsert.setData(mInfo);
        Log.w("testing",mInfo.toString());

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("user").child(uid).setValue(goInsert).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                checkLogin(act);
            }
        });
    }
}