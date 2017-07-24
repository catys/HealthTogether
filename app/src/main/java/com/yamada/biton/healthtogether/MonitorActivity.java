package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;

public class MonitorActivity extends AppCompatActivity {

    String mail = "mymail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerToggle
        DrawerLayout drawer =
                (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView Listener
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        //navigationView.setNavigationItemSelectedListener(this);

        //SQLite
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // queryメソッドの実行例
        Cursor c = db.query("user", new String[] { "mail", "monitor" }, null,
                null, null, null, null);

        boolean mov = c.moveToFirst();
        if (mov) {
            mail = c.getString(0);
        }
        c.close();
        db.close();

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.MonitorSelect(this,mail);

        //個人情報を表示する------------------------------------------------------------------------
    }

    public void EditClick (View view) {

        Intent intent = new Intent(MonitorActivity.this, UserEditActivity.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                break;
        }
        return true;
    }

    //@Override
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
        /*    case R.id.menu_schedule:
                Intent intent3 = new Intent(this, ScheduleActivity.class);
                startActivity(intent3);
                break;*/
            case R.id.menu_friend:
                Intent intent4 = new Intent(this, FriendActivity.class);
                startActivity(intent4);
                break;
        /*    case R.id.menu_ranking:
                //Intent intent5 = new Intent(this, Activity.class);
                //startActivity(intent5);
                break;*/
            case R.id.menu_user:
                Intent intent6 = new Intent(this, UserActivity.class);
                startActivity(intent6);
                break;
            case R.id.menu_contact:
                //Intent intent7 = new Intent(this, UserActivity.class);
                //startActivity(intent7);
                //break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
