package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;

public class UserActivity extends AppCompatActivity {

    String mail = "mymail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

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

        System.out.println(mail);

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.UserSelect(this,mail);

        //個人情報を表示する------------------------------------------------------------------------


    }

    public void EditClick (View view) {

        Intent intent = new Intent(UserActivity.this, UserEditActivity.class);
        startActivity(intent);

    }
}
