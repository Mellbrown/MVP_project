package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

public class CusInputUpdownCounter extends FrameLayout{

    private View viewLayout;

    private TextView label;
    private TextView txtUnit;
    private EditText iptVal;
    private FloatingActionButton btnPlus;
    private FloatingActionButton btnMinus;

    private OnChangedValue onChangedValue;

    public CusInputUpdownCounter(@NonNull Context context,String sLabel, String sUnit, int initValue, OnChangedValue onChangedValue) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.custview_input_updown_counter,this,false);
        addView(viewLayout);

        label = viewLayout.findViewById(R.id.label);
        txtUnit = viewLayout.findViewById(R.id.txtUnit);
        iptVal = viewLayout.findViewById(R.id.iptVal);
        btnPlus = viewLayout.findViewById(R.id.btnPlus);
        btnMinus = viewLayout.findViewById(R.id.btnMinus);

        this.onChangedValue =  onChangedValue;

        btnPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer value = Integer.valueOf(iptVal.getText().toString());
                value+=1;
                iptVal.setText(value+"");
                onChangedValue.onChangedValue(value);
            }
        });

        btnMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer value = Integer.valueOf(iptVal.getText().toString());
                if(value > 0) value-=1;
                iptVal.setText(value+"");
                onChangedValue.onChangedValue(value);
            }
        });

        iptVal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer value;
                try {
                    value = Integer.valueOf(s.toString());
                }catch (NumberFormatException e){
                    iptVal.setText("0");
                    value = 0;
                }
                onChangedValue.onChangedValue(value);
            }
        });

        label.setText(sLabel);
        txtUnit.setText(sUnit);
        iptVal.setText(initValue+"");
    }

    public interface OnChangedValue{
        void onChangedValue(int value);
    }
}
