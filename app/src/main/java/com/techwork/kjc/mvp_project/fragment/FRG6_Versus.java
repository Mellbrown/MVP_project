package com.techwork.kjc.mvp_project.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.subview.SubFRG6_SelectRival;
import com.techwork.kjc.mvp_project.subview.SubFRG6_ShowVersus;
import com.techwork.kjc.mvp_project.util.g2u;

import java.util.ArrayList;
import java.util.List;

public class FRG6_Versus extends Fragment {

    private View viewLayout;
    private FrameLayout frame;

    private SubFRG6_SelectRival subFRG6_selectRival;
    private SubFRG6_ShowVersus subFRG6_showVersus;

    public Requester requester;

    SimProfile youProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act6_versus, container,false);

        frame = viewLayout.findViewById(R.id.frame);

        if(requester == null)
            Log.e("FRG6_Versus", "리퀘스트 객체를 받지 못하였습니다");

        youProfile = requester.reuqestYouProfile();

        return viewLayout;
    }


    private void ShowSubFRG6_selectRival(){
        frame.removeAllViews();
        subFRG6_selectRival = new SubFRG6_SelectRival(getContext(), youProfile.photo, youProfile.name, new SubFRG6_SelectRival.Requester() {
            @Override
            public List<SubFRG6_SelectRival.RirvalItem> requestItems() {
                ArrayList<SubFRG6_SelectRival.RirvalItem> rirvalItems = new ArrayList<>();
                List<SimProfile> simProfiles = requester.requestRivalesProfiles();
                for( SimProfile profile : simProfiles){
                    rirvalItems.add(new SubFRG6_SelectRival.RirvalItem(profile.photo, profile.name));
                }
                return rirvalItems;
            }

            @Override
            public void selectedItem(SubFRG6_SelectRival.RirvalItem rirvalItem) {
                ShowSubFRG6_showVersus(rirvalItem);
            }
        });
        frame.addView(subFRG6_selectRival,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void ShowSubFRG6_showVersus(SubFRG6_SelectRival.RirvalItem rirvalItem){
        frame.removeAllViews();
        subFRG6_showVersus = new SubFRG6_ShowVersus(getContext(), new SubFRG6_ShowVersus.Requester() {
            @Override
            public SubFRG6_ShowVersus.TwoManInfo requestTwoManInfo() {
                return new SubFRG6_ShowVersus.TwoManInfo( youProfile.photo, youProfile.name, rirvalItem.photo, rirvalItem.name);
            }

            @Override
            public void requestCancle() {
                ShowSubFRG6_selectRival();
            }

            @Override
            public void requestConfirm() {
                Toast.makeText(getContext(), "계산중...", Toast.LENGTH_SHORT).show();
                subFRG6_showVersus.responseResult(requester.whoWinner(youProfile,new SimProfile(rirvalItem.photo, rirvalItem.name)));
            }

            @Override
            public void requestEndup() {
                requester.reuqestClose();
            }
        });

        frame.addView(subFRG6_showVersus,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public interface Requester{
        SimProfile reuqestYouProfile();
        List<SimProfile> requestRivalesProfiles();
        void reuqestClose();
        boolean whoWinner(SimProfile you, SimProfile rival);
    }

    public static class SimProfile{
        public Bitmap photo;
        public String name;


        public SimProfile(Bitmap photo, String name){
            this.photo = photo;
            this.name = name;
        }
    }
}
