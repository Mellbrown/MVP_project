package com.techwork.kjc.mvp_project.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class MeasureItemViewHolder extends RecyclerView.ViewHolder{

    public View itemView;
    public TextView txtDate;
    public TextView txtArmWeight;
    public TextView txtLegWeight;
    public TextView txtBackWeight;
    public TextView txtAllBodyWegith;
    public Button btnShowPrescribe;

    public MeasureItemViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        txtDate = itemView.findViewById(R.id.txtDate);
        txtArmWeight = itemView.findViewById(R.id.txtArmWeight);
        txtLegWeight = itemView.findViewById(R.id.txtLegWeight);
        txtBackWeight = itemView.findViewById(R.id.txtBackWeight);
        txtAllBodyWegith = itemView.findViewById(R.id.txtAllBodyWegith);
        btnShowPrescribe = itemView.findViewById(R.id.btnShowPrescribe);
    }

    public void dataBind(FRG5_Measure.MeasureItemBean data){
        txtDate.setText(data.getStringMdDate());
        txtArmWeight.setText(String.format("%.2fKG", data.armWeight));
        txtLegWeight.setText(String.format("%.2fKG", data.legWeight));
        txtBackWeight.setText(String.format("%.2fKG", data.backWeight));
        txtAllBodyWegith.setText(String.format("%.2fKG", data.allBodyWeight));
    }
}
