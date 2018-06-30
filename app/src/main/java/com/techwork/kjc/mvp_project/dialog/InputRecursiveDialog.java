package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.subview.CusInputUpdownCounter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputRecursiveDialog extends Dialog implements View.OnClickListener {
    public static final String arm = "팔";
    public static final String leg = "다리";
    public static final String back = "등(배)";
    public static final String body = "전신";

    private Button btnArm;
    private Button btnLeg;
    private Button btnBack;
    private Button btnBody;

    private TextView txtFirst;
    private TextView txtSecond;
    private TextView txtThird;
    private TextView txtFourth;

    private FloatingActionButton btnClear;
    private FloatingActionButton btnConfirm;
    private FrameLayout frame;

    private int reps = 0;

    Map<String, Boolean> pressed = new HashMap<String, Boolean>(){{
        put(arm,false);
        put(leg,false);
        put(back,false);
        put(body,false);
    }};
    List<String> queue = new ArrayList<>();

    void notifyDataChanged(){
//        btnArm.setEnabled(!pressed.get(arm));
//        btnLeg.setEnabled(!pressed.get(leg));
//        btnBack.setEnabled(!pressed.get(back));
//        btnBody.setEnabled(!pressed.get(body));

        txtFirst.setText(queue.size() >= 1 ? queue.get(0) : "");
        txtSecond.setText(queue.size() >= 2 ? queue.get(1) : "");
        txtThird.setText(queue.size() >= 3 ? queue.get(2) : "");
        txtFourth.setText(queue.size() >= 4 ? queue.get(3) : "");
    }

    void press(String btn){
//        if(pressed.get(btn)) return;
//        pressed.put(btn, true);
        if(queue.size() >= 4) return;
        queue.add(btn);

        notifyDataChanged();
    }

    void clear(){
//        pressed = new HashMap<String, Boolean>(){{
//            put(arm,false);
//            put(leg,false);
//            put(back,false);
//            put(body,false);
//        }};
        queue = new ArrayList<>();

        notifyDataChanged();
    }

    public InputRecursiveDialog(@NonNull Context context, OnSave onSave) {
        super(context);
        setContentView(R.layout.dialog_input_recursive);

        btnArm = findViewById(R.id.btnArm);
        btnLeg = findViewById(R.id.btnLeg);
        btnBack = findViewById(R.id.btnBack);
        btnBody = findViewById(R.id.btnBody);

        btnArm.setOnClickListener(this);
        btnLeg.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnBody.setOnClickListener(this);

        txtFirst = findViewById(R.id.txtFirst);
        txtSecond = findViewById(R.id.txtSecond);
        txtThird = findViewById(R.id.txtThird);
        txtFourth = findViewById(R.id.txtFourth);

        btnClear = findViewById(R.id.btnClear);
        btnConfirm = findViewById(R.id.btnConfirm);
        frame = findViewById(R.id.frame);

        CusInputUpdownCounter counter = new CusInputUpdownCounter(context,
                "횟수(Reps)", "개", 0, (int value) -> reps = value);
        frame.addView(counter,-2,-2);

        btnClear.setOnClickListener((v -> clear()));
        btnConfirm.setOnClickListener(v->{
            if(queue.size() < 1) return;
            if(reps < 1) return;
            for(int i = queue.size() ; 4 > i; i++) queue.add("");
            dismiss();
            onSave.onSave(new Date().getTime(), queue, reps);
        });

        notifyDataChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnArm:{ press(arm); }break;
            case R.id.btnLeg:{ press(leg); }break;
            case R.id.btnBack:{ press(back); }break;
            case R.id.btnBody:{ press(body); }break;
        }
    }

    public interface OnSave{
        void onSave(long timestamp, List<String> queue, int reps);
    }
}
