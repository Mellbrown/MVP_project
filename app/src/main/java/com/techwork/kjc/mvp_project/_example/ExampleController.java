package com.techwork.kjc.mvp_project._example;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class ExampleController extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, ExampleActivity.class);
        intent.putExtra(ExampleActivity.PARAM_MESSAGE, "hello?");
        intent.putExtra(ExampleActivity.REQUEST_LISTNER, new ExampleActivity.RequestListener() {
            @Override
            public String onRequestNewMessage() {
                return "비동기적인 데이터 호출";
            }
        });
        startActivityForResult(intent, 141);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 141:{
                switch (resultCode){
                    case ExampleActivity.RES_LINK_MEASURE : // 다른 처리;
                    break;
                }
            }break;
        }
    }
}
