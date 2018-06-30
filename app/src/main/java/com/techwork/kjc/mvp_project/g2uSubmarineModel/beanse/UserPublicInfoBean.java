package com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse;

public class UserPublicInfoBean {

    public static final String GENDER_MAIL = "남";
    public static final String GENDEE_FEMAIL = "여";
    public static final String GENDER_NONE = "미지정";
    public static final String DEFAULT_PHOTO_ID = "default";

    public String name = "";
    public String gener = GENDER_NONE;

    public String id = "";
    public String photoID = DEFAULT_PHOTO_ID;

    public String school = "";
    public Long grade = 0l;
    public Long _class = 0l;
    public Long number = 0l;
    public Double height = 0.0;
    public Double weight = 0.0;

    public UserPublicInfoBean(){}
    public UserPublicInfoBean(
            String name,
            String gener,
            String id,
            String photoID,
            String school,
            Long grade,
            Long _class,
            Long number,
            Double height,
            Double weight){
        this.name = name;
        this.gener = gener;
        this.id = id;
        this.photoID = photoID;
        this.school = school;
        this.grade = grade;
        this._class = _class;
        this.number = number;
        this.height = height;
        this.weight = weight;
    }
}
