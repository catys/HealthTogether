package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;

import java.io.FileDescriptor;
import java.io.IOException;

public class UserEditActivity extends AppCompatActivity {

    String proURL = "URL",mail = "mymail",pass = "mypass",nick = "mynick",height = "myheight",beforemail = "beforemail";
    String bw = "",bf = "",bmi = "",vf = "",sm = "",ba = "",bm = "";
    private static final int RESULT_PICK_IMAGEFILE = 1000;
    static public Bitmap bmp =null;

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

    //プロフ画像クリック
    public void imgClick (View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
    }

    //編集ボタンクリック
    public void OKClick(View view) {

        //編集の値を取得
        //メール
        EditText mailTxt = (EditText) findViewById(R.id.mailTxt);
        SpannableStringBuilder sp2 = (SpannableStringBuilder)mailTxt.getText();
        mail = sp2.toString();

        //パスワード
        EditText passTxt = (EditText) findViewById(R.id.passTxt);
        SpannableStringBuilder sp3 = (SpannableStringBuilder)passTxt.getText();
        pass = sp3.toString();

        //ニックネーム
        EditText nickTxt = (EditText) findViewById(R.id.nickTxt);
        SpannableStringBuilder sp4 = (SpannableStringBuilder)nickTxt.getText();
        nick = sp4.toString();

        //画像

        //身長
        EditText heightTxt = (EditText) findViewById(R.id.heightTxt);
        SpannableStringBuilder sp5 = (SpannableStringBuilder)heightTxt.getText();
        height = sp5.toString();

        //体組成情報
        //体重計
        Switch bwSwtch = (Switch)findViewById(R.id.bwSwitch);
        if(bwSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            bw = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            bw = "0";
        }

        //体脂肪
        Switch bfSwtch = (Switch)findViewById(R.id.bfSwitch);
        if(bfSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            bf = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            bf = "0";
        }

        //BMI
        Switch bmiSwtch = (Switch)findViewById(R.id.bmiSwitch);
        if(bmiSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            bmi = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            bmi = "0";
        }

        //内脂肪レベル
        Switch vfSwtch = (Switch)findViewById(R.id.vfSwitch);
        if(vfSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            vf = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            vf = "0";
        }

        //基礎代謝
        Switch bmSwtch = (Switch)findViewById(R.id.bmSwitch);
        if(bmSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            bm = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            bm = "0";
        }

        //骨格筋率
        Switch smSwtch = (Switch)findViewById(R.id.smSwitch);
        if(smSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            sm = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            sm = "0";
        }

        //体年齢
        Switch baSwtch = (Switch)findViewById(R.id.baSwitch);
        if(baSwtch.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //公開する
            ba = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            ba = "0";
        }



        //SQLiteのdatabaseに格納
        MyOpenHelper helper = new MyOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        // queryメソッドの実行例
        Cursor c = db.query("user", new String[] { "mail", "monitor" }, null,
                null, null, null, null);

        boolean mov = c.moveToFirst();
        if (mov) {
            beforemail = c.getString(0);
        }
        c.close();
        db.close();

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.UserUpdate(this,beforemail,mail,pass,nick,proURL,bw,bf,bmi,vf,sm,ba,bm);

    }

    //画像選択後
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                try {
                    ImageButton imgbtn = (ImageButton)findViewById(R.id.imgBtn);
                    bmp = getBitmapFromUri(uri);
                    imgbtn.setImageBitmap(bmp);
                    Global global = new Global();
                    global.setbmp(bmp);

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }


}
