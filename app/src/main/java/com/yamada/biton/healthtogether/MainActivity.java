package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpVital;
import com.yamada.biton.healthtogether.AsyncTasksPackage.httpconnection;
import com.yamada.biton.healthtogether.AsyncTasksPackage.vitalinsert;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import jp.co.omron.healthcare.omoron_connect.wrapper.EquipmentInfo;
import jp.co.omron.healthcare.omoron_connect.wrapper.UserProfileInfo;
import jp.co.omron.healthcare.omoron_connect.wrapper.VitalData;
import jp.co.omron.healthcare.omoron_connect.wrapper.WrapperApi;

/**
 * Created by 優太 on 2017/06/28.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;
    WrapperApi mWrapperApi = null;
    boolean getMetaData = false;
    boolean mUseTimeZone = true;
    String Equipmenttype;
    int UserID;
    String SerialID;

    private static final String APP_ID = "XCXvv8SD";
 //   private GoogleApiClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGetVitalData = (Button) findViewById(R.id.pairing);
        btnGetVitalData.setOnClickListener(button1ClickListener);


        getMetaData = false;


        mUseTimeZone = true;



        mWrapperApi = new WrapperApi(this, APP_ID, getPackageName());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
 //        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerToggle
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView Listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

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
        con.ResultDisplay(MainActivity.this,mymail);

        //グラフ非同期
        ////////////////////////////
        ConnectHttpVital postdata = new ConnectHttpVital();
        postdata.MeasurementHistorySelect(this,mymail);
        ConnectHttpVital postdata1 = new ConnectHttpVital();
        postdata1.Totalvital(this,mymail);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()){
            case R.id.menu_result:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_history:
                Intent intent2 = new Intent(this, MeasurementHistoryActivity.class);
                startActivity(intent2);
                break;
            //case R.id.menu_schedule:
                //Intent intent3 = new Intent(this, ScheduleActivity.class);
                //startActivity(intent3);
                //break;
            case R.id.menu_friend:
                Intent intent4 = new Intent(this, FriendActivity.class);
                startActivity(intent4);
                break;
            //case R.id.menu_ranking:
                //Intent intent5 = new Intent(this, Activity.class);
                //startActivity(intent5);
                //break;
            case R.id.menu_user:
                Intent intent6 = new Intent(this, UserActivity.class);
                startActivity(intent6);
                break;
            case R.id.menu_contact:
                //Intent intent7 = new Intent(this, UserActivity.class);
                //startActivity(intent7);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    View.OnClickListener button1ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            mWrapperApi = new WrapperApi(v.getContext(), APP_ID, getPackageName());
            ArrayList<EquipmentInfo> equipmentInfoList = mWrapperApi.getDeveiceList(true);


            if (equipmentInfoList != null){
                for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                    Equipmenttype = equipmentInfo.getDeviceType();
                    UserID = equipmentInfo.getUserId();
                    SerialID = equipmentInfo.getSerialId();

                }
            }
            long[] time = getFromToTime();

            String index = "";




            Log.d("Nyu", "index   = " + index);

            if (index.equals("")) {
                index = null;
            }




            String tmpDeviceNamne = null;
            int tmpoffset = 0, tmpcount = 7, tmporder = 2, tmpuserid = -1;
            String tmpserialid;


            tmpDeviceNamne = Equipmenttype;
            tmpuserid = UserID;
            tmpserialid = SerialID;

            ArrayList<VitalData> vitalDataList = mWrapperApi.GetVitalData(tmpDeviceNamne, index, time[0], time[1],
                    tmporder,
                    tmpoffset,
                    tmpcount,
                    getMetaData,
                    tmpuserid,
                    tmpserialid);




            String tvText = "";
            ArrayList<Integer> DataList = new ArrayList<Integer>();
            if (vitalDataList != null) {

                for (VitalData vital : vitalDataList) {

//入手データメモ：1,体重 2,体脂肪率 3,基礎代謝 4,骨格筋率 5,BMI 6,体年齢 7,内臓脂肪レベル
                    DataList.add(vital.getMeasurement());

                }

                //SQLite
                MyOpenHelper helper = new MyOpenHelper(MainActivity.this);
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

                UserProfileInfo info = mWrapperApi.getUserProfile();
                vitalinsert viin = new vitalinsert();
                viin.vitalpost(mymail,DataList,info);
                Toast toast = Toast.makeText(MainActivity.this,"アップロードに成功しました",Toast.LENGTH_LONG);
                toast.show();

                httpconnection con = new httpconnection();
                con.ResultDisplay(MainActivity.this,mymail);
            } else {
                Toast toast = Toast.makeText(MainActivity.this,"アップロードに失敗しました",Toast.LENGTH_LONG);
                toast.show();
            }

            //グラフ非同期
            ////////////////////////////
            ConnectHttpVital postdata = new ConnectHttpVital();
            postdata.MeasurementHistorySelect(MainActivity.this,mymail);
            ConnectHttpVital postdata1 = new ConnectHttpVital();
            postdata1.Totalvital(MainActivity.this,mymail);


            TextView nowflag1 = (TextView) findViewById(R.id.nowflag1);
            TextView nowflag2 = (TextView) findViewById(R.id.nowflag2);
            TextView nowflag3 = (TextView) findViewById(R.id.nowflag3);
            TextView nowflag4 = (TextView) findViewById(R.id.nowflag4);
            TextView nowflag5 = (TextView) findViewById(R.id.nowflag5);
            TextView nowflag6 = (TextView) findViewById(R.id.nowflag6);
            TextView nowflag7 = (TextView) findViewById(R.id.nowflag7);

            nowflag1.setText("今回");
            nowflag2.setText("今回");
            nowflag3.setText("今回");
            nowflag4.setText("今回");
            nowflag5.setText("今回");
            nowflag6.setText("今回");
            nowflag7.setText("今回");


        }


    };

    private long[] getFromToTime() {


        int yyyy, mm, dd, hh;
        long from = 0, to = 0;
        Calendar cal = null;

        TimeZone tz;
        if (mUseTimeZone) {
            tz = TimeZone.getDefault();
        } else {
            tz = TimeZone.getTimeZone("GMT");
        }
        cal = Calendar.getInstance(tz);

        long[] ret = {-1, -1};


        yyyy = 2000;
        mm = 01;
        dd = 01;
        hh = 00;
        //from




        cal.set(yyyy, mm - 1, dd, hh, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        from = cal.getTime().getTime();
        ret[0] = from;


        yyyy = 2020;
        mm = 12;
        dd = 31;
        hh = 23;
        //to
        cal.set(yyyy, mm - 1, dd, hh, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        to = cal.getTime().getTime();
        ret[1] = to;

        return ret;

    }

}
