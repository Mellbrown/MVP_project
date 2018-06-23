package com.techwork.kjc.mvp_project.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.techwork.kjc.mvp_project.fireSource.Fire_Auth;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.util.PhotoProcess;

import java.util.HashMap;

public class Second_RegisterController extends AppCompatActivity implements FRG2_Register.Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;
    public static StartController sct;

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

        //이 컨트롤러는 레지스터 담당만 합니다. 렌더링 될것인 이 것 뿐입니다.
        rendingFRG2_Register();
    }

    public void setParent(Activity act){
        sct = (StartController)act;
    }

    // 레지스터 화면 렌더링
    public void rendingFRG2_Register(){
        FRG2_Register frg2_register = new FRG2_Register();
        frg2_register.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID,frg2_register,"frg2_register");
        fragmentTransaction.commit();
    }

    @Override //레지스터 화면에서 어떻게든 이미지 줄것을 요구합니다.
    public void requestImagePath() {
        // 이미지를 사용자에게 받아내기 위해 포토프로세서를 소환해냅니다. (1)로 가시오.
        startActivityForResult(new Intent(Second_RegisterController.this, PhotoProcess.class),0);
    }
    @Override // (1)입니다. 포토프로세서가 다 일하면 여기로 답합니다.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == 0){
            String imagePath = data.getStringExtra(PhotoProcess.RES_IMAGE_PATH);
            FRG2_Register frg2_register = ((FRG2_Register) fragmentManager.findFragmentByTag("frg2_register"));

            //여기까지 오면 여기에 이미지가 있습니다.
            //파베에 이미지 업로드 할꺼면 올리시등가 ㅎㅎ

            // 뭥 일단 레지스터 화면에 받아온 이미지 드립니다.
            frg2_register.responseImagePath(imagePath);
        }
    }

    @Override // 이것은 레지스터 화면에서 드디어 사용자가 모든 폼을 입력했다고 연락이 왔네요
    public void requestSignup(
            String act2_id,
            String act2_pw,
            String act2_name,
            String act2_sex,
            String act2_school,
            String act2_grade,
            String act2_cls,
            String act2_num,
            String act2_tall,
            String act2_weight,
            ImageView act2_iv
    ) {
        //뭐 여기 사용자가 입력한 모든 데이터가 있어요, 지찬군이 이미지 뷰로까지 넘겨줬네여
        //이 타이밍에 파베에 업로드 하면 되겠군여
        HashMap<String,String> info = new HashMap<>();
        info.put("id",act2_id);
        info.put("pw",act2_pw);
        info.put("name",act2_name);
        info.put("sex",act2_sex);
        info.put("school",act2_school);
        info.put("grade",act2_grade);
        info.put("cls",act2_cls);
        info.put("num",act2_num);
        info.put("tall",act2_tall);
        info.put("weight",act2_weight);

        new Fire_Auth().createUserAuth(sct, act2_iv,info);
        finish();
    }

}
