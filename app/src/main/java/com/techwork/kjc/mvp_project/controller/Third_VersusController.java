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
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.io.IOException;
import java.util.ArrayList;
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

    @Override //누가 이겼는지 물어보군여
    public void whoWinner(FRG6_Versus.SimProfile you, FRG6_Versus.SimProfile rival) {
        // 일단 구분 기준을 딱히 알려 드리진 않아여 제공 받은 객체만 도로 넘겨 줄뿐이져
        // 그래서 뭐 가능하면 돌려드리는 기본 클래스에서 구분이 가능하면 그냥 그래 써도 될것 같지만
        // 여기서 구분을 위해 클래스 상속해서 구분할 기준 확장해도 충분히 구분할 방법 있어여 그쵸?

        // 글고 이제 보닌까 승패 판정할때 빠베 접근 안하고 스탠다드로 판정내릴수 있을 것 같긴한데
        // 일단 비동기로 대답 해줄 수 있어여 아래는 비동기 대답 코드
        ((FRG6_Versus) fragmentManager.findFragmentByTag("frg6_versus"))
                .responseWhoWinner(true); // 당신이 이겻다 코드
    }

    @Override // 닫아 달라는 뤼퀘
    public void reuqestClose() {
        finish(); // ㅇㅇ 닫아줘야지 닫아 달라면 근데 보닌까 잘 필요 없지 않을까 예상도 됨
    }

    public static class AttachID extends FRG6_Versus.SimProfile{
        public String uid;
        public AttachID(Bitmap photo, String name, String uid) {
            super(photo, name);
            this.uid = uid;
        }
    }
}
