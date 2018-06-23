package com.techwork.kjc.mvp_project.fragment;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.util.FRG7_Requester;
import com.techwork.kjc.mvp_project.util.g2u;

public class FRG7_Arm extends android.support.v4.app.Fragment {
   private View viewLayout;
   private ImageView act7_first;
    private ImageView act7_second;
    private ImageView act7_third;
    private ImageView act7_fource;
    public FRG7_Requester requester;
    public g2u.PatchRouterFRG7 tabPatch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act7_sel, container,false);

        act7_first = viewLayout.findViewById(R.id.act7_first);
                act7_second = viewLayout.findViewById(R.id.act7_second);
        act7_third = viewLayout.findViewById(R.id.act7_third);
                act7_fource = viewLayout.findViewById(R.id.act7_fource);

        GlideDrawableImageViewTarget first = new GlideDrawableImageViewTarget(act7_first);
        GlideDrawableImageViewTarget second = new GlideDrawableImageViewTarget(act7_second);
        GlideDrawableImageViewTarget third = new GlideDrawableImageViewTarget(act7_third);
        GlideDrawableImageViewTarget fource = new GlideDrawableImageViewTarget(act7_fource);

        Glide.with(this).load(R.raw.arm1).into(first);
        Glide.with(this).load(R.raw.arm2).into(second);
        Glide.with(this).load(R.raw.arm3).into(third);
        Glide.with(this).load(R.raw.arm4).into(fource);

        act7_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("팔1");
            }
        });

        act7_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("팔2");
            }
        });
        act7_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("팔3");
            }
        });
        act7_fource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("팔4");
            }
        });

        g2u.PatchEventAttacherFRG7(viewLayout, tabPatch);
        return viewLayout;
    }
}
