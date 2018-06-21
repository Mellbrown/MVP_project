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

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

public class FRG8_Graph extends Fragment {
    private View viewLayout;
    private FrameLayout act8_date;
    private FrameLayout act8_graph;
    public Requester requester;
    BarChart mBarChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act8_focus_graph, container,false);

        mBarChart = viewLayout.findViewById(R.id.barchart);

        GraphSet(2, 2, 0xFF123456);
        GraphSet(3, 5, 0xFF123456);
        GraphSet(1, 6, 0xFF123456);
        GraphSet(7,3, 0xFF123456); // 그래프 추가 함수 만들어놓음 하나 추가 될때마다 색깔바뀌는건 알고리즘 고민좀 해주세요
        // addGraph(그날에 맞는 운동횟수(그래프 수), 강도, 횟수 );


        mBarChart.startAnimation();


        return viewLayout;
    }

    public interface Requester{
        void requesterGetDate();

        void requesterGetData(int level, int reps);

    }
    public void GraphSet(int level, int reps, int color){
        String Slevel = String.valueOf(level) + "Kg";
        mBarChart.addBar(new BarModel(Slevel,reps, color)); // 라벨부분이라 스트링으로 변환해서 넘겨줘야함
    }

    public void addGraph(int arraySize, int level, int reps){

        for(int i = arraySize; i > 0 ; i--){
            GraphSet(level, reps, 0xFF123456); // 칼라 어뜨케좀 해주셈
        }

    }

}
