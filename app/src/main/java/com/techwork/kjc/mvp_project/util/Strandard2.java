package com.techwork.kjc.mvp_project.util;

import android.media.midi.MidiDevice;

import java.util.HashMap;
import java.util.Map;

public class Strandard2 {

    public static final String TOP = "상";
    public static final String MID = "중";
    public static final String BOT = "하";
    public static final String None = "평가 대상 아님";
    public static final String ELSE = "그 외";

    public static final String ARM ="팔";
    public static final String LEG = "다리";
    public static final String BACK = "등(배)";
    public static final String BODY = "전신";

    public static final String MAIL = "남";
    public static final String FEMAIL = "여";

    public static class EvaluePart{
        public String part;
        public String gender;
        public int grade;

        public EvaluePart(int grade, String gender, String part){
            this.grade = grade;
            this.gender = gender;
            this.part = part;
        }

        @Override
        public int hashCode() {
            return part.hashCode() + gender.hashCode() + grade;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof EvaluePart){
                EvaluePart o = (EvaluePart) obj;
                return g2u.NullEqual(part,o.part)
                        && g2u.NullEqual(gender, o.gender)
                        && grade == grade;
            }
            return false;
        }
    }

    public static class EvalueClass{
        public int top;
        public int mid;
        public int bot;

        public EvalueClass(int top, int mid, int bot){
            this.top = top;
            this.mid = mid;
            this.bot = bot;
        }
    }

    public static final Map<EvaluePart,EvalueClass> standardMap
            = new HashMap<EvaluePart,EvalueClass>(){
        {
            int[][] data = {
                    {17,15, 21,19, 25,23},
                    {10, 8, 12,10, 14,12},
                    { 5, 5,  7, 7,  9, 9},

                    {27,25, 31,29, 35,33},
                    {15,13, 17,15, 19,17},
                    { 9, 9, 10,10, 11,11},

                    {37,35, 41,39, 45,43},
                    {25,23, 27,25, 29,27},
                    {19,19, 20,20, 21,21},

                    {47,45, 51,49, 55,53},
                    {35,33, 37,35, 39,37},
                    {29,29, 30,30, 32,32}
            };

            for(int i = 0; 6 > i ; i++){
                for(int j = 0 ; 12 > j; j ++){
                    int p = j / 4;
                    put(new EvaluePart(
                            (int)( i / 2) + 4,
                            i % 2 == 0 ? MAIL : FEMAIL,
                            p == 0 ? ARM : p == 1 ? LEG : p == 2 ?  BACK : BODY
                    ),new EvalueClass(
                            data[p + 0][i],
                            data[p + 1][i],
                            data[p + 2][i]
                    ));
                }
            }
        }
    };


    public static String evaluation(EvaluePart evaluePart, double val){
        EvalueClass evalueClass = standardMap.get(evaluePart);
        if(evalueClass == null) return None;
        if( val > evalueClass.top){
            return TOP;
        } else if( val > evalueClass.mid){
            return MID;
        } else if( val > evalueClass.bot) {
            return BOT;
        } else return ELSE;
    }

    public static final String TOP3 = "근력 및 근지구력이 아주 뛰어난 학생입니다. 이 근력을 유지하기 위해 근력운동을 꾸준히 하세요.";
    public static final String BOT3 = "근력 및 근 지구력이 많이 부족합니다. MVP로 꾸준히 근력운동을 하면 근력왕이 될 수 있어요.";
    public static final String ELSE3 = "근력 및 근지구력이 양호합니다. 근력왕이 될 수 있도록 꾸준히 근력 운동을 하세요";

    public static final String EachBad = "근력이 부족한 신체 부위로 집중적인 근력 운동이 필요합니다. MVP로 부족한 부위의 근력운동을 꾸준히 해 보세요.";
    public static final String EachNormal = "보통 수준의 근력을 가지고 있습니다. MVP로 꾸준히 근력 운동을 하여 근력왕이 될 수 있도록 노력해 보세요.";
    public static final String EachGood = "근력이 아주 뒤어난 신체부위입니다. 근력이 유지될 수 있도록 꾸준히 근력운동을 하세요.";

}
