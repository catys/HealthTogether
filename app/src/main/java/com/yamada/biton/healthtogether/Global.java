package com.yamada.biton.healthtogether;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 優太 on 2017/06/28.
 */

public class Global extends Application {
    private int flag;
    private List<String> friendinfo;

    @Override
    public void onCreate(){
        super.onCreate();
    }

    public void setFlag(int i){
        flag = i;
    }

    public int getFlag(){
        return flag;
    }



}