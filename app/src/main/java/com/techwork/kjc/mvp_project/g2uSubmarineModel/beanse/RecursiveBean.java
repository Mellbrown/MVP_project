package com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse;

public class RecursiveBean {
    public String first = "";
    public String second = "";
    public String thrid = "";
    public String fourth = "";
    public Long reps = 0l;

    public RecursiveBean(){}
    public RecursiveBean(
            String first,
            String second,
            String thrid,
            String fourth,
            Long reps
    ){
        this.first = first;
        this.second = second;
        this.thrid = thrid;
        this.fourth = fourth;
        this.reps = reps;
    }
}
