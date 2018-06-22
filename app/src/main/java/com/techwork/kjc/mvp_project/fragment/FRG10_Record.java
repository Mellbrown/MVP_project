package com.techwork.kjc.mvp_project.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

public class FRG10_Record extends Fragment {
    private FrameLayout act10_cal;
    private Button king;
    private View viewLayout;
    public Requester requester;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act10_record, container,false);
        act10_cal = viewLayout.findViewById(R.id.act10_cal);
        king = viewLayout.findViewById(R.id.king);

        king.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.MVPactivityStart();
            }
        });

        return viewLayout;
    }

    public interface Requester{
        void setCal();
        void MVPactivityStart();
    }
}
