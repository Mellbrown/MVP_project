package com.techwork.kjc.mvp_project.subview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

public class SubFRG6_ShowVersus extends FrameLayout {
    private View viewLayout;

    private ImageView imgRivalPhoto;
    private TextView txtRivalName;

    private ImageView imgYouPhoto;
    private TextView txtYouName;

    private FrameLayout con_you_down;
    private FrameLayout con_you_up;
    private FrameLayout con_rival_up;
    private FrameLayout con_rival_down;

    private ImageView btnRejection;
    private ImageView img_you_result;
    private ImageView img_rival_result;

    public int youTime = 0;
    public int youStren = 0;
    public int rivalTime = 0;
    public int rivalStren = 0;

    public int getYouTime() { return youTime; }
    public int getYouStren() { return youStren; }
    public int getRivalStren() { return rivalStren; }
    public int getRivalTime() { return rivalTime; }

    private FloatingActionButton btnCancle;
    private FloatingActionButton btnConfirm;

    private Requester requester;

    public SubFRG6_ShowVersus(@NonNull Context context, Requester requester) {
        super(context);

        this.requester = requester;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewLayout = inflater.inflate(R.layout.subview_act6_show_versus, this, false);
        addView(viewLayout);

        imgRivalPhoto = viewLayout.findViewById(R.id.imgRivalPhoto);
        txtRivalName = viewLayout.findViewById(R.id.txtRivalName);

        imgYouPhoto = viewLayout.findViewById(R.id.imgYouPhoto);
        txtYouName = viewLayout.findViewById(R.id.txtYouName);

        con_you_down = viewLayout.findViewById(R.id.con_you_down);
        con_you_up = viewLayout.findViewById(R.id.con_you_up);
        con_rival_up = viewLayout.findViewById(R.id.con_rival_up);
        con_rival_down = viewLayout.findViewById(R.id.con_rival_down);

        btnCancle = viewLayout.findViewById(R.id.btnCancle);
        btnConfirm = viewLayout.findViewById(R.id.btnConfirm);

        btnRejection = viewLayout.findViewById(R.id.btnRejection);
        img_you_result = viewLayout.findViewById(R.id.img_you_result);
        img_rival_result = viewLayout.findViewById(R.id.img_rival_result);


        btnConfirm.setOnClickListener((View v)->requester.requestConfirm(youTime, youStren, rivalTime, rivalStren));
        btnCancle.setOnClickListener((View v)->requester.requestCancle());

        con_you_up.addView(new CusInputUpdownCounter(context,"시간","분", 0,(int v)->youTime=v));
        con_you_down.addView(new CusInputUpdownCounter(context,"강도","", 0,(int v)->youStren=v));
        con_rival_up.addView(new CusInputUpdownCounter(context,"시간","분", 0,(int v)->rivalTime=v));
        con_rival_down.addView(new CusInputUpdownCounter(context,"강도","", 0, (int v)->rivalStren=v));

        TwoManInfo twoManInfo = requester.requestTwoManInfo();
        if(twoManInfo.youphoto != null) imgYouPhoto.setImageBitmap(twoManInfo.youphoto);
        txtYouName.setText(twoManInfo.youname);
        if (twoManInfo.rivalphoto != null) imgRivalPhoto.setImageBitmap(twoManInfo.rivalphoto);
        txtRivalName.setText(twoManInfo.rivalname);
    }

    public static class TwoManInfo{
        public Bitmap youphoto;
        public String youname;
        public Bitmap rivalphoto;
        public String rivalname;

        public TwoManInfo(Bitmap youphoto, String youname,Bitmap rivalphoto, String rivalname){
            this.youphoto = youphoto;
            this.youname = youname;
            this.rivalphoto = rivalphoto;
            this.rivalname = rivalname;
        }
    }

    public void responseResult(boolean isYouWinner){
        if(isYouWinner){
            img_you_result.setImageResource(R.drawable.winner);
            img_rival_result.setImageResource(R.drawable.loser);
        }
        else {
            img_you_result.setImageResource(R.drawable.loser);
            img_rival_result.setImageResource(R.drawable.winner);
        }

        btnCancle.setVisibility(GONE);
        btnConfirm.setVisibility(GONE);
        btnRejection.setOnClickListener((View v)->{
            requester.requestEndup();
        });

        btnRejection.setVisibility(VISIBLE);
        img_you_result.setVisibility(VISIBLE);
        img_rival_result.setVisibility(VISIBLE);
    }

    public interface Requester{
        TwoManInfo requestTwoManInfo();
        void requestCancle();
        void requestConfirm(int youTime, int youLevel, int rivalTime, int rivalLevel);
        void requestEndup();
    }
}
