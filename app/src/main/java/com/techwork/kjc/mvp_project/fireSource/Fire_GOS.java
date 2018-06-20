package com.techwork.kjc.mvp_project.fireSource;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.techwork.kjc.mvp_project.fireSource.fireclass.UserFire;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class Fire_GOS {
    FirebaseDatabase mDb;
    DatabaseReference mRf;
    String uid;
    Fire_GOS(){
        mDb = FirebaseDatabase.getInstance();
        mRf = mDb.getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    //iv->image (User Image Only)
    void getUserImage(String user,ImageView iv){
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
