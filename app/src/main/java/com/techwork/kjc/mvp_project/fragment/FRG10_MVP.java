package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techwork.kjc.mvp_project.R;

public class FRG10_MVP extends Fragment{
    private View viewLayout;
    private RecyclerView act_m;
    private RecyclerView act_v;
    private RecyclerView act_p;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act10_mvp, container,false);
        act_m = viewLayout.findViewById(R.id.act10_m);
        act_v = viewLayout.findViewById(R.id.act10_v);
        act_p = viewLayout.findViewById(R.id.act10_p);



        return viewLayout;
    }
}
