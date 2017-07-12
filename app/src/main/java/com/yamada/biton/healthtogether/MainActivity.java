package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 優太 on 2017/06/28.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void FriendClick(View v){
        Intent intent = new Intent(MainActivity.this, FriendActivity.class);
        startActivity(intent);
    }

    public void ScheduleClick(View v){
        Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
        startActivity(intent);
    }
}
