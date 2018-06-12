package com.techwork.kjc.mvp_project.util;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class PhotoProcess extends AppCompatActivity {
    private static final int GALLERY_CODE = 10;
    public static final int REQ_CODE = 11111;

    public static final String RES_IMAGE_PATH = "RES_IMAGE_PATH";

    private String imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }



    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_CODE){
            imagePath = getPath(data.getData());
//            File f = new File(imagePath);
//            act2_image.setImageURI(Uri.fromFile(f));

            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == REQ_CODE){
                if(requestCode == RESULT_OK){
                    String name = data.getExtras().getString("name");

                }
            }
        }

        Intent intent = new Intent();
        intent.putExtra(RES_IMAGE_PATH, imagePath);
        setResult(0, intent);
        finish();
    }
}
