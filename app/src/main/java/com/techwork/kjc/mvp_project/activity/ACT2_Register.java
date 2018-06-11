package com.techwork.kjc.mvp_project.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.bean.UserInfoBean;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class ACT2_Register extends AppCompatActivity {

    public static final String REQUEST_LISTNER = "request_listener";
    private static final int GALLERY_CODE = 10;
    public static final int REQ_CODE = 11111;

    private String imagePath;
    private EditText act2_id;
    private EditText act2_pw;
    private EditText act2_name;
    private EditText act2_sex;
    private EditText act2_school;
    private EditText act2_grade;
    private EditText act2_cls;
    private EditText act2_num;
    private EditText act2_tall;
    private EditText act2_weight;
    private ImageView act2_image;
    private Button act2_Rbtn;

    RequestListener requestListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2__register);

        act2_id = (EditText) findViewById(R.id.act2_id);
        act2_pw = (EditText) findViewById(R.id.act2_pw);
        act2_name = (EditText) findViewById(R.id.act2_name);
        act2_sex = (EditText) findViewById(R.id.act2_sex);
        act2_school = (EditText) findViewById(R.id.act2_school);
        act2_grade = (EditText) findViewById(R.id.act2_grade);
        act2_cls = (EditText) findViewById(R.id.act2_cls);
        act2_num = (EditText) findViewById(R.id.act2_num);
        act2_tall = (EditText) findViewById(R.id.act2_tall);
        act2_weight = (EditText) findViewById(R.id.act2_weight);
        act2_image = findViewById(R.id.act2_image);
        act2_Rbtn = findViewById(R.id.act2_Rbtn);

        requestListener = getIntent().getParcelableExtra(REQUEST_LISTNER);

        act2_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GALLERY_CODE);
            }
        });
        act2_Rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestListener.requestSignup(act2_id.getText().toString()
                        , act2_pw.getText().toString()
                        , act2_name.getText().toString()
                        , act2_sex.getText().toString()
                        , act2_school.getText().toString()
                        , act2_grade.getText().toString()
                        , act2_cls.getText().toString()
                        , act2_num.getText().toString()
                        , act2_tall.getText().toString()
                        , act2_weight.getText().toString());

                requestListener.getimagePath(imagePath);
            }
        });

        requestListener.getInstance(this);
    }
    public interface RequestListener extends Parcelable {
        void getInstance(ACT2_Register instance);
        void requestSignup(String act2_id
                , String act2_pw
                , String act2_name
                , String act2_sex
                , String act2_school
                , String act2_grade
                , String act2_cls
                , String act2_num
                , String act2_tall
                , String act2_weight);
        void getimagePath(String imagePath);

    }
    public String getPath(Uri uri){
        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);
        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(index);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_CODE){

            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            act2_image.setImageURI(Uri.fromFile(f));

            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == REQ_CODE){

                if(requestCode == RESULT_OK){
                    String name = data.getExtras().getString("name");

                }
            }
        }
    }

}