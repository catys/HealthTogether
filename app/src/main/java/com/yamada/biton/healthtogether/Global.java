package com.yamada.biton.healthtogether;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 優太 on 2017/06/28.
 */

public class Global extends Application {



    @Override
    public void onCreate(){
        super.onCreate();
    }

    //フレンド関係/////////////////////////////////////
    static int flag;

    public static void setFlag(int i){
        flag = i;
    }

    public static int getFlag(){
        return flag;
    }

    ////////////////////////////////////////////////////
    //スケジュール関係//////////////////////////////////
    static long id;
    static List<List<String>> scheduleinfo = new ArrayList<List<String>>();
    static List<String> friendlist = new ArrayList<String>();
    static int year;
    static int month;
    static int day;

    public static void resetScheduleinfo(){
        scheduleinfo.clear();
        friendlist.clear();
    }

    public static void setScheduleinfo(String date,String morning,String noon,String night){
        List<String> sf = new ArrayList<String>();

        int flag = 0;
        for(int x = 0; x < scheduleinfo.size(); x++){
            if(date.equals(scheduleinfo.get(x).get(0))){
                if(!morning.equals(scheduleinfo.get(x).get(1))){
                    scheduleinfo.get(x).set(1, morning);
                }else if(!noon.equals(scheduleinfo.get(x).get(2))){
                    scheduleinfo.get(x).set(2, noon);
                }else if(!night.equals(scheduleinfo.get(x).get(3))){
                    scheduleinfo.get(x).set(3, night);
                }else{
                    flag = 1;
                }
            }
        }
        if(flag == 0){
            sf.add(date);
            sf.add(morning);
            sf.add(noon);
            sf.add(night);
            scheduleinfo.add(sf);
        }
    }

    public static List<List<String>> getScheduleinfo(){
        return scheduleinfo;
    }

    public static int getScheduleinfoSize(){
        return scheduleinfo.size();
    }

    public static void setYear(int y){
        year = y;
    }

    public static int getYear(){
        return year;
    }

    public static void setMonth(int m){
        month = m;
    }

    public static int getMonth(){
        return month;
    }

    public static void setDay(int d){
        day = d;
    }

    public static int getDay(){
        return day;
    }

    public static void setFriendlist(String friend){
        friendlist.add(friend);
    }

    public static List<String> getFriendlist(){ return friendlist;}
    ////////////////////////////////////////////////////

    ///////////BitMap共有用///////////////////////////////////////////////
    public static android.graphics.Bitmap bmp;

    public void setbmp(android.graphics.Bitmap bmp){
        this.bmp = bmp;
    }

    public android.graphics.Bitmap getbmp(){
        return this.bmp;
    }
    ///////////////////////////////////////////////////////////////////////

    //グラフ
    //////////////////////////////////////////
    private static float tvital;
    private static float[] vData;
    private static String[] dData;

    public static void setvital(float[] ydata) {
        vData = new float[30];
        vData = ydata;
    }

    public static float[] getvital() {
        return vData;
    }

    public static void settotal(float Tvital) {
        tvital = Tvital;

    }

    public static float gettotal() {

        return tvital;
    }

    public static void setdate(String[] ddata) {
        dData = ddata;

    }
    public static String[] getdate() {

        return dData;

    }
}