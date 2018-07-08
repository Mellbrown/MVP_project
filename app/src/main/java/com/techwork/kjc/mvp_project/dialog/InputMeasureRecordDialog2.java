package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;
import com.techwork.kjc.mvp_project.subview.CusInputUpdownCounter;
import com.techwork.kjc.mvp_project.subview.CusSelDateView;
import com.techwork.kjc.mvp_project.util.g2u;

import java.util.Calendar;

public class InputMeasureRecordDialog2 extends Dialog{

    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams;

    private CusSelDateView cusSelDateView;
    private CusInputUpdownCounter cusCounterArm;
    private CusInputUpdownCounter cusCounterLeg;
    private CusInputUpdownCounter cusCounterBack;
    private CusInputUpdownCounter cusCounterBody;
    private Button btnSave;

    private FRG5_Measure.MeasureItemBean measureItemBean = new FRG5_Measure.MeasureItemBean();

    public InputMeasureRecordDialog2(@NonNull Context c, final OnSaveListener onSaveListener) {
        super(c);

        linearLayout = new LinearLayout(c);
        layoutParams = new LinearLayout.LayoutParams(g2u.convertPixelsToDp(250f,c), g2u.convertPixelsToDp(300f,c));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(layoutParams);


        cusSelDateView = new CusSelDateView(c, new CusSelDateView.SimpleDate(),date->{
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(measureItemBean.timestamp);
            cal.set(date.year,date.month -1, date.date);
            measureItemBean.timestamp = cal.getTimeInMillis();
        });
        cusCounterArm = new CusInputUpdownCounter(c,"팔","KG",0, val->{measureItemBean.armWeight = val;});
        cusCounterLeg = new CusInputUpdownCounter(c, "다리", "KG", 0, val->{measureItemBean.legWeight = val;});
        cusCounterBack = new CusInputUpdownCounter(c, "등(배)", "KG", 0, val->{measureItemBean.backWeight = val;});
        cusCounterBody = new CusInputUpdownCounter(c, "전신", "KG", 0, val->{measureItemBean.allBodyWeight = val;});

        btnSave = new Button(c);
        btnSave.setText("저장");
        btnSave.setOnClickListener(v->{onSaveListener.onSave(measureItemBean); dismiss();});

        linearLayout.addView(cusSelDateView);
        linearLayout.addView(cusCounterArm);
        linearLayout.addView(cusCounterLeg);
        linearLayout.addView(cusCounterBack);
        linearLayout.addView(cusCounterBody);
        linearLayout.addView(btnSave);

        setContentView(linearLayout);
    }

    public interface OnSaveListener{
        void onSave(FRG5_Measure.MeasureItemBean measureItemBean);
    }
}
