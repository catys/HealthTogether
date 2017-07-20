package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpFriend;

import java.util.ArrayList;

/**
 * Created by 優太 on 2017/06/28.
 */

public class FriendActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ListView listView = (ListView)findViewById(R.id.listView);

        ArrayList<FriendInfo> list = new ArrayList<>();
        FriendAdapter adapter = new FriendAdapter(FriendActivity.this);
        adapter.setFriendList(list);
        listView.setAdapter(adapter);

        //フレンド全件検索
        ConnectHttpFriend postdata = new ConnectHttpFriend();
        postdata.FriendDisplay(this,mymail,listView,list,adapter);

        TextView textparam = (TextView)findViewById(R.id.friendText);
        ImageButton button2 = (ImageButton)findViewById(R.id.friendGetButton);
        TextView restex = (TextView)findViewById(R.id.resultText);
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);

        //初期値を非表示
        textparam.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        restex.setVisibility(View.GONE);
        ll.setVisibility(View.GONE);

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

    }

    //リロード
    public void Reload(View v) {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    //フレンド検索
    public void FriendSearch(View v){
        //EditText内に入力された値を取得
        EditText edit = (EditText)findViewById(R.id.mailText);
        SpannableStringBuilder sp = (SpannableStringBuilder)edit.getText();
        friendmail = sp.toString();

        //phpへとデータを送信
        ConnectHttpFriend postdata = new ConnectHttpFriend();
        postdata.FriendSelect(this,mymail,friendmail);
    }

    //フレンドの追加、解除
    public void FriendAdd(View v){
        //Global変数のflagを確認し、友達かどうか判定
        int flag = Global.getFlag();
        if(flag == 0){
            //追加
            ConnectHttpFriend postdata = new ConnectHttpFriend();
            postdata.FriendAdd(this,mymail,friendmail);
            Global.setFlag(1);
        }else{
            //削除
            ConnectHttpFriend postdata = new ConnectHttpFriend();
            postdata.FriendDelete(this,mymail,friendmail);
            Global.setFlag(0);
        }
    }

    //フレンドの追加、解除//listview用
    public void FriendAdd2(View v){
        //Global変数のflagを確認し、友達かどうか判定
        String fmail = (String)((ImageButton)v).getTag();

        String hantei = Global.getFriendmap(fmail);

        if(hantei.equals("0")){
            //追加
            ConnectHttpFriend postdata = new ConnectHttpFriend();
            postdata.FriendAdd2(this,mymail,fmail,v);
            Global.setMapFlag(fmail,"1");
        }else{
            //削除
            ConnectHttpFriend postdata = new ConnectHttpFriend();
            postdata.FriendDelete2(this,mymail,fmail,v);
            Global.setMapFlag(fmail,"0");
        }
    }

    //フレンド情報共有設定
    public void FriendInfoShare(View v){
        ConnectHttpFriend postdata = new ConnectHttpFriend();
        postdata.FriendReleaseUpdate(this,mymail,(String)((ImageButton)v).getTag(),v);
    }

    //フレンドスケジュール共有設定
    public void FriendScheduleShare(View v){
        ConnectHttpFriend postdata = new ConnectHttpFriend();
        postdata.FriendScheduleUpdate(this,mymail,(String)((ImageButton)v).getTag(),v);
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
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}