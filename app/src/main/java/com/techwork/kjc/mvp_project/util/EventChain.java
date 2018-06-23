package com.techwork.kjc.mvp_project.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

// 비동기적으로 동작하는 이벤트를 동기적으로 엮어 줄 수 있는 유틸이에오
// 정적으로 동작하니 모든 다른 지역의 이벤트로 엮을 수 있어요!

public class EventChain {
    public static EventChain global = new EventChain();

    HashMap<String, Boolean> state = new HashMap<>();
    ArrayList<RunItem> runItems = new ArrayList<>();

    public void ready(String label){ // 한 이벤트에 대해 들을 준비를 시켜요!
        state.put(label,false);
        Log.i("EventChain", "Reday for " + label);
    }

    public void complete(String label){ //이제 해당 이벤트가 완료되었을때를 여기로 알려줘여
        if (state.containsKey(label)) {
            if (!state.get(label)) {
                state.put(label,true);
                Log.i("EventChain","Complete " + label);
                internalCheck();
            } else{
                Log.w("EventChain", "already complete about " + label);
            }
        }else{
            Log.e("EventChain", "not ever been ready about " + label);
        }
    }

    public void andthen(CallBack runWith, String... labels){ // 여러 이벤트 엮어서 해당 이벤트가 완료 다됬으면 이 이벤트로 알려 준다는 거졍!
        RunItem runItem = new RunItem();
        runItem.labels = labels;
        runItem.runWith = runWith;
        runItems.add(runItem);
        internalCheck();
    }

    private void internalCheck(){
        for(RunItem runItem : runItems){
            boolean isAllComplete = true;

            completeCheck: for(String label : runItem.labels){
                if (state.containsKey(label)) {
                    if (!state.get(label)) {
                        isAllComplete = false;
                        break completeCheck;
                    }
                }else{
                    Log.e("EventChain", "not rerfern about " + label);
                }
            }

            if(isAllComplete){
                Log.i("EventChain","meet a condition follow " + runItem.labels + " and run AndthenEvent");
                runItem.runWith.run();
                runItems.remove(runItem);
            }
        }
    }

    class RunItem{
        String[] labels;
        CallBack runWith;
    }

    public interface CallBack{
        void run();
    }
}
