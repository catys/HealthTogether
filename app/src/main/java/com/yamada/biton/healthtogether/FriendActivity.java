package com.yamada.biton.healthtogether;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpFriend;

/**
 * Created by 優太 on 2017/06/28.
 */

public class FriendActivity  extends AppCompatActivity {
    //ユーザーのメールアドレスを設定
    String mymail = "testmail1",friendmail;
    Global global;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        //global = (Global)getApplication();

        TextView textparam = (TextView)findViewById(R.id.friendText);
        ImageButton button2 = (ImageButton)findViewById(R.id.friendGetButton);

        //初期値を非表示
        textparam.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
    }

    public void FriendSearch(View v){

        //EditText内に入力された値を取得
        EditText edit = (EditText)findViewById(R.id.mailText);
        SpannableStringBuilder sp = (SpannableStringBuilder)edit.getText();
        friendmail = sp.toString();

        //phpへとデータを送信
        ConnectHttpFriend postdata = new ConnectHttpFriend();
        postdata.FriendSelect(this,mymail,friendmail);

    }
}