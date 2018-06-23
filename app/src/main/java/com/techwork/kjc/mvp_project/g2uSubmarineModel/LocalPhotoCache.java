package com.techwork.kjc.mvp_project.g2uSubmarineModel;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class LocalPhotoCache {
    public static Uri getCacheFileUri(String prefix, String name){
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "techwork.mvp");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File file = new File(storageDir, prefix + "-" +name);
        Uri uri = Uri.fromFile(file);
        return uri;
    }

    public static boolean isExistCacheFile(String prefix, String name){
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "techwork.mvp");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File file = new File(storageDir, prefix + "-" +name);
        return file.exists();
    }
    public static File getCacheFile(String prefix, String name){
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "techwork.mvp");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File file = new File(storageDir, prefix + "-" +name);
        return file;
    }
}
