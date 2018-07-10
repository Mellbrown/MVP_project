package com.techwork.kjc.mvp_project.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.techwork.kjc.mvp_project.dialog.InputMeasureRecordDialog;
import com.techwork.kjc.mvp_project.dialog.InputMeasureRecordDialog2;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.dialog.ShowPreScriptionDialog;
import com.techwork.kjc.mvp_project.fireSource.Fire_GOS;
import com.techwork.kjc.mvp_project.fragment.FRG5_Measure;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.MeasureDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPhotoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPublicInfoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.MeasureBean;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;
import com.techwork.kjc.mvp_project.util.EventChain;
import com.techwork.kjc.mvp_project.util.Standard;
import com.techwork.kjc.mvp_project.util.Strandard2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Third_MeauserController extends AppCompatActivity implements FRG5_Measure.Requester {

    FragmentManager fragmentManager;
    FrameLayout frameLayout;
    int containerID;

    UserPublicInfoBean userPublicInfoBean = null;

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

        rendingFRG5_Measure(); //  빠른 렌더링 긔긔
    }

    // 역시 렌더링 코드 지렸다 그져?
    void rendingFRG5_Measure(){
        FRG5_Measure frg5_measure = new FRG5_Measure();
        frg5_measure.requester = this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(containerID, frg5_measure,"frg5_measure");
        fragmentTransaction.commit();
    }


    @Override //화면에서 전체 목록 달라는 요구 들어왔다 그졍?
    public void requestMeasureItemBeans() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 로드중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MeasureDAO.selectMeasureBeanseByUID(uid, new MeasureDAO.OnSelectedMeasureBeans() {
            @Override
            public void onSelectedMeasureBeans(boolean success, List<MeasureBean> measureBeans, DatabaseError databaseError) {
                progressDialog.dismiss();
                if(success){
                    ArrayList<FRG5_Measure.MeasureItemBean> measureItemBeans = new ArrayList<>();
                    for (MeasureBean measureBean : measureBeans){
                        FRG5_Measure.MeasureItemBean measureItemBean = new FRG5_Measure.MeasureItemBean();
                        measureItemBean.timestamp = measureBean.timestamp;
                        measureItemBean.armWeight = measureBean.arm;
                        measureItemBean.legWeight = measureBean.leg;
                        measureItemBean.backWeight = measureBean.back;
                        measureItemBean.allBodyWeight = measureBean.body;
                        measureItemBeans.add(measureItemBean);
                    }
                    getFragmentInstance().responseMeasureItemBeans(measureItemBeans);
                } else {
                    Toast.makeText(Third_MeauserController.this, "친구 목록을 불러올수 없습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Third_MeauserController", databaseError.getMessage());
                }
            }
        });
    }

    @Override // 화면에서 사용자가 + 버튼 눌럿데여! (저긴 버튼 클릭 이벤트 발생 외엔 아무것도 않함;;
    public void requestAddMeasureItem() {
        new InputMeasureRecordDialog2(Third_MeauserController.this,  measureItemBean -> {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            MeasureBean measureBean = new MeasureBean();
            measureBean.timestamp = measureItemBean.timestamp;
            measureBean.arm = measureItemBean.armWeight;
            measureBean.leg = measureItemBean.legWeight;
            measureBean.back = measureItemBean.backWeight;
            measureBean.body = measureItemBean.allBodyWeight;
            MeasureDAO.addMeasureBeanWithUID(uid,measureBean).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        getFragmentInstance().responseAddMeasureItem(measureItemBean);
                    } else {
                        Toast.makeText(Third_MeauserController.this, "서버에 데이터를 전달하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("Third_MeauserController", task.getException().getMessage());
                    }
                }
            });
        }).show();
    }

    @Override //  화면에서 사용자가 이 아이템 대해서 처방전 보여 달랬어여!
    public void requestShowPrescription(FRG5_Measure.MeasureItemBean measureItemBean) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("평가중...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        EventChain eventChain = new EventChain();
        eventChain.ready("사용자 데이터");
        if(userPublicInfoBean == null){
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            UserPublicInfoDAO.selectUserByUID(new ArrayList<String>() {{
                add(uid);
            }}, new UserPublicInfoDAO.OnSelectedLisnter() {
                @Override
                public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                    if(success && userPublicInfoBeanMap.size() == 1){
                        Third_MeauserController.this.userPublicInfoBean = userPublicInfoBeanMap.get(uid);
                        eventChain.complete("사용자 데이터");
                    } else {
                        Toast.makeText(Third_MeauserController.this, "데이터를 조회하는데 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.e("Third_MeauserController", databaseError.getMessage());
                        progressDialog.dismiss();
                    }
                }
            });
        } else eventChain.complete("사용자 데이터");

        eventChain.andthen(()->{
            int grade = Integer.valueOf(userPublicInfoBean.grade + "");
            String gener = userPublicInfoBean.gener;

            String evalArm = Strandard2.evaluation(new Strandard2.EvaluePart(grade, gener, Strandard2.ARM), measureItemBean.armWeight);
            String evalLeg = Strandard2.evaluation(new Strandard2.EvaluePart(grade, gener, Strandard2.LEG), measureItemBean.legWeight);
            String evalBack = Strandard2.evaluation(new Strandard2.EvaluePart(grade, gener, Strandard2.BACK), measureItemBean.backWeight);
            String evalBody = Strandard2.evaluation(new Strandard2.EvaluePart(grade, gener, Strandard2.BODY), measureItemBean.allBodyWeight);

            ArrayList<String> allEval = new ArrayList<String>(){{add(evalArm); add(evalLeg); add(evalBack); add(evalBody);}};

            int topcount = 0; for(String s : allEval) if(s.equals(Strandard2.TOP)) topcount ++;
            int botcount = 0; for (String s : allEval) if(s.equals(Strandard2.BOT) || s.equals(Strandard2.ELSE)) botcount ++;

            progressDialog.dismiss();
            new ShowPreScriptionDialog(Third_MeauserController.this,
                    new ShowPreScriptionDialog.Prescription(
                        topcount>3 ? Strandard2.TOP3 : (botcount > 3 ? Strandard2.BOT3 : Strandard2.ELSE3),
                        generate(evalArm), generate(evalLeg),generate(evalBack), generate(evalBody)
                    )
            ).show();

        },"사용자 데이터");
    }

    private ShowPreScriptionDialog.EachPrescription generate(String eachEval){
        return new ShowPreScriptionDialog.EachPrescription(
                eachEval.equals(Strandard2.TOP)? ShowPreScriptionDialog.EachPrescription.TOP_CLASS :
                        (
                            eachEval.equals(Strandard2.MID)? ShowPreScriptionDialog.EachPrescription.MIDDLE_CLASS :
                                    ShowPreScriptionDialog.EachPrescription.BOTTOM_CLASS
                        ),

                eachEval.equals(Strandard2.TOP)? Strandard2.EachGood :
                        (
                            eachEval.equals(Strandard2.MID)? Strandard2.EachNormal:
                                    Strandard2.EachBad
                        )
        );
    }

    @Override // 화면에서 사용자가 이 아이템 대해서 지워달라고 요청 했어요!
    public void requestRemoveThisMeasureItem(FRG5_Measure.MeasureItemBean measureItemBean) {
        //화면에서 어떤것만 지울지 선택했지 아무것도 안됬어여
        //여기서 빠베에 지우는 코드 실행시키고 역시 성공하면 응답해주고, 못하면 재까세야
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        MeasureBean measureBean = new MeasureBean();
        measureBean.timestamp = measureItemBean.timestamp;
        measureBean.arm = measureItemBean.armWeight;
        measureBean.leg = measureItemBean.legWeight;
        measureBean.back = measureItemBean.backWeight;
        measureBean.body = measureItemBean.allBodyWeight;
        MeasureDAO.removeMeasureBeanWithUID(uid, measureBean).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    getFragmentInstance().responseRemoveSuccessMeasureItem(measureItemBean);
                } else {
                    Toast.makeText(Third_MeauserController.this, "서버에 데이터를 삭제하지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("Third_MeauserController", task.getException().getMessage());
                }
            }
        });
    }

    // 현재 프래그먼트 코드 타이핑 치기 귀찮아서 만들었어여
    private  FRG5_Measure getFragmentInstance(){
        return ((FRG5_Measure) fragmentManager.findFragmentByTag("frg5_measure"));
    }
}
