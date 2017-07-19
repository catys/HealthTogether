package com.yamada.biton.healthtogether.AsyncTasksPackage;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.yamada.biton.healthtogether.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;




/**
 * Created by 優太 on 2017/06/28.
 */

public class httpconnection extends Activity {
    private static final String TAG = httpconnection.class.getSimpleName();
    private AsyncTask<Uri, Void, String> mTask;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void sendText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mTextView.append(text);
            }
        });
    }

    public void ResultDisplay(Activity activity, String mymail) {
        mActivity = activity;

        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/kame/ResultDisplay.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("mymail", mymail)
                .build();

        if (mTask == null) {
            mTask = new AsyncTask<Uri, Void, String>() {
                /**
                 * 通信において発生したエラー
                 */
                private Throwable mError = null;

                @Override
                protected String doInBackground(Uri... params) {
                    Uri uri = params[0];
                    sendText("\n通信開始\n");

                    String result = request(uri);

                    return result;
                }

                private String request(Uri uri) {
                    java.net.HttpURLConnection http = null;
                    InputStream is = null;
                    String result = null;
                    try {
                        // URLにHTTP接続
                        URL url = new URL(uri.toString());
                        http = (java.net.HttpURLConnection) url.openConnection();

                        http.setConnectTimeout(3000);//接続タイムアウトを設定する。
                        http.setReadTimeout(3000);//レスポンスデータ読み取りタイムアウトを設定する。

                        http.setRequestMethod("POST");
                        http.setDoOutput(true);// POSTによるデータ送信を可能
                        http.setRequestProperty("User-Agent", "@IT java-tips URLConnection");// ヘッダを設定
                        http.setRequestProperty("Accept-Language", "ja");// ヘッダを設定

                        http.setDoInput(true);//リクエストのボディ送信を許可する
                        http.setDoOutput(true);//レスポンスのボディ受信を許可する

                        OutputStream os = http.getOutputStream();//POST用のOutputStreamを取得

                        String postStr = "name=";//POSTするデータ
                        PrintStream ps = new PrintStream(os);
                        ps.print(postStr);//データをPOSTする
                        ps.close();
                        http.connect();
                        is = http.getInputStream();

                        result = toString(is);
                        sendText(result);
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "通信失敗", e);
                        sendText(e.toString());
                    } catch (IOException e) {
                        Log.e(TAG, "通信失敗", e);
                        sendText(e.toString());
                    } finally {
                        if (http != null) {
                            http.disconnect();
                        }
                        try {
                            if (is != null) {
                                is.close();
                            }
                        } catch (IOException e) {
                            Log.e(TAG, "ストリームのクローズ失敗", e);
                        }
                    }
                    return result;
                }

                private String toString(InputStream is) throws IOException {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is, "UTF-8"));
                    StringBuilder sb = new StringBuilder();
                    char[] b = new char[1024];
                    int line;
                    while (0 <= (line = reader.read(b))) {
                        sb.append(b, 0, line);
                    }
                    return sb.toString();
                }

                @Override
                protected void onPostExecute(String result) {
                    try {
                        JSONObject json = new JSONObject(result);
                        JSONObject item = json.getJSONObject("data");
                        System.out.println("返ってきてる");



                        TextView weight = (TextView) mActivity.findViewById(R.id.weight);
                        TextView basalmetabolism = (TextView) mActivity.findViewById(R.id.basalmetabolism);
                        TextView tbasalmetabolism = (TextView) mActivity.findViewById(R.id.tbasalmetabolism);

                        TextView bmi = (TextView) mActivity.findViewById(R.id.bmi);
                        TextView tbmi = (TextView) mActivity.findViewById(R.id.tbmi);
                        TextView vfat = (TextView) mActivity.findViewById(R.id.vfat);
                        TextView tvfat = (TextView) mActivity.findViewById(R.id.tvfat);
                        TextView sm = (TextView) mActivity.findViewById(R.id.sm);
                        TextView tsm = (TextView) mActivity.findViewById(R.id.tsm);
                        TextView bodyage = (TextView) mActivity.findViewById(R.id.bodyage);
                        TextView tbodyage = (TextView) mActivity.findViewById(R.id.tbodyage);
                        TextView bodyfat = (TextView) mActivity.findViewById(R.id.bodyfat);
                        TextView tbodyfat = (TextView) mActivity.findViewById(R.id.tbodyfat);


                        //体脂肪適性判定
                        if(item.getString("sex").equals("1")){
                            //男性の場合
                            float bf = Float.parseFloat(item.getString("bodyfat"));
                            bf = bf/10;
                            if(bf < 10.0){
                                tbodyfat.setText("低い");
                            }else if(bf >= 10.0 && bf < 20.0 ){
                                tbodyfat.setText("標準");
                            }else if(bf >= 20.0 && bf < 25.0){
                                tbodyfat.setText("やや高い");
                            }else if(bf >= 25.0){
                                tbodyfat.setText("高い");
                            }
                        }else{
                            //女性の場合
                            float bf = Float.parseFloat(item.getString("bodyfat"));
                            bf = bf/10;
                            if(bf < 20.0){
                                tbodyfat.setText("低い");
                            }else if(bf >= 20.0 && bf < 30.0 ){
                                tbodyfat.setText("標準");
                            }else if(bf >= 30.0 && bf < 35.0){
                                tbodyfat.setText("やや高い");
                            }else if(bf >= 35.0){
                                tbodyfat.setText("高い");
                            }
                        }

                        //bmi適性
                        float bmidata = Float.parseFloat(item.getString("bmi"));
                        bmidata = bmidata/10;
                        if(bmidata < 18.5){
                            tbmi.setText("痩せすぎ");
                        }else if(bmidata > 25.0){
                            tbmi.setText("肥満");
                        }else{
                            tbmi.setText("標準");
                        }

                        //内臓脂肪レベル
                        float vfatdata = Float.parseFloat(item.getString("visceralfat"));
                        if(vfatdata < 10){
                            tvfat.setText("標準");
                        }else if(vfatdata > 14){
                            tvfat.setText("高い");
                        }else{
                            tvfat.setText("やや高い");
                        }

                        //骨格筋率
                        if(item.getString("sex").equals("1")){
                            //男性の場合
                            float smdata = Float.parseFloat(item.getString("skeletalmuscle"));
                            smdata = smdata/10;
                            if(smdata < 32.9){
                                tsm.setText("低い");
                            }else if(smdata >= 32.9 && smdata < 35.8 ){
                                tsm.setText("標準");
                            }else if(smdata >= 35.8 && smdata < 37.4){
                                tsm.setText("やや高い");
                            }else if(smdata >= 37.4){
                                tsm.setText("高い");
                            }
                        }else{
                            //女性の場合
                            float smdata = Float.parseFloat(item.getString("skeletalmuscle"));
                            smdata = smdata/10;
                            if(smdata < 25.9){
                                tsm.setText("低い");
                            }else if(smdata >= 25.9 && smdata < 28.0 ){
                                tsm.setText("標準");
                            }else if(smdata >= 28.0 && smdata < 21.9){
                                tsm.setText("やや高い");
                            }else if(smdata >= 21.9) {
                                tsm.setText("高い");
                            }
                        }

                        float agedata = Float.parseFloat(item.getString("age"));
                        float byagedata = Float.parseFloat(item.getString("bodyage"));
                        if(agedata >= byagedata){
                            tbodyage.setText("適性");
                        }else{
                            tbodyage.setText("老");
                        }

                        //基礎代謝
                        if(agedata >= 18 && agedata <= 29){
                            int bmdata = Integer.parseInt(item.getString("basalmetabolism"));
                            if(bmdata < 1110){
                                tbasalmetabolism.setText("低燃費");
                            }else{
                                tbasalmetabolism.setText("高燃費");
                            }
                        }
                            //体重表示
                            float bw = Float.parseFloat(item.getString("bodyweight"));
                            bw = bw/100;
                            weight.setText(String.valueOf(bw)+"\n      kg");

                            //体脂肪表示
                            float bf = Float.parseFloat(item.getString("bodyfat"));
                            bf = bf/10;
                            bodyfat.setText(String.valueOf(bf)+"\n      %");

                            //内臓脂肪表示
                            int vf = Integer.parseInt(item.getString("visceralfat"));
                            vfat.setText(String.valueOf(vf)+"\n    level");

                            //基礎代謝表示
                            basalmetabolism.setText(item.getString("basalmetabolism")+"\n    kcal");

                            //骨格筋率表示
                        float sfat = Float.parseFloat(item.getString("subcutaneousfat"));
                        sfat = sfat/10;
                        sm.setText(String.valueOf(sfat)+"\n       %");

                            //BMI表示
                        float fbmi = Float.parseFloat(item.getString("bmi"));
                        fbmi = fbmi/10;
                        bmi.setText(String.valueOf(fbmi));

                            //体年齢表示
                            bodyage.setText(item.getString("bodyage")+"\n       才");


                        //日付表示
                        TextView date = (TextView) mActivity.findViewById(R.id.date);
                        TextView date2 = (TextView) mActivity.findViewById(R.id.date2);
                        TextView date3 = (TextView) mActivity.findViewById(R.id.date3);
                        TextView date4 = (TextView) mActivity.findViewById(R.id.date4);
                        TextView date5 = (TextView) mActivity.findViewById(R.id.date5);
                        TextView date6 = (TextView) mActivity.findViewById(R.id.date6);
                        TextView date1 = (TextView) mActivity.findViewById(R.id.date1);

                        date.setText(item.getString("date"));
                        date1.setText(item.getString("date"));
                        date2.setText(item.getString("date"));
                        date3.setText(item.getString("date"));
                        date4.setText(item.getString("date"));
                        date5.setText(item.getString("date"));
                        date6.setText(item.getString("date"));



                        mTask = null;
                    } catch (Exception e) {
                    }

                }

                @Override
                protected void onCancelled() {
                    onCancelled(null);
                }

                @Override
                protected void onCancelled(String result) {
                    sendText("\nonCancelled(String result), result=" + result);

                    mTask = null;
                }
            }.execute(uri);
        } else {
            // 現在通信のタスクが実行中。重複して実行されないように制御。
        }
    }

}

