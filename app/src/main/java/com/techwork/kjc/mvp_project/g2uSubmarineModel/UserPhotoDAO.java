package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class UserPhotoDAO {
    public static final String REMOTE_PATH = "user-photo";

    public static void uploadUserPhoto(Uri localPhotoResource, OnUploadComplete onUploadComplete){
        String extend = localPhotoResource.getPath().substring(localPhotoResource.getPath().lastIndexOf('.') + 1);
        final String serversFileName = new Date().getTime() + "-" + ((int) (Math.random() * 1000)) + "." + extend;
        FirebaseStorage.getInstance().getReference(REMOTE_PATH).child(serversFileName)
                .putFile(localPhotoResource).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                onUploadComplete.onUploadComplete(task.isSuccessful(), serversFileName, task.getException());
            }
        });
    }

    public interface OnUploadComplete{
        void onUploadComplete(boolean success, String photoID, Exception exception);
    }

    public static void selectPhotoByPhotoID(String photoID, OnDownloadComplete onDownloadComplete){
        if(LocalPhotoCache.isExistCacheFile("thumnail",photoID)){
            Uri thumnail = LocalPhotoCache.getCacheFileUri("thumnail", photoID);
            onDownloadComplete.OnDownloadComplete(true,thumnail,null);
        }else{
            Uri thumnail = LocalPhotoCache.getCacheFileUri("thumnail", photoID);
            FirebaseStorage.getInstance().getReference(REMOTE_PATH).child(photoID)
                .getFile(thumnail).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                    onDownloadComplete.OnDownloadComplete(task.isSuccessful(),thumnail,task.getException());
                }
            });
        }
    }

    public interface OnDownloadComplete{
        void OnDownloadComplete(boolean success, Uri photoResource,Exception e);
    }

    public static void selectMultiPhotoesByIdes(Set<String> photoIDes, OnMultiDownloadComplete onMultiDownloadComplete){
        final EventChain eventChain = new EventChain();
        final HashMap<String,Uri> photoResourceMap = new HashMap<>();

        for( String photoID : photoIDes){
            eventChain.ready(photoID);
            selectPhotoByPhotoID(photoID, new OnDownloadComplete() {
                @Override
                public void OnDownloadComplete(boolean success, Uri photoResource, Exception e) {
                    if(success) photoResourceMap.put(photoID,photoResource);
                    else photoResourceMap.put(photoID,null);
                    eventChain.complete(photoID);
                }
            });
        }

        eventChain.andthen(new EventChain.CallBack() {
            @Override
            public void run() {
                onMultiDownloadComplete.OnMultiDownloadComplete(photoResourceMap);
            }
        }, new ArrayList<>(photoIDes));
    }

    public interface OnMultiDownloadComplete{
        void OnMultiDownloadComplete(HashMap<String,Uri> photoResourceMap);
    }
}
