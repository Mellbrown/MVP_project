package com.techwork.kjc.mvp_project.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.techwork.kjc.mvp_project.R;

public class FRG8_FocusRegister  extends Fragment{
    private View viewLayout;
    private Button act8_save;
    private Button act8_clear;
    private NumberPicker act8_level;
    private NumberPicker act8_reps;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewLayout = inflater.inflate(R.layout.act8_focus_register_track, container,false);

        act8_save = viewLayout.findViewById(R.id.act8_save);
        act8_clear = viewLayout.findViewById(R.id.act8_clear);
        act8_level = viewLayout.findViewById(R.id.act8_level);
        act8_reps = viewLayout.findViewById(R.id.act8_reps);

        act8_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // requster.focus_register(level, reps);
            }
        });
        act8_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // requster.focus_register();
            }
        });

        return viewLayout;
    }
    public interface Requester{
        void focus_register(); //level, reps
        void focus_clear();
    }

    public void focus_clear(){
                // set NumberPick number 0
    }
    public void focus_register(int level, int Reps){

    }

}
