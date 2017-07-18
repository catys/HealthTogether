package com.yamada.biton.healthtogether;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.io.FileDescriptor;
import java.io.IOException;

public class EntryActivity extends AppCompatActivity {

    private static final int RESULT_PICK_IMAGEFILE = 1000;
    String imgURL1 = "URL",mail1 = "mymail",pass1 = "mypass",nick1 = "mynick",height1="myheight",age1="myage";
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

        //ニックネーム
        EditText nick = (EditText) findViewById(R.id.nickTxt);
        SpannableStringBuilder sp4 = (SpannableStringBuilder)nick.getText();
        nick1 = sp4.toString();

        //性別
        RadioButton man = (RadioButton) findViewById(R.id.manRadioBtn);
        RadioButton woman = (RadioButton) findViewById(R.id.womanRadioBtn);
        if(man.isChecked() == true){
            // チェックされた状態の時の処理を記述
            //男
            sflg = 1;
        } else if (woman.isChecked() == true) {
            // チェックされていない状態の時の処理を記述
            //女
            sflg = 0;
        }

        //身長
        EditText height = (EditText) findViewById(R.id.heightTxt);
        SpannableStringBuilder sp5 = (SpannableStringBuilder)height.getText();
        height1 = sp5.toString();

        //年齢
        EditText age = (EditText) findViewById(R.id.ageTxt);
        SpannableStringBuilder sp6 = (SpannableStringBuilder)age.getText();
        age1 = sp6.toString();

        //監視者フラグ
        CheckBox monitor = (CheckBox) findViewById(R.id.monitorChck);
        if(monitor.isChecked() == true){
            // チェックされた状態の時の処理を記述
            mflg = 1;
        } else {
            // チェックされていない状態の時の処理を記述
            mflg = 0;
        }

        Intent intent = new Intent(EntryActivity.this, EntryConfirmActivity.class);
        intent.putExtra("maildata", mail1);
        intent.putExtra("passdata", pass1);
        intent.putExtra("nickdata", nick1);
        intent.putExtra("sexdata", sflg);
        intent.putExtra("heightdata", height1);
        intent.putExtra("agedata", age1);
        intent.putExtra("monitordata", mflg);
        startActivity(intent);

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
