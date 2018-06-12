package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.bean.MeasureItemBean;

import java.util.Date;

public class InputMeasureRecordDialog extends Dialog{

    private EditText iptArm;
    private EditText iptLeg;
    private EditText iptBack;
    private EditText iptAllBody;
    private Button btnSave;

    public InputMeasureRecordDialog(@NonNull Context context, final OnSaveListener onSaveListener) {
        super(context);
        setContentView(R.layout.dialog_input_measure_record);

        iptArm = findViewById(R.id.iptArm);
        iptLeg = findViewById(R.id.iptLeg);
        iptBack = findViewById(R.id.iptBack);
        iptAllBody = findViewById(R.id.iptAllBody);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeasureItemBean measureItemBean = new MeasureItemBean();
                measureItemBean.timestamp = new Date().getTime();
                measureItemBean.armWeight = Double.valueOf(iptArm.getText().toString());
                measureItemBean.legWeight = Double.valueOf(iptLeg.getText().toString());
                measureItemBean.backWeight = Double.valueOf(iptBack.getText().toString());
                measureItemBean.allBodyWeight = Double.valueOf(iptAllBody.getText().toString());
                onSaveListener.onSave(measureItemBean);
                dismiss();
            }
        });
    }

    public interface OnSaveListener{
        void onSave(MeasureItemBean measureItemBean);
    }
}
