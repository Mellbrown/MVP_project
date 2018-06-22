package com.techwork.kjc.mvp_project.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;

/**
 * Created by mlyg2 on 2018-06-12.
 */

public class FRG1_Splash extends Fragment implements View.OnClickListener {

    // 필요한 UI
    private Button linkSignup;
    private Button linkSignin;

    public enum REQUEST_LINK{ SIGNUP, SIGNIN }
    public Requester requester;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.act1_splash, container,false);
        // UI 로드
        linkSignup = viewLayout.findViewById(R.id.linkSignup);
        linkSignin = viewLayout.findViewById(R.id.linkSignin);

        linkSignup.setOnClickListener(this);
        linkSignin.setOnClickListener(this);




        return viewLayout;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linkSignin:
                requester.requestLinkTo(REQUEST_LINK.SIGNIN);
                break;
            case R.id.linkSignup:
                requester.requestLinkTo(REQUEST_LINK.SIGNUP);
                break;
        }
    }

    public interface Requester{
        void requestLinkTo(REQUEST_LINK request_link);
    }
}
