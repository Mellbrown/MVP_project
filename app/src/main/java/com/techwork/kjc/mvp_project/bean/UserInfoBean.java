package com.techwork.kjc.mvp_project.bean;

import android.support.annotation.NonNull;

/**
 * Created by mlyg2 on 2018-06-10.
 * 앞으로 바뀔 이유가 없거나 바뀌어도 지난 데이터를 유지할 이유가 없는
 * 개인정보 데이터 모음입니다.
 */

public class UserInfoBean {

    public final static String MALE = "남";
    public final static String FEMALE = "여";
    public final static String None = "미지정";

    public String uid = "";
    public String email = "";
    public String name = "";
    public String photoUrl = "";

    public String gender = None;
    public Long grade = 0l;
    public Long _class = 0l;
    public Long number = 0l;

    public Double height = 0.0;
    public Double weight = 0.0;

}
