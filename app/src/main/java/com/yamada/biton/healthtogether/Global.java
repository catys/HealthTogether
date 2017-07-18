package com.yamada.biton.healthtogether;

import android.app.Application;

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
    private int flag;
    private List<String> friendinfo;

    public void setFlag(int i){
        flag = i;
    }

    public int getFlag(){
        return flag;
    }


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
    private static String tvital;
    private static float[] vData ;
    public static void setvital(float[]ydata){
        vData = new float[30];
        vData = ydata;
    }

    public static float[] getvital(){
        return vData;
    }

    public static void settotal(String Tvital){
        tvital = Tvital;
        System.out.println("settotal");
        System.out.println(tvital);
    }
    public static String gettotal(){
        System.out.println("gettotal");
        System.out.println(tvital);
        return tvital;
    }
}