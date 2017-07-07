package com.yamada.biton.healthtogether.AsyncTasksPackage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.yamada.biton.healthtogether.Global;
import com.yamada.biton.healthtogether.Param;

import org.achartengine.GraphicalView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Syota-1207 on 2017/06/28.
 */

public class ConnectHttpVital  extends Activity {
    private static final String TAG = ConnectHttpVital.class.getSimpleName();
    private AsyncTask<Uri, Void, String> mTask;
    private Activity mActivity;
    public List<String> mailaddress;
    public List<String> date;
    public List<String> bwflag;
    public List<String> bodyweight;
    private boolean isLineCurve =  true;

    private GraphicalView graphicalView;
    private LinearLayout chartlayout;

    private int Range = 50;
    private int lineWidth ;
    private int barWidth;
    private float dpx;

    private int[] xData ;
    private int[] yData ;
    public List<String> Allvital = new ArrayList<String>();



    Global global = (Global) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        global.initGlobalArrayList();
    }

    private void sendText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mTextView.append(text);
            }
        });
    }

    public void MeasurementHistorySelect(Activity activity,String mymail) {
        mActivity = activity;
        /*mailaddress.clear();
        date.clear();
        bwflag.clear();
        bodyweight.clear();*/

        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/shima/VitalHistory.php");

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
                    HttpURLConnection http = null;
                    InputStream is = null;
                    String result = null;
                    try {
                        // URLにHTTP接続
                        URL url = new URL(uri.toString());
                        http = (HttpURLConnection) url.openConnection();

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
                        //global.initGlobalArrayList();
                        Log.d("テスト", "これはメッセージです");
                        List mailaddress = new ArrayList<String>();
                        List date = new ArrayList<String>();
                        List bwflag = new ArrayList<String>();
                        List bodyweight = new ArrayList<String>();

                        JSONObject json = new JSONObject(result);
                        JSONArray viar = json.getJSONArray("data");
                        for (int i = 0; i < viar.length(); i++) {
                            JSONObject data = viar.getJSONObject(i);
                            // メールアドレスを取得
                            String gmailaddress = data.getString("mailaddress");
                            // 要素を追加
                            mailaddress.add(gmailaddress);
                            //　日付を取得
                            String gdate = data.getString("date");
                            // 要素を追加
                            date.add(gdate);
                            // bwflagを取得
                            String gbwflag = data.getString("bwflag");
                            // 要素を追加
                            bwflag.add(gbwflag);
                            // 体重を取得
                            String gbodyweight = data.getString("bodyweight");
                            // 要素を追加
                            bodyweight.add(gbodyweight);
                            Log.d("テスト", "これはメッセージです。4");
                        };
                        //global.initGlobalArrayList();
                        //global.setFlag(1);
                        for(int j = 0;bodyweight.size() > j ;j++) {
                            Log.d("テスト", "これはメッセージです。2");
                            System.out.println(bodyweight.get(j));
                            //global.bodyweightinfo.add(String.valueOf(bodyweight.get(j)));
                        }
                        global.setbodyweight(bodyweight);
                        //TextView textView = (TextView) mActivity.findViewById(R.id.friendText);

                        //ImageButton button2 = (ImageButton)mActivity.findViewById(R.id.friendGetButton);
                        // 画面サイズに合わせたチャート幅を設定する


                        // チャートデータ
                        xData = new int[Range];
                        yData = new int[Range];

                        // 適当なデータを作成する
                        //if(flag == 1) {
                        for (int i = 0; 4 > i; i++) {
                            //yData[i] = -i*i/50+5*i/10+70;
                            //Allvital.get(i);
                            String string = "123";
                            int intdata = Integer.parseInt(string);
                            yData[i] = intdata + 10;

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

    public List<String> bodyweightdata(){
        Log.d("テスト", "これはメッセージです。5");
        List weight = bodyweight;
        return weight;

    }

    /**
     * Created by 優太 on 2017/06/28.
     */

    public static class PostBmpAsyncHttpRequest extends AsyncTask<Param, Void, String> {
        private Activity mActivity;

        public PostBmpAsyncHttpRequest(Activity activity) {

            mActivity = activity;
        }

        @Override
        protected String doInBackground(Param... params) {
            Param param = params[0];
            HttpURLConnection connection = null;
            StringBuilder sb = new StringBuilder();
            try {
                // 画像をjpeg形式でstreamに保存
                ByteArrayOutputStream jpg = new ByteArrayOutputStream();
                param.bmp.compress(Bitmap.CompressFormat.JPEG, 100, jpg);


                URL url = new URL(param.uri);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);//接続タイムアウトを設定する。
                connection.setReadTimeout(3000);//レスポンスデータ読み取りタイムアウトを設定する。
                connection.setRequestMethod("POST");//HTTPのメソッドをPOSTに設定する。
                //ヘッダーを設定する
                connection.setRequestProperty("User-Agent", "Android");
                connection.setRequestProperty("Content-Type","application/octet-stream");
                connection.setDoInput(true);//リクエストのボディ送信を許可する
                connection.setDoOutput(true);//レスポンスのボディ受信を許可する
                connection.setUseCaches(false);//キャッシュを使用しない
                connection.connect();

                // データを投げる
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                out.write(jpg.toByteArray());
                out.flush();

                // データを受け取る
                InputStream is = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = "";
                while ((line = reader.readLine()) != null)
                    sb.append(line);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                connection.disconnect();
            }
            return sb.toString();
        }

        public void onPostExecute(String string) {
            // 戻り値をViewにセット
            //TextView textView = (TextView) mActivity.findViewById(R.id.textView);
            //textView.setText(string);
        }


    }
}
