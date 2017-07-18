package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

//aiueotest

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void StartClick(View v){

        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);


/*登録画面のやつ
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        // queryメソッドの実行例
        Cursor c = db.query("user", new String[] { "mail" , "monitor"}, null,
                null, null, null, null);

        boolean mov = c.moveToFirst();

        //内部DBなかったら登録画面へ遷移
        if(mov == false){
            Intent intent = new Intent(MainActivity.this, EntryActivity.class);
            startActivity(intent);
        }

        //内部DBがあってユーザか監視者かで遷移
        if(mov){
            //測定結果画面遷移
            if (c.getInt(1) == 0) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            } else if(c.getInt(1) == 1) {
                //監視画面遷移
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        }

        //DB切断
        c.close();
        db.close();
        */

    }

    //内部DB削除
    public void DeleteClick (View view) {
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        db.delete("user", null, null);
    }

    public void getClick(View v){
        Intent intent = new Intent(StartActivity.this, getvital.class);
        startActivity(intent);
    }
}
