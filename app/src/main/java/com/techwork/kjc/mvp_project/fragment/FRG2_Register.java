package com.techwork.kjc.mvp_project.fragment;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;

import java.io.File;

/**
 * Created by mlyg2 on 2018-06-12.
 */

public class FRG2_Register extends Fragment{

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

    public Requester requester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.act2__register, container,false);

        act2_id = viewLayout.findViewById(R.id.act2_id);
        act2_pw = viewLayout.findViewById(R.id.act2_pw);
        act2_name = viewLayout.findViewById(R.id.act2_name);
        act2_sex = viewLayout.findViewById(R.id.act2_sex);
        act2_school = viewLayout.findViewById(R.id.act2_school);
        act2_grade = viewLayout.findViewById(R.id.act2_grade);
        act2_cls = viewLayout.findViewById(R.id.act2_cls);
        act2_num = viewLayout.findViewById(R.id.act2_num);
        act2_tall = viewLayout.findViewById(R.id.act2_tall);
        act2_weight = viewLayout.findViewById(R.id.act2_weight);
        act2_image = viewLayout.findViewById(R.id.act2_image);
        act2_Rbtn = viewLayout.findViewById(R.id.act2_Rbtn);

        act2_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.requestImagePath();
            }
        });
        act2_Rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requester.requestSignup(act2_id.getText().toString()
                        , act2_pw.getText().toString()
                        , act2_name.getText().toString()
                        , act2_sex.getText().toString()
                        , act2_school.getText().toString()
                        , act2_grade.getText().toString()
                        , act2_cls.getText().toString()
                        , act2_num.getText().toString()
                        , act2_tall.getText().toString()
                        , act2_weight.getText().toString());
            }
        });

        return viewLayout;
    }

    public interface Requester{
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
        void requestImagePath();
    }

    public void responseImagePath(String imagePath){
        File f = new File(imagePath);
        act2_image.setImageURI(Uri.fromFile(f));
    }
}
