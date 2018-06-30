package com.techwork.kjc.mvp_project.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.techwork.kjc.mvp_project.R;

import java.util.HashMap;
import java.util.Iterator;

public class FRG4_setMenu extends Fragment implements View.OnClickListener {
    View viewLayout;

    private EditText act2_id;
    private EditText act2_pw;
    private EditText act2_name;
    private Spinner act2_sex;
    private EditText act2_school;
    private Spinner act2_grade;
    private Spinner act2_cls;
    private Spinner act2_num;
    private EditText act2_tall;
    private EditText act2_weight;
    private ImageView act2_image;
    private Button act2_Rbtn;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act_menu_set, container,false);

        act2_id = viewLayout.findViewById(R.id.act2_id);
        act2_pw = viewLayout.findViewById(R.id.act2_pw);
        act2_name = viewLayout.findViewById(R.id.act2_name);

        act2_sex = viewLayout.findViewById(R.id.act2_sex);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(getContext(), R.array.act2_sex, android.R.layout.simple_spinner_item);
        act2_sex.setAdapter(adapter1);
        act2_sex.setSelection(0);

        act2_school = viewLayout.findViewById(R.id.act2_school);

        act2_grade = viewLayout.findViewById(R.id.act2_grade);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(getContext(), R.array.act2_grade, android.R.layout.simple_spinner_item);
        act2_grade.setAdapter(adapter2);
        act2_grade.setSelection(0);


        act2_cls = viewLayout.findViewById(R.id.act2_cls);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(getContext(), R.array.act2_cls, android.R.layout.simple_spinner_item);
        act2_cls.setAdapter(adapter3);
        act2_cls.setSelection(0);

        act2_num = viewLayout.findViewById(R.id.act2_num);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(getContext(), R.array.act2_num, android.R.layout.simple_spinner_item);
        act2_num .setAdapter(adapter4);
        act2_num.setSelection(0);

        act2_tall = viewLayout.findViewById(R.id.act2_tall);
        act2_weight = viewLayout.findViewById(R.id.act2_weight);
        act2_image = viewLayout.findViewById(R.id.act2_image);
        act2_Rbtn = viewLayout.findViewById(R.id.act2_Rbtn);

        return viewLayout;
    }

    @Override
    public void onClick(View v) {

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
                , String act2_weight
                , ImageView act2_iv
        );
        void requestImagePath();
    }

    public void responseImagePath(Uri imageUri){

        act2_image.setImageURI(imageUri);
    }

}
