package com.yamada.biton.healthtogether;

import android.content.ContentValues;
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
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;
import com.yamada.biton.healthtogether.AsyncTasksPackage.PostBmpAsyncHttpRequest;

import java.io.FileDescriptor;
import java.io.IOException;

public class UserEditActivity extends AppCompatActivity {

    String proURL = "URL",mail = "mymail",pass = "mypass",repass = "mypass",nick = "mynick",beforemail = "beforemail";
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

        //phpへとデータを送信
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.MonitorEditSelect(this, mail);

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

        //プロフ画像
        proURL = "pro";

        //編集の値を取得
        //メール
        EditText mailTxt = (EditText) findViewById(R.id.editMailTxt);
        SpannableStringBuilder sp1 = (SpannableStringBuilder)mailTxt.getText();
        mail = sp1.toString();

        //パスワード
        EditText passTxt = (EditText) findViewById(R.id.editPassTxt);
        SpannableStringBuilder sp2 = (SpannableStringBuilder)passTxt.getText();
        pass = sp2.toString();

        //パスワード
        EditText passRetypeTxt = (EditText) findViewById(R.id.editPassRetypeTxt);
        SpannableStringBuilder sp3 = (SpannableStringBuilder)passRetypeTxt.getText();
        repass = sp3.toString();

        //ニックネーム
        EditText nickTxt = (EditText) findViewById(R.id.editNickTxt);
        SpannableStringBuilder sp4 = (SpannableStringBuilder)nickTxt.getText();
        nick = sp4.toString();

        //体組成情報
        //体重計
        Switch bwSwtch = (Switch)findViewById(R.id.bwSwitch);
        if(bwSwtch.isChecked()){
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
        if(bfSwtch.isChecked()){
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
        if(bmiSwtch.isChecked()){
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
        if(vfSwtch.isChecked()){
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
        if(bmSwtch.isChecked()){
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
        if(smSwtch.isChecked()){
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
        if(baSwtch.isChecked()){
            // チェックされた状態の時の処理を記述
            //公開する
            ba = "1";
        } else {
            // チェックされていない状態の時の処理を記述
            //公開しない
            ba = "0";
        }

        //phpへとデータを送信
        ConnectHttpUser postdata1 = new ConnectHttpUser();
        postdata1.UserEditCheck(this, mail);

        if (!mail.equals("") && !pass.equals("") && !repass.equals("") && !nick.equals("")) {

            TextView emptyTxt = (TextView) findViewById(R.id.emptyTxt);
            emptyTxt.setVisibility(View.GONE);

        }

        if (pass.equals(repass)) {

            TextView checkTxt = (TextView) findViewById(R.id.passCheckTxt);
            checkTxt.setVisibility(View.GONE);

        }


            if (mail.equals("") || pass.equals("") || repass.equals("") || nick.equals("")) {

            TextView emptyTxt = (TextView) findViewById(R.id.emptyTxt);
            emptyTxt.setVisibility(View.VISIBLE);

        } else if (!pass.equals(repass)) {

            TextView checkTxt = (TextView) findViewById(R.id.passCheckTxt);
            checkTxt.setVisibility(View.VISIBLE);

        }else {

            //SQLite
            MyOpenHelper helper = new MyOpenHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            // queryメソッドの実行例
            Cursor c = db.query("user", new String[]{"mail", "monitor"}, null,
                    null, null, null, null);

            boolean mov = c.moveToFirst();
            if (mov) {
                beforemail = c.getString(0);
            }
            c.close();

            if (!beforemail.equals(mail)){
                //プロフィール画像
                new PostBmpAsyncHttpRequest(this).execute(new Param("http://54.92.74.113/dataupload.php?mymail=" + mail, bmp));
                proURL = "pro";//"http://54.92.74.113/prof/" + mail + ".jpg";

            }

            //phpへとデータを送信
            ConnectHttpUser postdata2 = new ConnectHttpUser();
            postdata2.UserUpdate(this, beforemail, mail, pass, nick, proURL, bw, bf, bmi, vf, sm, ba, bm);

            //SQLite更新
            ContentValues updateValues = new ContentValues();
            updateValues.put("mail", mail);
            try {
                db.update("user", updateValues, "mail=?", new String[]{beforemail});
            } finally {
                db.close();
            }

            //個人情報表示画面へ遷移
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        }
    }

    //画像選択後
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                try {
                    ImageButton imgbtn = (ImageButton)findViewById(R.id.editImgBtn);
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
