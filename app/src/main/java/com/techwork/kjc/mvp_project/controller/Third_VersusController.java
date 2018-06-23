package com.techwork.kjc.mvp_project.controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fireSource.Fire_GOS;
import com.techwork.kjc.mvp_project.fireSource.fireclass.UserFire;
import com.techwork.kjc.mvp_project.fragment.FRG6_Versus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Third_VersusController extends AppCompatActivity implements FRG6_Versus.Requester {


    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    private HashMap<UserFire,Bitmap> myIF;
    private HashMap<UserFire,Bitmap> userArray;

    Fire_GOS ng;

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

        //데이터 준비되면 그때 렌더링 돌려 주세여
        ng = new Fire_GOS();
        ng.getFVUser(this);

    }

    public void renderingFRG6_Versus(HashMap<UserFire,Bitmap> myInfo, HashMap<UserFire,Bitmap> userArray){
        this.myIF = myInfo;
        this.userArray = userArray;
        FRG6_Versus frg6_versus = new FRG6_Versus();
        frg6_versus.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg6_versus,"frg6_versus");
        fragmentTransaction.commit();
    }

    @Override //화면에서 초기화하면서 바로 부를껍니다. 데이터가 바로 준비되어 있어야해여
    public armuguna reuqestYouProfile() {
        //onCreate에서 분명히 값이 다 준비되어 있을거라고 가정하고 넘기닌까 여기서 null이면 안되여
//        return new FRG6_Versus.SimProfile("",myInfo.name);
        return new armuguna(myIF.values().iterator().next(),myIF.keySet().iterator().next().name,myIF.keySet().iterator().next());
    }

    @Override
    public ArrayList<FRG6_Versus.SimProfile> requestRivalesProfiles() {
        ArrayList<FRG6_Versus.SimProfile> rres = new ArrayList<>();
        UserFire tmp;
        //onCreate에서 분명히 값이 다 준비되어 있을거라고 가정하고 넘기닌까 여기서 null이면 안되여
        Iterator iter = userArray.keySet().iterator();
        Iterator iter2 = userArray.values().iterator();
        while(iter.hasNext()&&iter2.hasNext()){
            tmp = (UserFire) iter.next();
            rres.add(new armuguna((Bitmap) iter2.next(),tmp.name,tmp));
        }
        return rres;
    }

    @Override //누가 이겼는지 물어보군여
    public void whoWinner(FRG6_Versus.SimProfile you, FRG6_Versus.SimProfile rival) {
        // 일단 구분 기준을 딱히 알려 드리진 않아여 제공 받은 객체만 도로 넘겨 줄뿐이져
        // 그래서 뭐 가능하면 돌려드리는 기본 클래스에서 구분이 가능하면 그냥 그래 써도 될것 같지만
        // 여기서 구분을 위해 클래스 상속해서 구분할 기준 확장해도 충분히 구분할 방법 있어여 그쵸?
        armuguna you1 = (armuguna) you;
        armuguna rival1 = (armuguna) rival;
        // 글고 이제 보닌까 승패 판정할때 빠베 접근 안하고 스탠다드로 판정내릴수 있을 것 같긴한데
        // 일단 비동기로 대답 해줄 수 있어여 아래는 비동기 대답 코드
        ng.setFV(you1.user.uid,rival1.user.uid);
        ((FRG6_Versus) fragmentManager.findFragmentByTag("frg6_versus"))
                .responseWhoWinner(true); // 당신이 이겻다 코드
    }

    @Override // 닫아 달라는 뤼퀘
    public void reuqestClose() {
        finish(); // ㅇㅇ 닫아줘야지 닫아 달라면 근데 보닌까 잘 필요 없지 않을까 예상도 됨
    }

    public static class armuguna extends FRG6_Versus.SimProfile{
        public UserFire user ;
        public armuguna(Bitmap photo, String name, UserFire userFuck) {
            super(photo, name);
            this.user = user;
        }
    }
}
