package com.yamada.biton.healthtogether;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpFriend;

import java.util.ArrayList;

/**
 * Created by 優太 on 2017/06/28.
 */

public class FriendActivity  extends AppCompatActivity {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;
    Global global = (Global)getApplication();

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        global = (Global)getApplication();

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

        //初期値を非表示
        textparam.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
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
        int flag = global.getFlag();
        if(flag == 0){
            //追加
            ConnectHttpFriend postdata = new ConnectHttpFriend();
            postdata.FriendAdd(this,mymail,friendmail);
            global.setFlag(1);
        }else{
            //削除
            ConnectHttpFriend postdata = new ConnectHttpFriend();
            postdata.FriendDelete(this,mymail,friendmail);
            global.setFlag(0);
        }
    }

    //フレンド情報共有設定
    public void FriendInfoShare(View v,String mymail,String fmail,String flag){
        ConnectHttpFriend postdata = new ConnectHttpFriend();

    }

    //フレンドスケジュール共有設定
    public void FriendScheduleShare(View v){

    }


}