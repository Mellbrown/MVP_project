package com.techwork.kjc.mvp_project.util;

public class Standard {


    String sex;
    String part;
    int grade;
    int strength;
    String result;
    public static  final  String MALE = "남";
    public static  final  String FEMALE = "여";

    int ARM;
    int LEG;
    int BODY;
    int aBODY;
    int aResult;
    String PrintResult;
    String pPrintResult;


    public static  PartGrade calc(String sex, int grade, int ARM, int LEG, int BODY, int aBODY) {
        int aResult = 0;
        int rARM = 0;
        int rLEG = 0;
        int rBODY = 0;
        int raBODY = 0; // r 하 0, 중 1, 상 2
        String PrintResult = "";


        switch (sex) {
            case MALE: {
                switch (grade) {
                    case 4:
                        if (ARM >= 17) {
                            rARM = 2;
                        } else if (10 <= ARM && ARM < 17)
                            rARM = 1;
                        else {
                            rARM = 0;
                        }

                        if (LEG >= 27) {
                            rLEG = 2;
                        } else if (15 <= LEG && LEG < 27)
                            rLEG = 1;
                        else {
                            rLEG = 0;
                        }

                        if (BODY >= 37) {
                            rBODY = 2;
                        } else if (25 <= BODY && BODY < 37)
                            rBODY = 1;
                        else {
                            rBODY = 0;
                        }

                        if (aBODY >= 47) {
                            raBODY = 2;
                        } else if (35 <= aBODY && aBODY < 47)
                            raBODY = 1;
                        else {
                            raBODY = 0;
                        }

                        break;

                    case 5:
                        if (ARM >= 21) {
                            rARM = 2;
                        } else if (12 <= ARM && ARM < 21)
                            rARM = 1;
                        else {
                            rARM = 0;
                        }

                        if (LEG >= 31) {
                            rLEG = 2;
                        } else if (17 <= LEG && LEG < 31)
                            rLEG = 1;
                        else {
                            rLEG = 0;
                        }

                        if (BODY >= 41) {
                            rBODY = 2;
                        } else if (27 <= BODY && BODY < 41)
                            rBODY = 1;
                        else {
                            rBODY = 0;
                        }

                        if (aBODY >= 51) {
                            raBODY = 2;
                        } else if (37 <= aBODY && aBODY < 51)
                            raBODY = 1;
                        else {
                            raBODY = 0;
                        }

                        break;

                    case 6:
                        if (ARM >= 25) {
                            rARM = 2;
                        } else if (14 <= ARM && ARM < 25)
                            rARM = 1;
                        else {
                            rARM = 0;
                        }

                        if (LEG >= 35) {
                            rLEG = 2;
                        } else if (19 <= LEG && LEG < 35)
                            rLEG = 1;
                        else {
                            rLEG = 0;
                        }

                        if (BODY >= 45) {
                            rBODY = 2;
                        } else if (29 <= BODY && BODY < 45)
                            rBODY = 1;
                        else {
                            rBODY = 0;
                        }

                        if (aBODY >= 55) {
                            raBODY = 2;
                        } else if (39 <= aBODY && aBODY < 55)
                            raBODY = 1;
                        else {
                            raBODY = 0;
                        }
                        break;
                }
            }

            break;


            case FEMALE: {
                switch (grade) {
                    case 4:
                        if (ARM >= 15) {
                            rARM = 2;
                        } else if (8 <= ARM && ARM < 15)
                            rARM = 1;
                        else {
                            rARM = 0;
                        }

                        if (LEG >= 25) {
                            rLEG = 2;
                        } else if (13 <= LEG && LEG < 25)
                            rLEG = 1;
                        else {
                            rLEG = 0;
                        }

                        if (BODY >= 35) {
                            rBODY = 2;
                        } else if (23 <= BODY && BODY < 35)
                            rBODY = 1;
                        else {
                            rBODY = 0;
                        }

                        if (aBODY >= 45) {
                            raBODY = 2;
                        } else if (33 <= aBODY && aBODY < 45)
                            raBODY = 1;
                        else {
                            raBODY = 0;
                        }

                        break;

                    case 5:
                        if (ARM >= 19) {
                            rARM = 2;
                        } else if (10 <= ARM && ARM < 19)
                            rARM = 1;
                        else {
                            rARM = 0;
                        }

                        if (LEG >= 29) {
                            rLEG = 2;
                        } else if (15 <= LEG && LEG < 29)
                            rLEG = 1;
                        else {
                            rLEG = 0;
                        }

                        if (BODY >= 39) {
                            rBODY = 2;
                        } else if (25 <= BODY && BODY < 39)
                            rBODY = 1;
                        else {
                            rBODY = 0;
                        }

                        if (aBODY >= 49) {
                            raBODY = 2;
                        } else if (35 <= aBODY && aBODY < 49)
                            raBODY = 1;
                        else {
                            raBODY = 0;
                        }

                        break;

                    case 6:
                        if (ARM >= 23) {
                            rARM = 2;
                        } else if (12 <= ARM && ARM < 23)
                            rARM = 1;
                        else {
                            rARM = 0;
                        }

                        if (LEG >= 33) {
                            rLEG = 2;
                        } else if (17 <= LEG && LEG < 33)
                            rLEG = 1;
                        else {
                            rLEG = 0;
                        }

                        if (BODY >= 43) {
                            rBODY = 2;
                        } else if (27 <= BODY && BODY < 43)
                            rBODY = 1;
                        else {
                            rBODY = 0;
                        }

                        if (aBODY >= 53) {
                            raBODY = 2;
                        } else if (37 <= aBODY && aBODY < 53)
                            raBODY = 1;
                        else {
                            raBODY = 0;
                        }
                        break;
                }
                break;


            }
        }
        aResult = rARM+rLEG+rBODY+raBODY;
        return new PartGrade(rARM, rLEG, rBODY, raBODY, aResult);
    }

    public void PrintResult(){
        if(aResult>=7){
            PrintResult = "근력 및 근지구력이 아주 뛰어난 학생입니다. 이 근력을 유지하기 위해 근력운동을 꾸준히 하세요.";
        }
        else if(aResult<=2){
            PrintResult = "근력 및 근지구력이 많이 부족합니다. MVP로 꾸준히 근력 운동을 하면 근력왕이 될 수 있어요.";
        }
        else{
            PrintResult = "근력 및 근지구력이 양호합니다. 근력왕이 될 수 있도록 꾸준히 근력운동을 하세요.";
        }

    }
    public void pPrintResult(int partResult){

        if(partResult == 0){
            pPrintResult = "근력이 부족한 신체 부위로 집중적인 근력 운동이 필요합니다. MVP로 부족한 부위의 근력운동을 꾸준히 해 보세요.";
        }

        if(partResult == 1){
            pPrintResult = "보통 수준의 근력을 가지고 있습니다. MVP로 꾸준히 근력 운동을 하여 근력왕이 될 수 있도록 노력해 보세요";
        }
        if(partResult == 2){
            pPrintResult = "근력이 아주 뛰어난 신체부위입니다. 근력이 유지될 수 있도록 꾸준히 근력운동을 하세요.";
        }

    }


    public static class PartGrade{
        public int aResult;
        public int rARM;
        public int rLEG;
        public int rBODY;
        public int raBODY;

        public PartGrade(int rARM, int rLEG, int rBODY, int raBODY, int aResult){
            this.rARM = rARM;
            this.rLEG = rLEG;
            this.rBODY = rBODY;
            this.raBODY = raBODY;
            this.aResult = aResult;
        }

    }

    public void MVP_Standard(int score){

    }

}
