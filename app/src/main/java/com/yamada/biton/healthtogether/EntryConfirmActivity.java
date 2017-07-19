package com.yamada.biton.healthtogether;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;
import com.yamada.biton.healthtogether.AsyncTasksPackage.PostBmpAsyncHttpRequest;

public class EntryConfirmActivity extends AppCompatActivity {

    String proURL = "URL",mail = "mymail",pass = "mypass",nick = "mynick",sflg = "mysex",height = "myheight",age = "myage",mflg = "mymonitor";

    String setMail = "mail",setPass = "pass",setNick = "nick",setHeight = "height",setAge = "age";
    int setMonitor,setSex;
    Global global = new Global();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_confirm);

        Intent intent = getIntent();

        //プロフィール画像
        ImageView imgView = (ImageView) findViewById(R.id.entryImg);
        //画像セット
        Bitmap bmp = global.getbmp();

        imgView.setImageBitmap(bmp);

        //メールアドレス
        //id取得
        TextView mailText = (TextView)findViewById(R.id.entryMailTxt);
        //テキストセット
        setMail = intent.getStringExtra("maildata");
        mailText.setText(String.valueOf(setMail));

        //パスワード
        //id取得
        TextView passText = (TextView)findViewById(R.id.entryPassTxt);
        //テキストセット
        setPass = intent.getStringExtra("passdata");
        passText.setText(String.valueOf(setPass));

        //ニックネーム
        //id取得
        TextView nickText = (TextView)findViewById(R.id.entryNickTxt);
        //テキストセット
        setNick = intent.getStringExtra("nickdata");
        nickText.setText(String.valueOf(setNick));

 /*       //性別フラグ
        //id取得
        //TextView sexText = (TextView)findViewById(R.id.sexTxt);
        //数値セット
        //setSex = intent.getIntExtra("sexdata",0);
        //if (setSex == 1) {
        //    sexText.setText("男");
        //}else if(setSex == 0) {
        //    sexText.setText("女");
        //}

        //身長
        //id取得
        //TextView heightText = (TextView)findViewById(R.id.entryHeightTxt);
        //テキストセット
        //setHeight = intent.getStringExtra("heightdata");
        //heightText.setText(String.valueOf(setHeight));

        //年齢
        //id取得
        //TextView ageText = (TextView)findViewById(R.id.entryAgeTxt);
        //テキストセット
        //setAge = intent.getStringExtra("agedata");
        //ageText.setText(String.valueOf(setAge));

        //監視者フラグ
        //id取得
        TextView monitorText = (TextView)findViewById(R.id.monitorTxt);
        //数値セット
        setMonitor = intent.getIntExtra("monitordata",0);
        if (setMonitor == 1) {
            monitorText.setText("監視者として登録する");
        }else if(setMonitor == 0) {
            monitorText.setText("監視者として登録しない");
        }*/

    }

    //登録ボタンクリック
    public void entryClick (View view) {
        Intent dataintent = getIntent();
        Bitmap bmp = global.getbmp();

        //メールアドレス
        mail = dataintent.getStringExtra("maildata");

        //パスワード
        pass = dataintent.getStringExtra("passdata");

        //ニックネーム
        nick = dataintent.getStringExtra("nickdata");

        //プロフィール画像
        new PostBmpAsyncHttpRequest(this).execute(new Param("http://54.92.74.113/dataupload.php?mymail=" + mail,bmp));
        proURL = "http://54.92.74.113/prof/" + mail + ".jpg";

        //監視者フラグ
        sflg = String.valueOf(dataintent.getIntExtra("sexdata",0));

        //身長
        height = dataintent.getStringExtra("heightdata");

        //年齢
        age = dataintent.getStringExtra("agedata");

        //監視者フラグ
        mflg = String.valueOf(dataintent.getIntExtra("monitordata",0));

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.UserInsert(this,mail,pass,nick,proURL,sflg,height,age,mflg);

        //SQLiteのdatabaseに格納
        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        //データセット
        ContentValues insertValues = new ContentValues();
        insertValues.put("mail",mail);
        insertValues.put("monitor",mflg);
        long id  = db.insert("user",mail,insertValues);

        Intent intent = new Intent(EntryConfirmActivity.this, UserActivity.class);
        startActivity(intent);

    }
}
