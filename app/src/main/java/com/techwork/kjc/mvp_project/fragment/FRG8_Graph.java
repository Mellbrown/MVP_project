package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.R;

public class FRG8_Graph extends Fragment {
    private View viewLayout;
    private FrameLayout act8_date;
    private FrameLayout act8_graph;
    public Requester requester;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act8_focus_graph, container,false);
        act8_date = viewLayout.findViewById(R.id.act8_date);
        act8_graph = viewLayout.findViewById(R.id.act8_graph);




        return viewLayout;
    }

    public interface Requester{
        void requesterSetDate();
        void requesterGraphSet(int level, int reps);
    }
}
