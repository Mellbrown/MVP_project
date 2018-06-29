package com.techwork.kjc.mvp_project.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.techwork.kjc.mvp_project.R;
import com.techwork.kjc.mvp_project.dialog.ProgressDialog;
import com.techwork.kjc.mvp_project.fragment.FRG2_Register;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.LocalPhotoCache;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPhotoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.UserPublicInfoDAO;
import com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse.UserPublicInfoBean;
import com.techwork.kjc.mvp_project.util.EventChain;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class StartEditController extends AppCompatActivity {

    private EditText act2_id;
    private EditText act2_pw;
    private EditText act2_name;
    private Spinner act2_sex;
    private EditText act2_school;
    private Spinner act2_grade;
    private Spinner act2_cls;
    private Spinner act2_num;
    private EditText act2_tall;
    private EditText act2_weight;
    private ImageView act2_image;
    private Button act2_Rbtn;
    
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2__register);

        act2_id = findViewById(R.id.act2_id);
        act2_id.setVisibility(View.GONE);
        act2_pw = findViewById(R.id.act2_pw);
        act2_pw.setVisibility(View.GONE);
        act2_name = findViewById(R.id.act2_name);

        act2_sex = findViewById(R.id.act2_sex);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.act2_sex, android.R.layout.simple_spinner_item);
        act2_sex.setAdapter(adapter1);
        act2_sex.setSelection(0);

        act2_school = findViewById(R.id.act2_school);

        act2_grade = findViewById(R.id.act2_grade);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.act2_grade, android.R.layout.simple_spinner_item);
        act2_grade.setAdapter(adapter2);
        act2_grade.setSelection(0);


        act2_cls = findViewById(R.id.act2_cls);
        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.act2_cls, android.R.layout.simple_spinner_item);
        act2_cls.setAdapter(adapter3);
        act2_cls.setSelection(0);

        act2_num = findViewById(R.id.act2_num);
        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.act2_num, android.R.layout.simple_spinner_item);
        act2_num .setAdapter(adapter4);
        act2_num.setSelection(0);

        act2_tall = findViewById(R.id.act2_tall);
        act2_weight = findViewById(R.id.act2_weight);
        act2_image = findViewById(R.id.act2_image);
        act2_Rbtn = findViewById(R.id.act2_Rbtn);

        act2_Rbtn.setText("수정");
        act2_Rbtn.setOnClickListener(v -> update());

         findViewById(R.id.logout).setOnClickListener(v->{
             FirebaseAuth.getInstance().signOut();
             finish();
        });

        loading();
    }

    String mPhotoID[] =  {"default"};
    private void loading(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("로딩중...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserPublicInfoDAO.selectUserByUID(new ArrayList<String>() {{
            add(uid);
        }}, new UserPublicInfoDAO.OnSelectedLisnter() {
            @Override
            public void onSelected(boolean success, Map<String, UserPublicInfoBean> userPublicInfoBeanMap, DatabaseError databaseError) {
                if(success && userPublicInfoBeanMap.containsKey(uid)){
                    UserPublicInfoBean infoBean = userPublicInfoBeanMap.get(uid);
                    UserPhotoDAO.selectPhotoByPhotoID(infoBean.photoID, new UserPhotoDAO.OnDownloadComplete() {
                        @Override
                        public void OnDownloadComplete(boolean success, Uri photoResource, Exception e) {
                            progressDialog.dismiss();
                            if(success){
                                act2_id.setText(infoBean.id);
                                act2_name.setText(infoBean.name);
                                act2_sex.setSelection(infoBean.gener.equals("남") ?  0 : 1);
                                act2_school.setText(infoBean.school);
                                act2_grade.setSelection(Integer.valueOf(infoBean.grade+"") - 4);
                                act2_cls.setSelection(Integer.valueOf(infoBean._class+"") - 1);
                                act2_num.setSelection(Integer.valueOf(infoBean.number+"") - 1);
                                act2_tall.setText(String.format("%.2f", infoBean.height));
                                act2_weight.setText(String.format("%.2f",infoBean.weight));
                                mPhotoID[0] = infoBean.photoID;
                                act2_image.setImageURI(photoResource);
                            } else {
                                Toast.makeText(StartEditController.this, "불러오지 못하였습니다.", Toast.LENGTH_SHORT).show();
                                Log.e("starteditcontrool", e.getMessage());
                                finish();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(StartEditController.this, "불러오지 못하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.e("starteditcontrool", databaseError.getMessage());
                    finish();
                }
            }
        });
    }

    private void update(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("업데이트중...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final EventChain eventChain2 = new EventChain();
        final EventChain eventChain3 = new EventChain();
        eventChain2.ready("이미지 업로드");
        eventChain3.ready("계정 정보 저장");

        if(imageUri != null){
            UserPhotoDAO.uploadUserPhoto(imageUri, new UserPhotoDAO.OnUploadComplete() {
                @Override
                public void onUploadComplete(boolean success, String photoID, Exception exception) {
                    if(photoID != null) mPhotoID[0]= photoID;
                    if(!success)
                        Toast.makeText(StartEditController.this, "이미지 업로드 실패\n" + exception.getMessage(), Toast.LENGTH_LONG).show();
                    eventChain2.complete("이미지 업로드");
                }
            });
        } else {
            Toast.makeText(this, "이미지 변경되지 않음", Toast.LENGTH_SHORT).show();
            eventChain2.complete("이미지 업로드");
        }

        eventChain2.andthen(()->{
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            long grade = 0;
            long _class = 0;
            long number = 0;
            double height = 0;
            double wegith = 0;
            try{
                grade = Long.valueOf(act2_grade.getSelectedItem().toString());
                _class = Long.valueOf(act2_cls.getSelectedItem().toString());
                number = Long.valueOf(act2_num.getSelectedItem().toString());
                height = Double.valueOf(act2_tall.getText().toString());
                wegith = Double.valueOf(act2_weight.getText().toString());
            }
            catch (Exception e){ e.printStackTrace(); }

            UserPublicInfoDAO
                    .addUser(uid,new UserPublicInfoBean(
                            act2_name.getText().toString(),
                            act2_sex.getSelectedItem().toString(),
                            act2_id.getText().toString(),
                            mPhotoID[0],
                            act2_school.getText().toString(),
                            grade,
                            _class,
                            number,
                            height,
                            wegith)
                    )
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful())
                                Toast.makeText(StartEditController.this, "계정 정보 저장 실패", Toast.LENGTH_SHORT).show();
                            eventChain3.complete("계정 정보 저장");
                        }
                    });

        }, "이미지 업로드");

        eventChain3.andthen(()->{
            progressDialog.dismiss();
            Toast.makeText(StartEditController.this, "업데이트 완료",Toast.LENGTH_LONG).show();
            finish();
        }, "계정 정보 저장");
    }

    Uri imageUri = null;
    private void onTakePhoto(Uri takedImageUri){
        //일단 업로드할 이미지가 선택됬다면 부랴 부랴 일단 이미지를 보여줘요
        imageUri = takedImageUri;
        act2_image.setImageURI(imageUri);
    }

    //사진 어디서 가져올까요? 물어보는 팝업창 띄워주는 메소드애오
    private void ShowTakePhotoDialog(){
        new AlertDialog.Builder(this) // 팝업창 빌드기.
                .setTitle("사진 가져오기") // 팝업창 제목
                .setMessage("사진을 어디서 가져올까요?") // 팝업창 내용
                .setCancelable(false) // 바깥 터치하면 그냥 바로 사라짐
                .setPositiveButton("갤러리", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 갤러리 버튼 눌렀을 때 처리할 내용
                        getAlbum(); // 이제 저 아래 세상에 처리 코드가 있어오
                    }
                })
                .setNeutralButton("사진 찍기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // 사진찍기 버튼 눌렀을 때 처리할 내용
                        captureCamera(); // 이제 저 아래 세상에 처리 코드가 있어오
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 취소 눌렀을 때 처리할 내용. 딱히 없죠?
                        finish();
                    }
                })
                .create() //이렇게 세팅한 내용으로 팝업창 빌드 해여
                .show(); // 빌드 완료 되면 이제 띄워요!
    }

    /*******************************************************************************************
     * 여기서 부터는 사진 불러오거나, 찍는 코드를 몰아놨어요. 복잡할 수도 있어요.*
     * 사진찍기나, 앨범가져오기, 사진 자르기는 앱 밖에서 처리하는 거라 다른 세상과 통신 할 필요가 있어요
     * */

    // 여기 다른 세상과 통신하기 위한 간한한 구별 신호를 정하는 거애오
    private static final int REQ_TAKE_PHOTO = 498;
    private static final int REQ_TAKE_ALBUM = 914;
    private static final int REQ_IMAGE_CROP = 991;

    Uri imageURI, resultUIR;

    //imageURI는 카메라로 찍어온 이미지를 잠시 저장할.
    //resultUIR는 크롭까지 준비된 이미지를 잠시 저장할

    // 앨범에서 사진을 가져오기 위한 메소드애오
    private void getAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent,REQ_TAKE_ALBUM); //위에서 정했던 통신 신호 여기서 쓰네요
    }

    // 카메라로 사진 찍어 가져오기 위한 메소드 애오
    private void captureCamera(){
        String state = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            //외장 메모리가 사용 가능시(사용가능)
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                File photoFile = LocalPhotoCache.getCacheFile("camera",new Date().getTime() + "-" + (int)(Math.random() * 100000)+".jpg");
                // 여기서 쓸수 있는 빈 파일 찾아와요.
                //이미지 파일 만들기 성공했다면
                if(photoFile != null){
                    //다른 앱에 파일 공유하기 위한 프로바이더 생성
                    imageURI = FileProvider.getUriForFile(this,getPackageName(),photoFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI); // 가져온 빈파일에 채워달라고 요청하는 거애오.
                    startActivityForResult(intent, REQ_TAKE_PHOTO); //위에서 정했던 통신 신호 여기서 쓰네요
                }
            }
            else {
                Toast.makeText(this,"저장 공간에 접근이 불가능합니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    // 앨범이든, 카메라로 찍었든, 가져온 이미지를 자르기를 위한 메소드 애오
    private void cropImage(Uri targetImage){
        resultUIR = Uri.fromFile(LocalPhotoCache.getCacheFile("camera",new Date().getTime() + "-" + (int)(Math.random() * 100000)+".jpg")); // 여기서 쓸수 있는 빈 파일 찾아와요.

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(targetImage,"image/*"); //아저씨 이 사진으로 잘라주세오
        intent.putExtra("outputX",200);
        intent.putExtra("outputY",200);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("scale",true);
        intent.putExtra("output",resultUIR); // 가져온 빈파일에 채워달라고 요청하는 거애오
        startActivityForResult(intent,REQ_IMAGE_CROP); //위에서 정했던 통신 신호 여기서 쓰네요
    }

    // 위에서 외부로 요청한 애들은 다 여기서 응답해줘요/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            // 아! 외부에서 사진 찍기 잘 처리해서 넘겨줬다고 하는군요!
            case REQ_TAKE_PHOTO:{
                if(resultCode == RESULT_OK){
                    // 이미 미리 준비해놓은 imageURI 변수에 잘 담아 줬을 꺼얘요!
                    cropImage(imageURI); // 그럼 바로 잘라달라고 다른 애한테 요청해줘요!
                }
            }break;
            // 아! 외부에서 사용자가 선택한 앨범 사진이 왔다고 하는군요!
            case REQ_TAKE_ALBUM:{
                if(resultCode == RESULT_OK){
                    if(data.getData() != null){
                        // 선택된 앨범 사진 파일은 data.getData() 담겨 있어요!
                        cropImage(data.getData()); // 그럼 바로 잘라달라고 다른 애한테 요청해줘요!
                    }
                }
            }break;

            //앗! 이제 이미지 자르기까지 모두 완료했다군요
            case REQ_IMAGE_CROP:{
                if(resultCode == RESULT_OK){
                    // 이미 미리 준비해놓은 resultURI 변수에 알아수 잘 담아줬을꺼예요
                    // 이제 위에서 결과적으로 다 처리된 이미지를 넘겨줍시다.
                    onTakePhoto(resultUIR);
                }
            }break;
        }
    }
}
