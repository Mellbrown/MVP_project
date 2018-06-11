package com.techwork.kjc.mvp_project.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.techwork.kjc.mvp_project.R;

import java.util.ArrayList;

/**
 * Created by mlyg2 on 2018-06-12.
 */

public class FRG3_Login extends Fragment implements View.OnClickListener {

    private View viewLayout;

    private Button linkLogin;

    public Requester requester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act3_login, container,false);

        linkLogin = viewLayout.findViewById(R.id.act3_login);
        linkLogin.setOnClickListener(this);

        return viewLayout;
    }

    private ArrayList<String> goLogin(){
        ArrayList<String> info = null;
        info.add(((EditText)viewLayout.findViewById(R.id.act3_id)).getText().toString());
        info.add(((EditText)viewLayout.findViewById(R.id.act3_pw)).getText().toString());
        return info;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.act3_login:
                ArrayList<String> info = goLogin();
                requester.onRequestLogin(info.get(0),info.get(1));
                break;
        }
    }


    public interface Requester{
        void onRequestLogin(String email, String password);
    }
}
