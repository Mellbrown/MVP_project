package com.techwork.kjc.mvp_project.controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.techwork.kjc.mvp_project.dialog.PracticeMenuDialog;
import com.techwork.kjc.mvp_project.fireSource.Fire_Auth;
import com.techwork.kjc.mvp_project.fragment.FRG1_Splash;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.fragment.FRG3_Login;
import com.techwork.kjc.mvp_project.fragment.FRG4_MenuMain;
import com.techwork.kjc.mvp_project.util.EventChain;
import com.techwork.kjc.mvp_project.util.PhotoProcess;

import java.util.ArrayList;
import java.util.HashMap;

public class StartController extends AppCompatActivity implements FRG1_Splash.Requester, FRG4_MenuMain.Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

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

        checkPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){ // 로그인 안되어 있으면
            rendingFRG1_Splash();
        } else { // 로그인 되어 있으면
            rendingFRG4_MainMenu();
        }
    }

    static final int splash = 1;
    static final int main = 2;
    private int curpage = -1;
    // 시작 스플레시 화면 떳음
    public void rendingFRG1_Splash(){
        if(curpage == splash) return;
        curpage = splash;
        FRG1_Splash frg1_splash = new FRG1_Splash();
        frg1_splash.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg1_splash);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void rendingFRG4_MainMenu(){
        if(curpage == main) return;
        curpage = main;
        FRG4_MenuMain frg4_mainmenu = new FRG4_MenuMain();
        frg4_mainmenu.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerID, frg4_mainmenu);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override // 스플레시 화면에서 다른 화면 이동을 요구
    public void requestLinkTo(FRG1_Splash.REQUEST_LINK request_link) {
        switch (request_link){
            case SIGNIN:{
                startActivity(new Intent(StartController.this, Second_LoginController.class));
            } break;
            case SIGNUP:{
                startActivity(new Intent(StartController.this, Second_RegisterController.class));
            }break;
        }
    }

    // 메뉴 화면이 다른 화면을 이동을 요구
    @Override
    public void MeasureActivityStart() {
        // 측정 컨트롤러로 가라!
        startActivity(new Intent(StartController.this, Third_MeauserController.class));
    }

    @Override
    public void VersusActivityStart() {
        // 대결 컨트롤러로 가라!
        startActivity(new Intent(StartController.this, Third_VersusController.class));
    }

    @Override
    public void RecordActivityStart() {
        // 앙 아직 만들어졌다 띄
        Toast.makeText(StartController.this, "데이터 베이스 조정중입니다...", Toast.LENGTH_SHORT).show();
    }


    @Override // 냥 냥 나중에 수정된 내용!
    public void Logout() {
        FirebaseAuth.getInstance().signOut();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){ // 로그인 안되어 있으면
            rendingFRG1_Splash();
        } else { // 로그인 되어 있으면
            rendingFRG4_MainMenu();
        }
    }

    @Override //메뉴중하나는 다이얼로그 띄워서 세부 메뉴를 씁니다.
    public void PracticeDialogStart() {
        final PracticeMenuDialog practiceMenuDialog[] = new PracticeMenuDialog[1];
        practiceMenuDialog[0] = new PracticeMenuDialog(StartController.this, new PracticeMenuDialog.Requester() {
            @Override
            public void FocusActivityStart() {
                //포커즈 컨트롤러로 가라
                startActivity(new Intent(StartController.this, Third_FocusController.class));
                practiceMenuDialog[0].dismiss();
            }

            @Override
            public void CycleActivityStart() {
                // 순환 컨트롤러로 가라
                startActivity(new Intent(StartController.this, Third_Recursive.class));
                practiceMenuDialog[0].dismiss();
            }
        });
        practiceMenuDialog[0].show();
    }

    /************************여긴 권한 처리하는 코드가 담겨 있어오***************************/
    private static final int PERMISSION_REQ_CAMERA = 189;

    private void ShowNoticeRejectPermissionDialog(){
        new AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
    private void checkPermission(){
        if ( // 필요한 권한 있는지 검사를 해오
                ContextCompat.checkSelfPermission(this, // 이 어플이
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) //외부 저장소 권한을 가지고 있는지 물어봐요
                        != PackageManager.PERMISSION_GRANTED ||// 승인이 안되어 있오요??
                        ContextCompat.checkSelfPermission(this, // 이 어플이
                                Manifest.permission.CAMERA) //외부 저장소 권한을 가지고 있는지 물어봐요
                                != PackageManager.PERMISSION_GRANTED // 승인이 안되어 있오요??
                ) {
            // 오 외부 저장소 승인이 안되어 있다네오
            if ( // 저번에 주인이 절대 안주겠다고 했던가요?
                    (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) || //외부 저장소 접근 권한하고

                            (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                    android.Manifest.permission.CAMERA)) // 카메라 사용 접근 권한하고
                    ) {
                // 오맨, 그랬데요!!, 그러면 쓸꺼면 알아서 설정가서 세팅하라고 합시다.
                ShowNoticeRejectPermissionDialog();
            } else {
                // 그런적은 없다고 하네요. 그럼 지금 달라고 합시다.
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, // 외부 저장소 접근과
                                android.Manifest.permission.CAMERA // 카메라 사용 접근 권한을
                        },
                        PERMISSION_REQ_CAMERA // 그리고 여기로 대답해줘요(신호코드)!! 위에 있어요!!
                );
            }
        }//필요한 권한 다 있다네오!
    }

    //여기다 사용자에게 권한 요청 응답이 여기서 처리를 해요
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            // 우리가 이쪽으로 응답해달라 신호가 왔군요!!
            case PERMISSION_REQ_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] < 0) {
                        Toast.makeText(this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                break;
        }
    }
}
