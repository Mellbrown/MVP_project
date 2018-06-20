package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

public class ShowPreScriptionDialog extends Dialog{

    private TextView txtAll;
    private TextView txtArm;
    private TextView txtLeg;
    private TextView txtBack;
    private TextView txtBody;

    private TextView lblArm;
    private TextView lblLeg;
    private TextView lblBack;
    private TextView lblAll;

    public ShowPreScriptionDialog(@NonNull Context context, Prescription prescription) {
        super(context);
        setContentView(R.layout.dialog_show_prescription);

        txtAll = findViewById(R.id.txtAll);
        txtArm = findViewById(R.id.txtArm);
        txtLeg = findViewById(R.id.txtLeg);
        txtBack = findViewById(R.id.txtBack);
        txtBody = findViewById(R.id.txtBody);

        lblArm = findViewById(R.id.lblArm);
        lblLeg = findViewById(R.id.lblLeg);
        lblBack = findViewById(R.id.lblBack);
        lblAll = findViewById(R.id.lblAll);


        txtAll.setText(prescription.wholeScript);
        bindEachScript(lblArm,txtArm, prescription.arm);
        bindEachScript(lblLeg,txtLeg, prescription.leg);
        bindEachScript(lblBack,txtBack, prescription.back);
        bindEachScript(lblAll,txtBody, prescription.body);
    }

    private void bindEachScript(TextView lblView, TextView textView, EachPrescription eachPrescription){
        switch (eachPrescription.class_){
            case EachPrescription.TOP_CLASS : {
                lblView.setTextColor(Color.GREEN);
            }break;
            case EachPrescription.MIDDLE_CLASS: {
                lblView.setTextColor(Color.YELLOW);
            }break;
            case EachPrescription.BOTTOM_CLASS: {
                lblView.setTextColor(Color.RED);
            }break;
        }

        textView.setText(eachPrescription.script);
    }

    public static class Prescription{
        public String wholeScript;
        public EachPrescription arm;
        public EachPrescription leg;
        public EachPrescription back;
        public EachPrescription body;

        public Prescription(String wholeScript,
                            EachPrescription arm,
                            EachPrescription leg,
                            EachPrescription back,
                            EachPrescription body ){
            this.arm = arm;
            this.leg = leg;
            this.back = back;
            this.body = body;
            this.wholeScript = wholeScript;
        }
    }

    public static class EachPrescription{
        public static final int TOP_CLASS = 0;
        public static final int MIDDLE_CLASS = 1;
        public static final int BOTTOM_CLASS = 2;

        public int class_;
        public String script;

        public EachPrescription(int class_, String script){
            this.class_ = class_;
            this.script = script;
        }
    }
}
