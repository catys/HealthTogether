package com.yamada.biton.healthtogether;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

public class MeasurementHistoryActivity extends AppCompatActivity {
    private LinearLayout chartlayout;
    private LinearLayout chartlayout1;
    private final int HALF_DAY = 12 * 3600 * 1000;
    public boolean isLineCurve = true;
    private XYMultipleSeriesDataset dataset;
    private GraphicalView graphicalView;

    private float[] vData;
    private String[] ddData;
    private String month;
    private String month1;
    private String day;
    private String day1;
    private int Range = 50;
    private int lineWidth;
    private int barWidth;
    private float dpx;
    private int count;
    private int[] xData;
    private float[] yData;
    public List<String> Allvital = new ArrayList<String>();
    private ProgressDialog progressDialog;

    //ユーザーのメールアドレスを設定

    String mymail = "a@gmail.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementhistory);

        //プログレスバー
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("処理実行中");
        progressDialog.setCancelable(true);
        progressDialog.show();

        chartlayout = (LinearLayout) findViewById(R.id.chart1);
        GraphicalView graphicalView = TimeChartView();
        chartlayout.removeAllViews();
        chartlayout.addView(graphicalView);


        float ttotal=Global.gettotal();
        TextView textparam = (TextView)findViewById(R.id.vitaltext1);
        textparam.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            // テキストを設定
            textparam.setText("あなたのトータルは\n"+ ttotal + "kgです");
        //handler.postDelayed(updateRunnable, 0);
        progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private GraphicalView TimeChartView() {
        // (1)グラフデータの準備
        // X軸 日付
        ddData = Global.getdate();

        String [] xStrValue=ddData;

        // Y軸 体重
        vData = Global.getvital();
        float[] yDoubleValue = vData;

        //MAX MIN
        float max = vData[0];	//とりあえず最大値をn[0]に設定して変数maxに代入
        float min = vData[0];	//とりあえず最小値をn[0]に設定して変数minに代入
        for (int i=0; i<vData.length; i++) {
            if( vData[i]!=0.0) {
                if (max < vData[i]) {    //現在の最大値よりも大きい値が出たら
                    max = vData[i];    //変数maxに値を入れ替える
                }
                if (min > vData[i]) {    //現在の最小値よりも小さい値が出たら
                    min = vData[i];    //変数minに値を入れ替える
                }
            }
        }


        // 日付を文字形式から Date型へ変換
        int DataCount = xStrValue.length;
        Date[] xDateValue = new Date[DataCount];

            for (int i = 0; i < DataCount; i++) {
                if(xStrValue[i] != null) {
                xDateValue[i] = String2date(xStrValue[i]);
                    count = i+1;
                    System.out.println(count);
                }
            }


        // テキストを設定
        month= ddData[0].substring(5, 7);
        day = ddData[0].substring(8, 10);
        month1= ddData[count-1].substring(5, 7);
        day1= ddData[count-1].substring(8, 10);
        TextView textparam1 = (TextView)findViewById(R.id.datetext);
        textparam1.setText(month+"月"+ day +"日"+" ～ "+ month1 +"月"+ day1 +"日");


/*
        // テキストを設定
        ddData[0] = ddData[0].substring(5, 10);
        ddData[count-1] = ddData[count-1].substring(5, 10);
        TextView textparam1 = (TextView)findViewById(R.id.datetext);
        textparam1.setText(ddData[0] +" ～ "+ddData[count-1]);
*/


// (2) グラフのタイトル、X軸Y軸ラベル、色等の設定
XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setChartTitle("体重");     // グラフタイトル

                renderer.setChartTitleTextSize(50);             //
                renderer.setXTitle("");                     // X軸タイトル
                renderer.setYTitle("");                     // Y軸タイトル
                renderer.setAxisTitleTextSize(25);              //
                renderer.setLegendTextSize(25);                 // 凡例　テキストサイズ
                renderer.setPointSize(7f);                      // ポイントマーカーサイズ
                renderer.setXAxisMin(xDateValue[0].getTime() - HALF_DAY);  // X軸最小値
                renderer.setXAxisMax(xDateValue[count - 1].getTime() + HALF_DAY);    // X軸最大値


               //平均値出したい
                renderer.setYAxisMin(min-5);                    // Y軸最小値
                renderer.setYAxisMax(max+5);                    // Y軸最大値

                renderer.setXLabelsAlign(Paint.Align.CENTER);         // X軸ラベル配置位置
                renderer.setYLabelsAlign(Paint.Align.RIGHT);          // Y軸ラベル配置位置
                renderer.setAxesColor(Color.BLACK);            // X軸、Y軸カラー
                renderer.setLabelsColor(Color.BLACK);          // ラベルカラー
                renderer.setXLabelsColor(Color.BLACK);
                renderer.setYLabelsColor(0, Color.BLACK);
                renderer.setXLabels(5);                         // X軸ラベルのおおよその数
                renderer.setYLabels(5);                         // Y軸ラベルのおおよその数
                renderer.setLabelsTextSize(25);
                renderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));// ラベルサイズ
                // グリッド表示
                renderer.setShowGrid(true);
                // グリッド色
                renderer.setGridColor(Color.parseColor("#000000")); // グリッドカラー
                // グラフ描画領域マージン top, left, bottom, right
                renderer.setMargins(new int[]{100, 60, 60, 60});  //
                // 凡例非表示
                 renderer.setShowLegend(false);

                // マージンを凡例にフィットさせる
                renderer.setFitLegend(true);

                // (3) データ系列の色、マーク等の設定
                XYSeriesRenderer r1 = new XYSeriesRenderer();
                r1.setColor(Color.rgb(255, 170, 170));
                r1.setPointStyle(PointStyle.CIRCLE);
                r1.setLineWidth(5f);
                r1.setFillPoints(true);
                renderer.addSeriesRenderer(r1);


                // (4) データ系列　データの設定 (体重)
                XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                TimeSeries series1 = new TimeSeries("体重");     // データ系列凡例
                for (int i = 0; i < count; i++) {

                series1.add(xDateValue[i], yDoubleValue[i]);

                }
        dataset.addSeries(series1);


        // (5)タイムチャートグラフの作成
        GraphicalView mChartView = ChartFactory.getTimeChartView(this, dataset, renderer, "M/d");

        return mChartView;

        }

    /*
 * 日付時刻文字列を Date型に変換
 */

private Date String2date(String strDate) {
        Date dateDate = null;
        // 日付文字列→date型変換フォーマットを指定して
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        try {
        dateDate = sdf1.parse(strDate);
        } catch (ParseException e) {
        Toast.makeText(this, "正しい日付を入力して下さい", Toast.LENGTH_SHORT).show();
        }
        return dateDate;
        }


/*
private double addX = 0;
public int plus = 0;
private double minus = 0;

private Handler handler = new Handler();
private Runnable updateRunnable = new Runnable() {
        String mymail = "a@gmail.com";

@Override
public void run() {
        try {

        if (vData[plus] == 0.0) {
        progressDialog.dismiss();

        } else if (addX < vData.length) {
        handler.postDelayed(updateRunnable, 100);
        } else {
        progressDialog.dismiss();
        }
        } catch (Exception e) {
        System.out.println("例外が発生しました。");
        }
        }
        };
        */

        }

