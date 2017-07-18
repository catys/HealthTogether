package com.yamada.biton.healthtogether.AsyncTasksPackage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yamada.biton.healthtogether.Global;
import com.yamada.biton.healthtogether.R;

import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
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
    public boolean isLineCurve =  true;


    private LinearLayout chartlayout;
    private LinearLayout cHartEngineLayout;

    private int Range = 30;
    private int lineWidth ;
    private int barWidth;
    private float dpx;

    private int[] xData ;
    private float[] yData ;
    public List<String> Allvital = new ArrayList<String>();

    private XYMultipleSeriesDataset dataset;
    private GraphicalView graphicalView;
    private ProgressDialog progressDialog;


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

    public void MeasurementHistorySelect(Activity activity,String mymail) {
        mActivity = activity;
        //chartlayout = LL;



        //graphicalView =GV;
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
                        System.out.println("1");
                        // チャートデータ
                        xData = new int[Range];
                        yData = new float[Range];

                        List mailaddress = new ArrayList<String>();
                        List date = new ArrayList<String>();
                        List bwflag = new ArrayList<String>();
                        List bodyweight = new ArrayList<String>();

                        JSONObject json = new JSONObject(result);
                        JSONArray viar = json.getJSONArray("data");
                        for (int i = 0; i < viar.length(); i++) {
                            JSONObject data = viar.getJSONObject(i);
                            /*
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
                            bodyweight.add(gbodyweight);*/
                            String gbodyweight = data.getString("bodyweight");
                            yData[i] = Float.parseFloat(gbodyweight);
                            System.out.println(yData[i]);
                        }
                         Global.setvital(yData);

                        //TextView textView = (TextView) mActivity.findViewById(R.id.friendText);

                        //ImageButton button2 = (ImageButton)mActivity.findViewById(R.id.friendGetButton);

                        //MeasurementHistoryActivity ac = new MeasurementHistoryActivity();
                        //ac.nice();
                        //ac.handler.postDelayed(updateRunnable, 1000);

                        //makeChart(chartlayout);
                        mTask = null;
                    } catch (Exception e) {
                        System.out.println("取得エラー");
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

    public void Totalvital(Activity activity,String mymail) {
        mActivity = activity;
        //chartlayout = LL;



        //graphicalView =GV;
        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/shima/Totalvital.php");

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
                        System.out.println("Totalvital");


                        JSONObject json = new JSONObject(result);
                        JSONObject total = json.getJSONObject("data");
                            String totalbodyweight = total.getString("vital");
                            //float vital = Float.parseFloat(totalbodyweight);
                        System.out.println("Totalvital");
                            System.out.println(totalbodyweight);
                        Global.settotal(totalbodyweight);

                        TextView textView = (TextView) mActivity.findViewById(R.id.vitaltext1);

                        //ImageButton button2 = (ImageButton)mActivity.findViewById(R.id.friendGetButton);

                        //MeasurementHistoryActivity ac = new MeasurementHistoryActivity();
                        //ac.nice();
                        //ac.handler.postDelayed(updateRunnable, 1000);

                        //makeChart(chartlayout);
                        mTask = null;
                    } catch (Exception e) {
                        System.out.println("取得エラー");
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



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
