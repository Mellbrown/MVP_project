package com.techwork.kjc.mvp_project.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;

public class PracticeMenuDialog extends Dialog {
    ImageView act4_sub_focus;
    ImageView act4_sub_cycle;
    Requester requester;

    public PracticeMenuDialog(@NonNull Context context, Requester requester){
        super(context);

        this.requester = requester;

        setContentView(R.layout.act4_submenu);

        act4_sub_focus = findViewById(R.id.act4_sub_focus);
        act4_sub_cycle = findViewById(R.id.act4_sub_cycle);

        act4_sub_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.FocusActivityStart();
            }
        });
        act4_sub_cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.CycleActivityStart();
            }
        });

    }


    public interface Requester{
        void FocusActivityStart();
        void CycleActivityStart();
    }

}
