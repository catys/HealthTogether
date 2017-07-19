package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpVital;
import com.yamada.biton.healthtogether.AsyncTasksPackage.httpconnection;

/**
 * Created by 優太 on 2017/06/28.
 */

public class MainActivity extends AppCompatActivity {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SQLite
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // queryメソッドの実行例
        Cursor c = db.query("user", new String[] { "mail", "monitor" }, null,
                null, null, null, null);

        boolean mov = c.moveToFirst();
        if (mov) {
            mymail = c.getString(0);
        }
        c.close();
        db.close();

        httpconnection con = new httpconnection();
        con.ResultDisplay(MainActivity.this,"a@gmail.com"/*mymail"testman1"*/);
        Button sendButton = (Button) findViewById(R.id.pairing);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vitalinsertを呼ぶ
            }
        });

        //グラフ非同期
        ////////////////////////////
        ConnectHttpVital postdata = new ConnectHttpVital();
        postdata.MeasurementHistorySelect(this,"a@gmail.com");
        ConnectHttpVital postdata1 = new ConnectHttpVital();
        postdata1.Totalvital(this,"a@gmail.com");
    }

    public void FriendClick(View v){
        Intent intent = new Intent(MainActivity.this, FriendActivity.class);
        startActivity(intent);
    }

    public void ScheduleClick(View v){
        Intent intent = new Intent(MainActivity.this, ScheduleShareActivity.class);
        startActivity(intent);
    }

    public void UserClick(View v) {
        Intent intent = new Intent(MainActivity.this, MeasurementHistoryActivity.class);
        startActivity(intent);

    }

    public void MeasurementHistoryClick(View v){
        Intent intent = new Intent(MainActivity.this, MeasurementHistoryActivity.class);
        startActivity(intent);
    }
}
