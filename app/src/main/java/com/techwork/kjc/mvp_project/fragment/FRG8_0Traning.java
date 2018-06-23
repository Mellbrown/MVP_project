package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.subview.CusSelDateView;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Graph;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Histrory;
import com.techwork.kjc.mvp_project.subview.SubFRG8_Track;

import java.util.ArrayList;

public class FRG8_0Traning extends Fragment implements View.OnClickListener {

    private View viewLayout;

    private SubFRG8_Track subFRG8_track;
    private SubFRG8_Histrory subFRG8_histrory;
    private SubFRG8_Graph subFRG8_graph;

    private FrameLayout frame;
    private TextView txtTitle;
    private Button tabTrack;
    private Button tabHistory;
    private Button tabGraph;

    private int curPage = -1;
    private static final int track = 1;
    private static final int history = 2;
    private static final int graph = 3;

    public Requester requester;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act8_0training, container,false);

        frame = viewLayout.findViewById(R.id.frame);

        frame = viewLayout.findViewById(R.id.frame);
        txtTitle = viewLayout.findViewById(R.id.txtTitle);
        tabTrack = viewLayout.findViewById(R.id.tabTrack);
        tabHistory = viewLayout.findViewById(R.id.tabHistory);
        tabGraph = viewLayout.findViewById(R.id.tabGraph);

        tabTrack.setOnClickListener(this);
        tabHistory.setOnClickListener(this);
        tabGraph.setOnClickListener(this);

        txtTitle.setText(requester.setTitle());

        ShowSubFRG8_Track();

        return viewLayout;
    }

    private void ShowSubFRG8_Track(){
        if(curPage == track) return;
        curPage = track;
        subFRG8_track = new SubFRG8_Track(getContext(), new SubFRG8_Track.OnRequesterUploadItem() {
            @Override
            public ArrayList<SubFRG8_Track.Item> onRequestInitdata() {
                return requester.onTrackRequestInitData();
            }

            @Override
            public void onReqeusteUploadItem(int level, int reps) {
                requester.onTrackReqeusteUploadItem(level,reps);
            }
        });
        frame.removeAllViews();
        frame.addView(subFRG8_track,-1,-1);
    }

    public void setTrackItemes(ArrayList<SubFRG8_Track.Item> trackItemes){
        if(curPage != track) return;
        subFRG8_track.setData(trackItemes);
    }

    private void ShowSubFRG8_Histrory(){
        if(curPage == history) return;
        curPage = history;
        subFRG8_histrory = new SubFRG8_Histrory(getContext(), new SubFRG8_Histrory.OnRequesterDataOfDate() {
            @Override
            public ArrayList<SubFRG8_Histrory.Item> onRequesteInitData(CusSelDateView.SimpleDate requestDate) {
                return requester.onHistoryRequesterInitDataOfDate(requestDate);
            }

            @Override
            public void onRequesterDataOfDate(CusSelDateView.SimpleDate requestDate) {
                requester.onHistoryRequesterDataOfDate(requestDate);
            }
        });
        frame.removeAllViews();
        frame.addView(subFRG8_histrory,-1,-1);
    }

    public void setHistoryItemes(ArrayList<SubFRG8_Histrory.Item> historyItemes){
        if(curPage != history) return;
        subFRG8_histrory.setData(historyItemes);
    }

    private void ShowSubFRG8_Graph(){
        if(curPage == graph) return;
        curPage = graph;
        subFRG8_graph = new SubFRG8_Graph(getContext(), new SubFRG8_Graph.OnRequesterDataOfDate() {
            @Override
            public ArrayList<SubFRG8_Graph.Item> requestInitData(CusSelDateView.SimpleDate requestDate) {
                return requester.onGraphRequesterInitDataOfDate(requestDate);
            }

            @Override
            public void onRequesterDataOfDate(CusSelDateView.SimpleDate requestDate) {
                requester.onGraphRequesterDataOfDate(requestDate);
            }
        });
        frame.removeAllViews();
        frame.addView(subFRG8_graph,-1,-1);
    }

    public void setGraphItemes(ArrayList<SubFRG8_Graph.Item> graphItemes){
        if(curPage != graph) return;
        subFRG8_graph.setData(graphItemes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tabTrack : {
                ShowSubFRG8_Track();
            }break;
            case R.id.tabHistory : {
                ShowSubFRG8_Histrory();
            }break;
            case R.id.tabGraph : {
                ShowSubFRG8_Graph();
            }break;
        }
    }

    public interface Requester{
        String setTitle();
        ArrayList<SubFRG8_Track.Item> onTrackRequestInitData();
        ArrayList<SubFRG8_Histrory.Item> onHistoryRequesterInitDataOfDate(CusSelDateView.SimpleDate requestDate);
        ArrayList<SubFRG8_Graph.Item> onGraphRequesterInitDataOfDate(CusSelDateView.SimpleDate requestDate);
        void onTrackReqeusteUploadItem(int level, int reps);
        void onHistoryRequesterDataOfDate(CusSelDateView.SimpleDate requestDate);
        void onGraphRequesterDataOfDate(CusSelDateView.SimpleDate requestDate);
    }
}
