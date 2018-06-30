package com.techwork.kjc.mvp_project.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG6_Versus;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPhotoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPublicInfoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.VersusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.VesusBean;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Third_VersusController extends AppCompatActivity implements FRG6_Versus.Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    private Map<String, UserPublicInfoBean> userPublicInfoBeanMap;
    private HashMap<String, Uri> photoResourceMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        containerID = getResources().getIdentifier("container","id",getPackageName());
        frameLayout = new FrameLayout(this);
        frameLayout.setId(containerID);
        frameLayout.setLayoutParams(
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );
        setContentView(frameLayout);
        fragmentManager = getSupportFragmentManager();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("친구목록 불러오는 중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final EventChain eventChainUserInfo = new EventChain();
        eventChainUserInfo.ready("목록");
        UserPublicInfoDAO.selectAllUser(new UserPublicInfoDAO.OnSelectedLisnter() {
            @Override
            public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                if(success){
                    Third_VersusController.this.userPublicInfoBeanMap = userPublicInfoBeanMap;
                    eventChainUserInfo.complete("목록");
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(Third_VersusController.this, "친구 목록을 불러올수 없습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Third_VersusController", databaseError.getMessage());
                    finish();
                }
            }
        });

        eventChainUserInfo.andthen(()->{
            Set<String> photoIdes = new HashSet<>();
            for( UserPublicInfoBean userPublicInfoBean : userPublicInfoBeanMap.values())
                photoIdes.add(userPublicInfoBean.photoID);

            UserPhotoDAO.selectMultiPhotoesByIdes(photoIdes, new UserPhotoDAO.OnMultiDownloadComplete() {
                @Override
                public void OnMultiDownloadComplete(HashMap<String, Uri> photoResourceMap) {
                    progressDialog.dismiss();
                    Third_VersusController.this.photoResourceMap = photoResourceMap;
                    renderingFRG6_Versus();
                }
            });
        }, "목록");

    }

    void renderingFRG6_Versus(){
        FRG6_Versus frg6_versus = new FRG6_Versus();
        frg6_versus.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg6_versus,"frg6_versus");
        fragmentTransaction.commit();
    }

    @Override //화면에서 초기화하면서 바로 부를껍니다. 데이터가 바로 준비되어 있어야해여
    public FRG6_Versus.SimProfile reuqestYouProfile() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserPublicInfoBean you = userPublicInfoBeanMap.remove(uid);
        Uri uri = photoResourceMap.get(you.photoID);
        Bitmap bitmap = null;
        try { bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri); }
        catch (IOException e) { e.printStackTrace(); }
        return new AttachID(bitmap, you.name, uid);
    }

    @Override // 이것도 초기화 타이밍에 바로 부릅니다. 데이터가 미리 준비되어 있어야 해여
    public ArrayList<FRG6_Versus.SimProfile> requestRivalesProfiles() {
        //onCreate에서 분명히 값이 다 준비되어 있을거라고 가정하고 넘기닌까 여기서 null이면 안되여
        ArrayList<FRG6_Versus.SimProfile> simProfiles = new ArrayList<>();

        for( String uid : userPublicInfoBeanMap.keySet()){
            UserPublicInfoBean rival = userPublicInfoBeanMap.get(uid);
            Uri uri = photoResourceMap.get(rival.photoID);
            Bitmap bitmap = null;
            try { bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri); }
            catch (IOException e) { e.printStackTrace(); }
            simProfiles.add(new AttachID(bitmap, rival.name, uid));
        }

        return simProfiles;
    }



    @Override // 닫아 달라는 뤼퀘
    public void reuqestClose() {
        finish(); // ㅇㅇ 닫아줘야지 닫아 달라면 근데 보닌까 잘 필요 없지 않을까 예상도 됨
    }

    @Override
    public void whoWinner(FRG6_Versus.SimProfile you, FRG6_Versus.SimProfile rival, int youTime, int youLevel, int rivalTime, int rivalLevel) {

        if(!(you instanceof AttachID)) Log.e("Third_VersusController", "you 캐스팅 불가");
        if(!(rival instanceof AttachID)) Log.e("Third_VersusController", "rival 캐스팅 불가");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("판정중....");
        progressDialog.setCancelable(false);
        progressDialog.show();
        AttachID castYou = (AttachID) you;
        AttachID castRival = (AttachID) rival;
        VesusBean vesusBean = new VesusBean();
        vesusBean.winner = youLevel > rivalLevel ? castYou.uid : castRival.uid;
        vesusBean.rival_level = (long)rivalLevel;
        vesusBean.rival_time = (long)rivalTime;
        vesusBean.you_level = (long)youLevel;
        vesusBean.you_time = (long)youTime;
        vesusBean.timestamp = new Date().getTime();
        vesusBean.you_uid = castYou.uid;
        vesusBean.rival_uid = castRival.uid;
        VersusDAO.addVersusBean(castYou.uid, castRival.uid, vesusBean, new VersusDAO.OnComplete() {
            @Override
            public void onComlete() {
                progressDialog.dismiss();
                ((FRG6_Versus) fragmentManager.findFragmentByTag("frg6_versus"))
                    .responseWhoWinner(youLevel > rivalLevel); // 당신이 이겻다 코드
            }
        });


    }

    public static class AttachID extends FRG6_Versus.SimProfile{
        public String uid;
        public AttachID(Bitmap photo, String name, String uid) {
            super(photo, name);
            this.uid = uid;
        }
    }
}
