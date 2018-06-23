package com.techwork.kjc.mvp_project.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG6_Versus;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPhotoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPublicInfoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.VersusDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Third_VersusController extends AppCompatActivity implements FRG6_Versus.Requester {


    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    private Map<String, UserPublicInfoBean> userPublicInfoBeanMap;
    private UserPublicInfoBean userPublicInfoBean;

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
        UserPublicInfoDAO.selectAllUser(new UserPublicInfoDAO.OnSelectedLisnter() {
            @Override
            public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                if(success){
                    Third_VersusController.this.userPublicInfoBean = userPublicInfoBeanMap.remove(uid);
                    Third_VersusController.this.userPublicInfoBeanMap = userPublicInfoBeanMap;
                    renderingFRG6_Versus();
                } else {
                    Toast.makeText(Third_VersusController.this, "친구 목록을 불러올수 없습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Third_VersusController", databaseError.getMessage());
                    finish();
                }
            }
        });

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
        //onCreate에서 분명히 값이 다 준비되어 있을거라고 가정하고 넘기닌까 여기서 null이면 안되여
        if(userPublicInfoBean == null){
        }
            Log.e("Third_VersusController", "저기요? 아직 readyForyouProfile 준비 안되었여");
        return null;
    }

    @Override // 이것도 초기화 타이밍에 바로 부릅니다. 데이터가 미리 준비되어 있어야 해여
    public List<FRG6_Versus.SimProfile> requestRivalesProfiles() {
        //onCreate에서 분명히 값이 다 준비되어 있을거라고 가정하고 넘기닌까 여기서 null이면 안되여
        if(this.userPublicInfoBeanMap == null){

        }
            Log.e("Third_VersusController", "저기요? 아직 readyForRirvalProfiles 준비 안되었여");
        return null;
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
}
