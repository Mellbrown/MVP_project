package com.techwork.kjc.mvp_project.bean;

/**
 * Created by mlyg2 on 2018-06-11.
 */

public class ProfileUpdateBean {
    public final static String MALE = "남";
    public final static String FEMALE = "여";
    public final static String None = "미지정";

    public String uid = "";
    public String name = "";
    public String photoUrl = "";

    public String gender = None;
    public Long grade = 0l;
    public Long _class = 0l;
    public Long number = 0l;

    public Double height = 0.0;
    public Double weight = 0.0;
}
