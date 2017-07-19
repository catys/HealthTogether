package com.yamada.biton.healthtogether.AsyncTasksPackage;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
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
 * Created by miipo on 2017/07/12.
 */

public class ConnectHttpUser extends Activity {
    private static final String TAG = ConnectHttpUser.class.getSimpleName();
    private AsyncTask<Uri, Void, String> mTask;
    private Activity mActivity;
//    Global global = (Global)getApplication();

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

    //個人情報登録
    public void UserInsert(Activity activity, String mailaddress,String password,String nickname,String profileURL,String monitorflag) {
        mActivity = activity;

        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/inu/UserInsert.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                //ユーザの情報
                .appendQueryParameter("mailaddress",mailaddress)
                .appendQueryParameter("password",password)
                .appendQueryParameter("nickname",nickname)
                .appendQueryParameter("profileURL",profileURL)
                .appendQueryParameter("monitorflag",monitorflag)
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

    //個人情報表示
    public void UserSelect(Activity activity,String mail) {
        mActivity = activity;

        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/inu/UserSelect.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("mail",mail)
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
                        JSONObject json = new JSONObject(result);
                        // タイトルを取得する場合
                        JSONObject item = json.getJSONObject("data");
                        String mail = item.getString("mailaddress");//メールアドレス
                        String pass = item.getString("password");//パスワード
                        String nick = item.getString("nickname");//ニックネーム
                        String profile = item.getString("profileURL");//プロフィール画像
                        String monitor = item.getString("monitorflag");//監視者フラグ
                        int sex = item.getInt("sex");//性別
                        String height = item.getString("height");//身長
                        String age = item.getString("age");//性別

                        //プロフィール画像
//                        ImageView imageView = (ImageView) mActivity.findViewById(R.id.userImg);

                        //メールアドレス
                        TextView mailTxt = (TextView) mActivity.findViewById(R.id.userMailTxt);
                        mailTxt.setText(mail);

                        //パスワード
                        TextView passTxt = (TextView) mActivity.findViewById(R.id.userPassTxt);
                        passTxt.setText(pass);

                        //ニックネーム
                        TextView nickTxt = (TextView) mActivity.findViewById(R.id.userNickTxt);
                        nickTxt.setText(nick);

                        //性別
                        TextView sexTxt = (TextView) mActivity.findViewById(R.id.userSexTxt);
                        String woman = "女",man = "男";
                        if (sex == 1) {
                            sexTxt.setText(man);
                        }else if(sex == 0) {
                            sexTxt.setText(woman);
                        }

                        //身長
                        TextView heightTxt = (TextView) mActivity.findViewById(R.id.userHeightTxt);
                        heightTxt.setText(height);

                        //年齢
                        TextView ageTxt = (TextView) mActivity.findViewById(R.id.userAgeTxt);
                        ageTxt.setText(age);

//公開する情報ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー

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

    //編集画面に表示する
    public void UserEditSelect(Activity activity,String mail) {
        mActivity = activity;

        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/inu/UserSelect.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("mail",mail)
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
                        JSONObject json = new JSONObject(result);
                        // タイトルを取得する場合
                        JSONObject item = json.getJSONObject("data");
                        String mail = item.getString("mailaddress");//メールアドレス
                        String pass = item.getString("password");//パスワード
                        String nick = item.getString("nickname");//ニックネーム
                        String profile = item.getString("profileURL");//プロフィール画像
                        String monitor = item.getString("monitorflag");//監視者フラグ
                        int sex = item.getInt("sex");//性別
                        String height = item.getString("height");//身長
                        String age = item.getString("age");//性別

                        //編集画面に表示

                        //プロフィール画像
//                        ImageView imageView = (ImageView) mActivity.findViewById(R.id.userImg);

                        //メールアドレス
                        EditText mailTxt1 = (EditText) mActivity.findViewById(R.id.mailTxt);
                        mailTxt1.setText(mail);

                        //パスワード
                        EditText passTxt1 = (EditText) mActivity.findViewById(R.id.passTxt);
                        passTxt1.setText(pass);

                        //ニックネーム
                        EditText nickTxt1 = (EditText) mActivity.findViewById(R.id.nickTxt);
                        nickTxt1.setText(nick);

                        //性別
                        RadioGroup group = (RadioGroup)mActivity.findViewById(R.id.radioGroupSex);
                        if (sex == 1) {
                            group.check(R.id.manRadioBtn);
                        }else if(sex == 0) {
                            group.check(R.id.womanRadioBtn);
                        }

                        //身長
                        EditText heightTxt1 = (EditText) mActivity.findViewById(R.id.heightTxt);
                        heightTxt1.setText(height);

                        //年齢
                        EditText ageTxt1 = (EditText) mActivity.findViewById(R.id.ageTxt);
                        ageTxt1.setText(age);


                        //公開する情報ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー



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


    public void UserUpdate(Activity activity,String beforemail,String mail,String pass,String nick,String proURL,String bw,String bf,String bmi,String vf,String sm,String ba,String bm) {
        //    mActivity = activity;

        // URLを、扱いやすいUri型で組む
        Uri baseUri = Uri
                .parse("http://54.92.74.113/inu/UserUpdate.php");

        // パラメータの付与
        Uri uri = baseUri.buildUpon()
                .appendQueryParameter("beforemail",beforemail)
                .appendQueryParameter("mailaddress",mail)
                .appendQueryParameter("password",pass)
                .appendQueryParameter("nickname",nick)
                .appendQueryParameter("profileURL",proURL)
                .appendQueryParameter("bwflag",bw)
                .appendQueryParameter("bfflag",bf)
                .appendQueryParameter("bmiflag",bmi)
                .appendQueryParameter("vfflag",vf)
                .appendQueryParameter("smflag",sm)
                .appendQueryParameter("baflag",ba)
                .appendQueryParameter("bmflag",bm)
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

                /*                @Override
                                protected void onPostExecute(String result) {
                                    try {
                                        ImageButton button2 = (ImageButton)mActivity.findViewById(R.id.friendGetButton);
                                        button2.setImageResource(release_button);

                                        mTask = null;
                                    } catch (Exception e) {
                                    }

                                }
                */
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
