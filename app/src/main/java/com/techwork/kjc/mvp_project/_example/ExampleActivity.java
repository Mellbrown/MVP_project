package com.techwork.kjc.mvp_project._example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.techwork.kjc.mvp_project.R;

import java.io.Serializable;

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {

    // UI 변수
    private Button linkVersus;
    private Button linkRecord;
    private Button linkPractice;
    private Button linkMeasure;
    private TextView txtTest;

    // 중간 데이터 요청 리스너
    private RequestListener requestListener;

    // 액티비티에 사용되는 변수
    private String message;

    // 초기 데이터 불러오기 파라메터
    public static final String PARAM_MESSAGE = "PARAM_MESSAGE";

    // 액티비티 끝나고 처리할 요청
    public static final int RES_LINK_VERSUS = 323455;
    public static final int RES_LINK_RECORD = 3492345;
    public static final int RES_LINK_PRACTICE = 3223445;
    public static final int RES_LINK_MEASURE = 342435;

    // 액티비티 동작중 필요한 데이터를 요청하기 위한 리스너
    public static final String REQUEST_LISTNER = "REQUEST_LISTNER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_example);

        //UI 로드
//        linkVersus = findViewById(R.id.linkVersus);
//        linkRecord = findViewById(R.id.linkRecord);
//        linkPractice = findViewById(R.id.linkPractice);
//        linkMeasure = findViewById(R.id.linkMeasure);
//        txtTest = findViewById(R.id.txtTest);

        linkVersus.setOnClickListener(this);
        linkRecord.setOnClickListener(this);
        linkPractice.setOnClickListener(this);
        linkMeasure.setOnClickListener(this);

        // intent에서 구현된 요청 리스너 받기
        requestListener = ((RequestListener) getIntent().getSerializableExtra(REQUEST_LISTNER));
        // 초기 데이터 받아오기
        message = getIntent().getStringExtra(PARAM_MESSAGE);
        // 초기 데이터로 액티비티에 그리기
        txtTest.setText(message);


        // 차후에 필요한 데이터를 비동기적으로 데이터 요청
        message = requestListener.onRequestNewMessage();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //액티비티 끝내면서 내용 전달하기
//            case R.id.linkVersus : setResult(RES_LINK_VERSUS); finish(); break;
//            case R.id.linkRecord : setResult(RES_LINK_RECORD); finish(); break;
//            case R.id.linkPractice : setResult(RES_LINK_PRACTICE); finish(); break;
//            case R.id.linkMeasure : setResult(RES_LINK_MEASURE); finish(); break;
        }
    }

    // 필요한 데이터를 요청하기 위한 리스너 인터페이스를 작성
    public interface RequestListener extends Serializable {
        String onRequestNewMessage();
    }

}
