package com.yamada.biton.healthtogether;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpFriend;
import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpSchedule;

import java.util.Calendar;

/**
 * Created by 優太 on 2017/07/06.
 */

public class ScheduleActivity extends AppCompatActivity {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;

    //現在の日付を取得
    Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH) + 1;// 0 - 11
    int day = cal.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //SI = (Global) this.getApplication();

        Global.resetScheduleinfo();

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
        date7.setText((day + 6) + "日");

        ConnectHttpSchedule post = new ConnectHttpSchedule();
        post.ScheduleSet(this,mymail);
    }

    public void ScheduleAdd(View v){
        String date = String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-";

        CheckBox cb1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox cb11 = (CheckBox) findViewById(R.id.checkBox11);
        CheckBox cb111 = (CheckBox) findViewById(R.id.checkBox111);
        CheckBox cb2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox cb22 = (CheckBox) findViewById(R.id.checkBox22);
        CheckBox cb222 = (CheckBox) findViewById(R.id.checkBox222);
        CheckBox cb3 = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox cb33 = (CheckBox) findViewById(R.id.checkBox33);
        CheckBox cb333 = (CheckBox) findViewById(R.id.checkBox333);
        CheckBox cb4 = (CheckBox) findViewById(R.id.checkBox4);
        CheckBox cb44 = (CheckBox) findViewById(R.id.checkBox44);
        CheckBox cb444 = (CheckBox) findViewById(R.id.checkBox444);
        CheckBox cb5 = (CheckBox) findViewById(R.id.checkBox5);
        CheckBox cb55 = (CheckBox) findViewById(R.id.checkBox55);
        CheckBox cb555 = (CheckBox) findViewById(R.id.checkBox555);
        CheckBox cb6 = (CheckBox) findViewById(R.id.checkBox6);
        CheckBox cb66 = (CheckBox) findViewById(R.id.checkBox66);
        CheckBox cb666 = (CheckBox) findViewById(R.id.checkBox666);
        CheckBox cb7 = (CheckBox) findViewById(R.id.checkBox7);
        CheckBox cb77 = (CheckBox) findViewById(R.id.checkBox77);
        CheckBox cb777 = (CheckBox) findViewById(R.id.checkBox777);

        ////月末から月の変更がまだできてない//1～9までは0を付けないといけない
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2000);
        cal.set(Calendar.MONTH, 1);
        int lastDayOfMonth = cal.getActualMaximum(Calendar.DATE);

        //時間ないので脳筋で～
        int dflag = 0;
        int carryflag = 0;
        int day = Global.getDay();
        for(int i = 0; i < 7;i++){
            day = day+i;
            if(lastDayOfMonth == day){
                dflag = i;
            }else if(10 == day){
                carryflag = i;
            }
        }

        int mflag = 0;
        int month = Global.getMonth();
        if((dflag != 0) && (month == 12)){
            mflag = 1;
        }


/*
        if(dflag == 0){
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay()),String.valueOf( cb1.isChecked()),String.valueOf( cb11.isChecked()),String.valueOf( cb111.isChecked()));
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay() + 1),String.valueOf( cb2.isChecked()),String.valueOf( cb22.isChecked()),String.valueOf( cb222.isChecked()));
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay() + 2),String.valueOf( cb3.isChecked()),String.valueOf( cb33.isChecked()),String.valueOf( cb333.isChecked()));
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay() + 3),String.valueOf( cb4.isChecked()),String.valueOf( cb44.isChecked()),String.valueOf( cb444.isChecked()));
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay() + 4),String.valueOf( cb5.isChecked()),String.valueOf( cb55.isChecked()),String.valueOf( cb555.isChecked()));
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay() + 5),String.valueOf( cb6.isChecked()),String.valueOf( cb66.isChecked()),String.valueOf( cb666.isChecked()));
            Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay() + 6),String.valueOf( cb7.isChecked()),String.valueOf( cb77.isChecked()),String.valueOf( cb777.isChecked()));
        }else if(dflag != 0){
            if(dflag == 1){
                Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth()) + "-" + String.valueOf(Global.getDay()),String.valueOf( cb1.isChecked()),String.valueOf( cb11.isChecked()),String.valueOf( cb111.isChecked()));
                if(mflag == 1){
                    Global.setScheduleinfo(String.valueOf(Global.getYear() + 1) + "-" + String.valueOf(Global.getMonth() + 1) + "-01",String.valueOf( cb2.isChecked()),String.valueOf( cb22.isChecked()),String.valueOf( cb222.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear() + 1) + "-" + String.valueOf(Global.getMonth() + 1) + "-02",String.valueOf( cb3.isChecked()),String.valueOf( cb33.isChecked()),String.valueOf( cb333.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear() + 1) + "-" + String.valueOf(Global.getMonth() + 1) + "-03",String.valueOf( cb4.isChecked()),String.valueOf( cb44.isChecked()),String.valueOf( cb444.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear() + 1) + "-" + String.valueOf(Global.getMonth() + 1) + "-04",String.valueOf( cb5.isChecked()),String.valueOf( cb55.isChecked()),String.valueOf( cb555.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear() + 1) + "-" + String.valueOf(Global.getMonth() + 1) + "-05",String.valueOf( cb6.isChecked()),String.valueOf( cb66.isChecked()),String.valueOf( cb666.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear() + 1) + "-" + String.valueOf(Global.getMonth() + 1) + "-06",String.valueOf( cb7.isChecked()),String.valueOf( cb77.isChecked()),String.valueOf( cb777.isChecked()));
                }else{
                    Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth() + 1) + "-01",String.valueOf( cb2.isChecked()),String.valueOf( cb22.isChecked()),String.valueOf( cb222.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth() + 1) + "-02",String.valueOf( cb3.isChecked()),String.valueOf( cb33.isChecked()),String.valueOf( cb333.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth() + 1) + "-03",String.valueOf( cb4.isChecked()),String.valueOf( cb44.isChecked()),String.valueOf( cb444.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth() + 1) + "-04",String.valueOf( cb5.isChecked()),String.valueOf( cb55.isChecked()),String.valueOf( cb555.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth() + 1) + "-05",String.valueOf( cb6.isChecked()),String.valueOf( cb66.isChecked()),String.valueOf( cb666.isChecked()));
                    Global.setScheduleinfo(String.valueOf(Global.getYear()) + "-" + String.valueOf(Global.getMonth() + 1) + "-06",String.valueOf( cb7.isChecked()),String.valueOf( cb77.isChecked()),String.valueOf( cb777.isChecked()));
                }
            }else if(dflag == 2){

            }else if(dflag == 3){

            }else if(dflag == 4){

            }else if(dflag == 5){

            }else if(dflag == 6){

            }
        }
*/
        ConnectHttpSchedule post = new ConnectHttpSchedule();
        post.ScheduleInsert(this,mymail);
    }
}
