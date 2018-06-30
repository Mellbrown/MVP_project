package com.techwork.kjc.mvp_project.g2uSubmarineModel.beanse;

public class FocusBean {
    public Long timestamp;
    public Long level;
    public Long reps;

    @Override
    public String toString() {
        return String.format("%d-%dlv %drep", timestamp, level, reps);
    }
}
