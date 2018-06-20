package com.techwork.kjc.mvp_project.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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

    private Bitmap test1;
    private Bitmap test2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewLayout = inflater.inflate(R.layout.act6_versus, container,false);

        frame = viewLayout.findViewById(R.id.frame);

        test1 = BitmapFactory.decodeResource(getResources(), R.drawable.nuburi);
        test2 =BitmapFactory.decodeResource(getResources(), R.drawable.bonobono);

        ShowSubFRG6_selectRival();

        return viewLayout;
    }


    private void ShowSubFRG6_selectRival(){
        frame.removeAllViews();
        subFRG6_selectRival = new SubFRG6_SelectRival(getContext(), test1, "지찬군", new SubFRG6_SelectRival.Requester() {
            @Override
            public List<SubFRG6_SelectRival.RirvalItem> requestItems() {
                ArrayList<SubFRG6_SelectRival.RirvalItem> rirvalItems = new ArrayList<>();
                for(int i = 0; 20 > i; i ++){
                    SubFRG6_SelectRival.RirvalItem rirvalItem = new SubFRG6_SelectRival.RirvalItem();
                    rirvalItem.photo = test2;
                    rirvalItem.name = "덕구 센세 (" + i + ")";
                    rirvalItems.add(rirvalItem);
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
                return new SubFRG6_ShowVersus.TwoManInfo( test1, "지찬군", test2, rirvalItem.name);
            }

            @Override
            public void requestCancle() {
                ShowSubFRG6_selectRival();
            }

            @Override
            public void requestConfirm() {
                Toast.makeText(getContext(), "계산중...", Toast.LENGTH_SHORT).show();
                subFRG6_showVersus.responseResult((int)g2u.rand(0,2) == 0);
            }

            @Override
            public void requestEndup() {
                ShowSubFRG6_selectRival();
            }
        });

        frame.addView(subFRG6_showVersus,new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
