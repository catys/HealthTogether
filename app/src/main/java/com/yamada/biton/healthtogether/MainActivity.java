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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpVital;
import com.yamada.biton.healthtogether.AsyncTasksPackage.httpconnection;

/**
 * Created by 優太 on 2017/06/28.
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.menu_schedule:
                Intent intent3 = new Intent(this, ScheduleActivity.class);
                startActivity(intent3);
                break;
            case R.id.menu_friend:
                Intent intent4 = new Intent(this, FriendActivity.class);
                startActivity(intent4);
                break;
            case R.id.menu_ranking:
                //Intent intent5 = new Intent(this, Activity.class);
                //startActivity(intent5);
                break;
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

}
