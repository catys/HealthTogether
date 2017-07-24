package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpUser;

import java.io.FileDescriptor;
import java.io.IOException;

public class EntryActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1000;
    String imgURL1 = "URL",mail1 = "mymail",pass1 = "mypass",repass1 = "mypass",nick1 = "mynick",height1="myheight",age1="myage";
    int mflg,sflg;
    static public Bitmap bmp =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    //プロフ画像クリック
    public void imgClick (View view) {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
    }

    //登録確認ボタンクリック
    public void ConfirmClick (View view) {

        //メール
        EditText mail = (EditText) findViewById(R.id.mailTxt);
        SpannableStringBuilder sp2 = (SpannableStringBuilder)mail.getText();
        mail1 = sp2.toString();

        //パスワード
        EditText pass = (EditText) findViewById(R.id.passTxt);
        SpannableStringBuilder sp3 = (SpannableStringBuilder)pass.getText();
        pass1 = sp3.toString();

        //パスワード
        EditText repass = (EditText) findViewById(R.id.passRetypeTxt);
        SpannableStringBuilder sp4 = (SpannableStringBuilder)repass.getText();
        repass1 = sp4.toString();

        //ニックネーム
        EditText nick = (EditText) findViewById(R.id.nickTxt);
        SpannableStringBuilder sp5 = (SpannableStringBuilder)nick.getText();
        nick1 = sp5.toString();

        //監視者フラグ
        CheckBox monitor = (CheckBox) findViewById(R.id.monitorChck);
        if(monitor.isChecked()){
            // チェックされた状態の時の処理を記述
            mflg = 1;
        } else {
            // チェックされていない状態の時の処理を記述
            mflg = 0;
        }

        //未入力処理
        if (!mail1.equals("") && !pass1.equals("") && !repass1.equals("") && !nick1.equals("")) {

            TextView emptyTxt = (TextView) findViewById(R.id.emptyTxt);
            emptyTxt.setVisibility(View.GONE);
        }

        //パス一致処理
        if (pass1.equals(repass1)) {

            TextView checkTxt = (TextView) findViewById(R.id.passCheckTxt);
            checkTxt.setVisibility(View.GONE);
        }

            //phpへとデータを送信
        //ユーザ重複チェック
        ConnectHttpUser postdata = new ConnectHttpUser();
        postdata.UserCheck(this, mail1);

        if (mail1.equals("") || pass1.equals("") || repass1.equals("") || nick1.equals("")) {

            TextView emptyTxt = (TextView) findViewById(R.id.emptyTxt);
            emptyTxt.setVisibility(View.VISIBLE);

        } else if (!pass1.equals(repass1)) {

            TextView checkTxt = (TextView) findViewById(R.id.passCheckTxt);
            checkTxt.setVisibility(View.VISIBLE);

        }else {
            Intent intent = new Intent(EntryActivity.this, EntryConfirmActivity.class);
            intent.putExtra("maildata", mail1);
            intent.putExtra("passdata", pass1);
            intent.putExtra("nickdata", nick1);
            intent.putExtra("monitordata", mflg);
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
                    ImageButton imgbtn = (ImageButton)findViewById(R.id.imgBtn);
                    bmp = getBitmapFromUri(uri);
                    imgbtn.setImageBitmap(bmp);
                    Global global = new Global();
                    global.setbmp(bmp);

                    // bitmapの画像を200×90で作成する
                    //Bitmap bitmap2 = Bitmap.createScaledBitmap(bmp, 400, 500, false);

                    //imgbtn.setImageBitmap(bitmap2);

                    //imgbtn.setScaleType(ImageButton.ScaleType.CENTER_CROP);

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
