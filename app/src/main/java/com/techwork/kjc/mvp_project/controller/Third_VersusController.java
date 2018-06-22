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

import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG6_Versus;

import java.util.ArrayList;
import java.util.List;

public class Third_VersusController extends AppCompatActivity implements FRG6_Versus.Requester {


    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    private FRG6_Versus.SimProfile readyForyouProfile;
    private ArrayList<FRG6_Versus.SimProfile> readyForRirvalProfiles;

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

        ProgressDialog progressDialog = new ProgressDialog(this).setTitle("로딩");

        Bitmap youPhoto = BitmapFactory.decodeResource(getResources(),R.drawable.nuburi);
        readyForyouProfile = new FRG6_Versus.SimProfile(youPhoto, "지찬군");


        Bitmap rivalPhoto = BitmapFactory.decodeResource(getResources(), R.drawable.bonobono);
        readyForRirvalProfiles = new ArrayList<>();
        for(int i = 0 ; 30 > i ; i++){
            readyForRirvalProfiles.add(new FRG6_Versus.SimProfile(rivalPhoto,"덕구 센세(" + i + ")" ));
        }

        //데이터 준비되면 그때 렌더링 돌려 주세여
        renderingFRG6_Versus();
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
        if(readyForyouProfile == null)
            Log.e("Third_VersusController", "저기요? 아직 readyForyouProfile 준비 안되었여");
        return readyForyouProfile;
    }

    @Override // 이것도 초기화 타이밍에 바로 부릅니다. 데이터가 미리 준비되어 있어야 해여
    public List<FRG6_Versus.SimProfile> requestRivalesProfiles() {
        //onCreate에서 분명히 값이 다 준비되어 있을거라고 가정하고 넘기닌까 여기서 null이면 안되여
        if(readyForRirvalProfiles == null)
            Log.e("Third_VersusController", "저기요? 아직 readyForRirvalProfiles 준비 안되었여");
        return readyForRirvalProfiles;
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
