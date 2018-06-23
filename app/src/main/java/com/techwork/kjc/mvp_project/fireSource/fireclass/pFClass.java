package com.techwork.kjc.mvp_project.fireSource.fireclass;

import java.util.HashMap;

public class pFClass {
    public String level,reps,discript,num;
    public void setData(HashMap<String,String> info){
        discript = info.get("discript");
        level = info.get("level");
        reps= info.get("reps");
        num= info.get("num");
    }
}
