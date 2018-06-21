package com.techwork.kjc.mvp_project.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.techwork.kjc.mvp_project.R;

public class FRG7_aBody extends android.support.v4.app.Fragment {
   private View viewLayout;
   private ImageView act7_first;
    private ImageView act7_second;
    private ImageView act7_third;
    private ImageView act7_fource;
    public Requester requester;
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

        Glide.with(this).load(R.raw.abody1).into(first);
        Glide.with(this).load(R.raw.abody2).into(second);
        Glide.with(this).load(R.raw.abody3).into(third);
        Glide.with(this).load(R.raw.abody4).into(fource);

        act7_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("abody1");
            }
        });

        act7_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("abody2");
            }
        });
        act7_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("abody3");
            }
        });
        act7_fource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requester.TrackActivityStart("abody4");
            }
        });

        return viewLayout;
    }
    public interface Requester{
        void TrackActivityStart(String part);
    }
}
