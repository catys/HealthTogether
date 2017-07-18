package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpSchedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 優太 on 2017/07/12.
 */

public class ScheduleShareActivity  extends AppCompatActivity {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;

    //現在の日付を取得
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;// 0 - 11
    int day = cal.get(Calendar.DAY_OF_MONTH);

    private Spinner spinner;
    private TextView textView;
    // 選択肢
    //private String spinnerItems[] = {"Spinner", "Android", "Apple", "Windows"};
    //private List<String> spinnerItems = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduleshare);

        Global.setYear(year);
        Global.setMonth(month);
        Global.setDay(day);

        //1週間分の日付
        TextView date1 = (TextView)findViewById(R.id.date1);
        TextView date2 = (TextView)findViewById(R.id.date2);
        TextView date3 = (TextView)findViewById(R.id.date3);
        TextView date4 = (TextView)findViewById(R.id.date4);
        TextView date5 = (TextView)findViewById(R.id.date5);
        TextView date6 = (TextView)findViewById(R.id.date6);
        TextView date7 = (TextView)findViewById(R.id.date7);

        String datechar = month + "月" + day + "日 ";
        date1.setText(datechar);

        date2.setText((day + 1) + "日 ");
        date3.setText((day + 2) + "日 ");
        date4.setText((day + 3) + "日 ");
        date5.setText((day + 4) + "日 ");
        date6.setText((day + 5) + "日 ");
        date7.setText((day + 6) + "日 ");

        Global.resetScheduleinfo();

        ConnectHttpSchedule post = new ConnectHttpSchedule();
        post.ShareFriend(this,mymail);
    }

    public void ScheduleEntry(View v){
        Intent intent = new Intent(ScheduleShareActivity.this, ScheduleActivity.class);
        startActivity(intent);
    }
}
