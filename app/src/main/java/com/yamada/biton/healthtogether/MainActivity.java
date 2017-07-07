package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * Created by 優太 on 2017/06/28.
 */

public class MainActivity extends AppCompatActivity {

    //ユーザーのメールアドレスを設定
    String mymail = "a@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void FriendClick(View v){
        Intent intent = new Intent(MainActivity.this, FriendActivity.class);
        startActivity(intent);
    }

    public void MeasurementHistoryClick(View v){
        Log.d("テスト", "これはメッセージです。3");

        Intent intent = new Intent(MainActivity.this, MeasurementHistoryActivity.class);
        startActivity(intent);
    }
}
