package com.yamada.biton.healthtogether;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;

public class UserEditActivity extends AppCompatActivity {

    String mail = "mymail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // queryメソッドの実行例
        Cursor c = db.query("user", new String[]{"mail", "monitor"}, null,
                null, null, null, null);

        boolean mov = c.moveToFirst();
        if (mov) {
            mail = c.getString(0);
        }
        c.close();
        db.close();

        System.out.println(mail);

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.UserEditSelect(this, mail);

    }

    public void OKClick(View view) {

        //編集の値を取得

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
//        postdata.UpdateUser(this,$beforemail,$mail,$pass,$nick,$proURL,$sex,$height,$age,$bw,$bf,$bmi,$vf,$sm,$ba,$bm);

    }
}
