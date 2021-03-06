package com.yamada.biton.healthtogether.AsyncTasksPackage;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.yamada.biton.healthtogether.Global;
import com.yamada.biton.healthtogether.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by 優太 on 2017/07/07.
 */

public class ConnectHttpSchedule extends Activity {
    private static final String TAG = ConnectHttpFriend.class.getSimpleName();
    private AsyncTask<Uri, Void, String> mTask;
    private Activity mActivity;

    private int setyear;
    private int setmonth;
    private int setday;

    private Spinner spinner;
    private TextView textView;

    private List<String> fl = new ArrayList<String>();

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

    public void ScheduleSet(Activity activity,String mymail) {
        mActivity = activity;

        setyear = Global.getYear();
        setmonth = Global.getMonth();
        setday = Global.getDay();

        Uri baseUri = Uri
                .parse("http://54.92.74.113/sue/ScheduleSet.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("mymail",mymail)
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

                private String request(Uri uri ) {
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
                        CheckBox cb1 = (CheckBox) mActivity.findViewById(R.id.checkBox1);
                        CheckBox cb11 = (CheckBox) mActivity.findViewById(R.id.checkBox11);
                        CheckBox cb111 = (CheckBox) mActivity.findViewById(R.id.checkBox111);
                        CheckBox cb2 = (CheckBox) mActivity.findViewById(R.id.checkBox2);
                        CheckBox cb22 = (CheckBox) mActivity.findViewById(R.id.checkBox22);
                        CheckBox cb222 = (CheckBox) mActivity.findViewById(R.id.checkBox222);
                        CheckBox cb3 = (CheckBox) mActivity.findViewById(R.id.checkBox3);
                        CheckBox cb33 = (CheckBox) mActivity.findViewById(R.id.checkBox33);
                        CheckBox cb333 = (CheckBox) mActivity.findViewById(R.id.checkBox333);
                        CheckBox cb4 = (CheckBox) mActivity.findViewById(R.id.checkBox4);
                        CheckBox cb44 = (CheckBox) mActivity.findViewById(R.id.checkBox44);
                        CheckBox cb444 = (CheckBox) mActivity.findViewById(R.id.checkBox444);
                        CheckBox cb5 = (CheckBox) mActivity.findViewById(R.id.checkBox5);
                        CheckBox cb55 = (CheckBox) mActivity.findViewById(R.id.checkBox55);
                        CheckBox cb555 = (CheckBox) mActivity.findViewById(R.id.checkBox555);
                        CheckBox cb6 = (CheckBox) mActivity.findViewById(R.id.checkBox6);
                        CheckBox cb66 = (CheckBox) mActivity.findViewById(R.id.checkBox66);
                        CheckBox cb666 = (CheckBox) mActivity.findViewById(R.id.checkBox666);
                        CheckBox cb7 = (CheckBox) mActivity.findViewById(R.id.checkBox7);
                        CheckBox cb77 = (CheckBox) mActivity.findViewById(R.id.checkBox77);
                        CheckBox cb777 = (CheckBox) mActivity.findViewById(R.id.checkBox777);

                        JSONObject json = new JSONObject(result);
                        JSONArray datas = json.getJSONArray("data");

                        JSONObject item = datas.getJSONObject(0);

                        String flag = item.getString("flag");

                        if(flag.equals("登録済み")){
                            for (int i = 0; i <= datas.length(); i++) {
                                JSONObject item2 = datas.getJSONObject(i);

                                // スケジュールを取得する
                                String date = item2.getString("date");
                                String morningflag = item2.getString("morningflag");
                                String noonflag = item2.getString("noonflag");
                                String nightflag = item2.getString("nightflag");

                                //スケジュールが登録されており、かつ表示される場合
                                int checkdate = Integer.parseInt(date.replace("-",""));
                                int checkyear = checkdate/10000;
                                int checkmonth = (checkdate - checkyear * 10000) / 100;
                                int checkday = checkdate - checkyear * 10000 - checkmonth * 100;

                                if(checkyear == setyear){
                                    if(checkmonth == setmonth) {
                                        //比較引いた値が1～7の間なら表示
                                        int checkid = checkday - setday + 1;

                                        if (1 <= checkid && 7 >= checkid ) {
                                            switch (checkid) {
                                                case 1:
                                                    if(morningflag.equals("1")){
                                                        cb1.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb11.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb111.setChecked(true);
                                                    }
                                                    break;
                                                case 2:
                                                    if(morningflag.equals("1")){
                                                        cb2.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb22.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb222.setChecked(true);
                                                    }
                                                    break;
                                                case 3:
                                                    if(morningflag.equals("1")){
                                                        cb3.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb33.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb333.setChecked(true);
                                                    }
                                                    break;
                                                case 4:
                                                    if(morningflag.equals("1")){
                                                        cb4.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb44.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb444.setChecked(true);
                                                    }
                                                    break;
                                                case 5:
                                                    if(morningflag.equals("1")){
                                                        cb5.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb55.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb555.setChecked(true);
                                                    }
                                                    break;
                                                case 6:
                                                    if(morningflag.equals("1")){
                                                        cb6.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb66.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb666.setChecked(true);
                                                    }
                                                    break;
                                                case 7:
                                                    if(morningflag.equals("1")){
                                                        cb7.setChecked(true);
                                                    }
                                                    if(noonflag.equals("1")){
                                                        cb77.setChecked(true);
                                                    }
                                                    if(nightflag.equals("1")){
                                                        cb777.setChecked(true);
                                                    }
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                }

                            }
                        }
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

    public void ScheduleInsert(Activity activity,String mymail) {
        mActivity = activity;

        int max = Global.getScheduleinfoSize();

        Uri baseUri = Uri
                .parse("http://54.92.74.113/sue/ScheduleAdd.php");

        Uri uri = baseUri.buildUpon()
                    .appendQueryParameter("mymail",mymail)
                    .appendQueryParameter("max",String.valueOf(max))
                    .appendQueryParameter("d0",Global.getScheduleinfo().get(0).get(0))
                    .appendQueryParameter("m0",Global.getScheduleinfo().get(0).get(1))
                    .appendQueryParameter("n0",Global.getScheduleinfo().get(0).get(2))
                    .appendQueryParameter("ni0",Global.getScheduleinfo().get(0).get(3))
                    .appendQueryParameter("d1",Global.getScheduleinfo().get(1).get(0))
                    .appendQueryParameter("m1",Global.getScheduleinfo().get(1).get(1))
                    .appendQueryParameter("n1",Global.getScheduleinfo().get(1).get(2))
                    .appendQueryParameter("ni1",Global.getScheduleinfo().get(1).get(3))
                    .appendQueryParameter("d2",Global.getScheduleinfo().get(2).get(0))
                    .appendQueryParameter("m2",Global.getScheduleinfo().get(2).get(1))
                    .appendQueryParameter("n2",Global.getScheduleinfo().get(2).get(2))
                    .appendQueryParameter("ni2",Global.getScheduleinfo().get(2).get(3))
                    .appendQueryParameter("d3",Global.getScheduleinfo().get(3).get(0))
                    .appendQueryParameter("m3",Global.getScheduleinfo().get(3).get(1))
                    .appendQueryParameter("n3",Global.getScheduleinfo().get(3).get(2))
                    .appendQueryParameter("ni3",Global.getScheduleinfo().get(3).get(3))
                    .appendQueryParameter("d4",Global.getScheduleinfo().get(4).get(0))
                    .appendQueryParameter("m4",Global.getScheduleinfo().get(4).get(1))
                    .appendQueryParameter("n4",Global.getScheduleinfo().get(4).get(2))
                    .appendQueryParameter("ni4",Global.getScheduleinfo().get(4).get(3))
                    .appendQueryParameter("d5",Global.getScheduleinfo().get(5).get(0))
                    .appendQueryParameter("m5",Global.getScheduleinfo().get(5).get(1))
                    .appendQueryParameter("n5",Global.getScheduleinfo().get(5).get(2))
                    .appendQueryParameter("ni5",Global.getScheduleinfo().get(5).get(3))
                    .appendQueryParameter("d6",Global.getScheduleinfo().get(6).get(0))
                    .appendQueryParameter("m6",Global.getScheduleinfo().get(6).get(1))
                    .appendQueryParameter("n6",Global.getScheduleinfo().get(6).get(2))
                    .appendQueryParameter("ni6",Global.getScheduleinfo().get(6).get(3))
                    .build();

        System.out.println(uri);

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

                private String request(Uri uri ) {
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

    public void ShareFriend(Activity activity,String mymail) {
        mActivity = activity;

        Uri baseUri = Uri
                .parse("http://54.92.74.113/sue/ShareFriend.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("mymail",mymail)
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

                private String request(Uri uri ) {
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
                        //textView = (TextView)mActivity.findViewById(R.id.text_view);

                        spinner = (Spinner)mActivity.findViewById(R.id.spinner);

                        JSONObject json = new JSONObject(result);
                        JSONArray datas = json.getJSONArray("data");

                        for (int i = 0; i < datas.length(); i++) {
                            JSONObject item = datas.getJSONObject(i);

                            // タイトルを取得する場合
                            Global.setFriendlist(item.getString("fmail"));
                            //String fmail = item.getString("fmail");
                            //String allreleaseflag = item.getString("allreleaseflag");
                            //String scheduleflag = item.getString("scheduleflag");
                            //String profile = item.getString("profileURL");

                        }
                        fl = Global.getFriendlist();

                        // ArrayAdapter
                        ArrayAdapter<String> adapter
                                = new ArrayAdapter<String>(mActivity, android.R.layout.simple_spinner_item, fl);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // spinner に adapter をセット
                        spinner.setAdapter(adapter);

                        // リスナーを登録
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            //　アイテムが選択された時
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Spinner spinner = (Spinner) parent;
                                String item = (String) spinner.getSelectedItem();

                                for(int x = 0; x < fl.size(); x++){
                                    if(item.equals(fl.get(x))){
                                        //textView.setText(fl.get(x));
                                    }
                                }
                            }

                            //　アイテムが選択されなかった
                            public void onNothingSelected(AdapterView<?> parent) {
                                //
                            }
                        });
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
