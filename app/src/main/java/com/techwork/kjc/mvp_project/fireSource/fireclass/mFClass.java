package com.techwork.kjc.mvp_project.fireSource.fireclass;

import java.util.HashMap;

public class mFClass {
    String abody,arm,back,leg;
    public boolean setData(HashMap<String, String> info){
        abody = info.get("abody");
        arm= info.get("arm");
        back = info.get("back");
        leg = info.get("leg");
        if(info.size()!=4)
            return false;
        return true;
    }
}
