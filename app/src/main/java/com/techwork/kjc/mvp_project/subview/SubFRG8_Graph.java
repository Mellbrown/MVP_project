package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.R;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SubFRG8_Graph extends FrameLayout{

    private View viewLayout;

    private FrameLayout sel_date;
    private BarChart mBarChart;

    private CusSelDateView cusSelDateView;
    private CusSelDateView.SimpleDate curDate = new CusSelDateView.SimpleDate();

    private OnRequesterDataOfDate onRequesterDataOfDate;

    public SubFRG8_Graph(@NonNull Context context,  OnRequesterDataOfDate onRequesterDataOfDate) {
        super(context);

        this.onRequesterDataOfDate = onRequesterDataOfDate;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.subview_act8_graph, this, false);
        addView(viewLayout);

        sel_date = viewLayout.findViewById(R.id.sel_date);
        mBarChart = viewLayout.findViewById(R.id.barchart);

        cusSelDateView = new CusSelDateView(getContext(), curDate, new CusSelDateView.OnChangedDateListener() {
            @Override
            public void onChangedDate(CusSelDateView.SimpleDate date) {
                curDate = date;
                onRequesterDataOfDate.onRequesterDataOfDate(date);
            }
        });
        sel_date.addView(cusSelDateView, -1, -2);

        setData(onRequesterDataOfDate.requestInitData(curDate));
    }

    public static class Item {
        public int number;
        public int level;
        public int reps;
    }

    public void setData(ArrayList<Item> items){
        mBarChart.clearChart();
        if(items.size() == 0) return;
        Collections.sort(items,(Item o1,Item o2)->o1.level - o2.level);
        int graphData[] = new int[items.get(items.size()-1).level + 1];
        Arrays.fill(graphData, 0);

        for(int i = 0; items.size() > i ; i++){
            Item item = items.get(i);
            graphData[item.level] += item.reps;
        }

        ArrayList<BarModel> barModels = new ArrayList<>();
        for(int i = 0; graphData.length > i; i++){
            barModels.add(new BarModel(i + ".Lv", graphData[i], Color.YELLOW));
        }

        mBarChart.addBarList(barModels);
    }

    public interface OnRequesterDataOfDate{
        ArrayList<Item> requestInitData(CusSelDateView.SimpleDate requestDate);
        void onRequesterDataOfDate(CusSelDateView.SimpleDate requestDate);
    }
}
