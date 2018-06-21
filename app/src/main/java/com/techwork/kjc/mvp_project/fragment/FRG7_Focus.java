package com.techwork.kjc.mvp_project.fragment;

import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.util.g2u;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FRG7_Focus extends Fragment implements View.OnClickListener {
    private View viewLayout;

    private ConstraintLayout constraintLayout;

    private Button tabArm;
    private Button tabLeg;
    private Button tabBack;
    private Button tabAllBody;
    private ImageView pivot;

    private ArrayList<ImageView> imageViews = new ArrayList<>();

    private static final float circleRadiusdp = 100;
    private static final float imageSizedp = 80;

    public static final String P_ARM = "팔";
    public static final String P_LEG = "다리";
    public static final String P_BACK = "등배";
    public static final String P_All_BODY = "전신";

    private Map<String,List<SelectItem>> itemes = new HashMap<String,List<SelectItem>>(){
        {
            put(P_ARM, new ArrayList<>());
            put(P_LEG, new ArrayList<>());
            put(P_BACK, new ArrayList<>());
            put(P_All_BODY, new ArrayList<>());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act7_focus, container,false);

        constraintLayout = viewLayout.findViewById(R.id.constraintLayout);

        tabArm = viewLayout.findViewById(R.id.tabArm);
        tabLeg = viewLayout.findViewById(R.id.tabLeg);
        tabBack = viewLayout.findViewById(R.id.tabBack);
        tabAllBody = viewLayout.findViewById(R.id.tabAllBody);
        pivot = viewLayout.findViewById(R.id.pivot);

        tabArm.setOnClickListener(this);
        tabLeg.setOnClickListener(this);
        tabBack.setOnClickListener(this);
        tabAllBody.setOnClickListener(this);

        itemes.get(P_ARM).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_ARM).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_ARM).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_ARM).add(new SelectItem(R.drawable.app_icon,null));

        itemes.get(P_LEG).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_LEG).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_LEG).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_LEG).add(new SelectItem(R.drawable.app_icon,null));

        itemes.get(P_BACK).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_BACK).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_BACK).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_BACK).add(new SelectItem(R.drawable.app_icon,null));


        itemes.get(P_All_BODY).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_All_BODY).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_All_BODY).add(new SelectItem(R.drawable.app_icon,null));
        itemes.get(P_All_BODY).add(new SelectItem(R.drawable.app_icon,null));


        SetPage(itemes.get(P_ARM));

        return viewLayout;
    }

    private void SetPage(List<SelectItem> tar){
        if(tar.size() > imageViews.size())
            for(int i = tar.size() - imageViews.size(); i > 0 ; i--)
                genImageView();

        int angle = 360 / tar.size();
        for(int i = 0; imageViews.size() > i; i++){
            ImageView imageView = imageViews.get(i);
            if(i < tar.size()){
                SelectItem selectItem = tar.get(i);
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                        g2u.convertPixelsToDp(imageSizedp, getContext()),
                        g2u.convertPixelsToDp(imageSizedp, getContext()));
                layoutParams.circleConstraint = R.id.pivot;
                layoutParams.circleAngle = angle * i;
                layoutParams.circleRadius = g2u.convertPixelsToDp(circleRadiusdp, getContext());
                imageView.setLayoutParams(layoutParams);
                imageView.setImageResource(selectItem.resid);
                imageView.setVisibility(View.VISIBLE);
            } else {
                ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                        g2u.convertPixelsToDp(imageSizedp, getContext()),
                        g2u.convertPixelsToDp(imageSizedp, getContext()));
                layoutParams.circleConstraint = R.id.pivot;
                layoutParams.circleAngle = 0;
                layoutParams.circleRadius = 0;
                imageView.setLayoutParams(layoutParams);
                imageView.setVisibility(View.GONE);
            }
        }
    }

    private ImageView genImageView(){
        ImageView imageView = new ImageView(getActivity());
        imageViews.add(imageView);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                g2u.convertPixelsToDp(imageSizedp, getContext()),
                g2u.convertPixelsToDp(imageSizedp, getContext()));
        layoutParams.circleConstraint = R.id.pivot;
        layoutParams.circleAngle = 0;
        layoutParams.circleRadius = 0;
        constraintLayout.addView(imageView, layoutParams);
        return imageView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tabArm:{
                SetPage(itemes.get(P_ARM));
            }break;
            case R.id.tabLeg:{
                SetPage(itemes.get(P_LEG));
            }break;
            case R.id.tabBack:{
                SetPage(itemes.get(P_BACK));
            }break;
            case R.id.tabAllBody:{
                SetPage(itemes.get(P_All_BODY));
            }break;
        }
    }

    public static class SelectItem{
        @DrawableRes
        int resid;
        public View.OnClickListener onClickListener;

        public SelectItem(@DrawableRes int resid, View.OnClickListener onClickListener){
            this.resid = resid;
            this.onClickListener = onClickListener;
        }
    }
}
