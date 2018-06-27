package com.techwork.kjc.mvp_project.fireSource.fireclass;

import java.util.HashMap;

public class UserFire {
    public String cls,grade,id,name,num,pw,school,sex,tall,weight,path,uid;
    public boolean setData(HashMap<String, String> info){
        this.uid = info.get("uid");
        this.cls = info.get("cls");
        this.grade = info.get("grade");
        this.id = info.get("id");
        this.name = info.get("name");
        this.num = info.get("num");
        this.pw = info.get("pw");
        this.school = info.get("school");
        this.sex = info.get("sex");
        this.tall = info.get("tall");
        this.weight = info.get("weight");
        this.path = info.get("path");
        return true;
    }
}
