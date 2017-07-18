package com.yamada.biton.healthtogether;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
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

public class MeasurementHistoryActivity extends Activity {
    private LinearLayout chartlayout;
    private LinearLayout chartlayout1;
    private final int HALF_DAY = 12 * 3600 * 1000;
    public boolean isLineCurve = true;
    private XYMultipleSeriesDataset dataset;
    private GraphicalView graphicalView;


    private int Range = 50;
    private int lineWidth;
    private int barWidth;
    private float dpx;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("処理実行中");
        progressDialog.setCancelable(true);
        progressDialog.show();


        String[] titles = new String[] { "体重" };
        List<float[]> x = new ArrayList<float[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new float[] { });
        }
        List<float[]> values = new ArrayList<float[]>();
        values.add(new float[] {0});
        values.add(new float[] {0});
        int[] colors = new int[] { Color.MAGENTA };
        PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND };
        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
                    .setFillPoints(true);
        }
        setChartSettings(renderer, "Average temperature", "Horizontal axis",
                "Vertical axis", 0, 12.5, 0, 100, Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(5);
        renderer.setYLabels(5);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[] { 0, 50, 0, 100 });
        renderer.setZoomLimits(new double[] { 0, 50, -10, 100 });

        dataset = buildDataset(titles, x, values);

        graphicalView = ChartFactory.getLineChartView(
                getApplicationContext(), dataset, renderer);

        LinearLayout cHartEngineLayout = (LinearLayout) findViewById(R.id.chart1);
        cHartEngineLayout.addView(graphicalView);
        chartlayout = (LinearLayout) findViewById(R.id.chart1);
        chartlayout.removeAllViews();
        chartlayout.addView(graphicalView);
        //chartlayout1 = (LinearLayout) findViewById(R.id.chart1);
        //chartlayout1.removeAllViews();




        //chartlayout1.addView(textparam);
        handler.postDelayed(updateRunnable, 0);
    }



    private XYMultipleSeriesDataset buildDataset(String[] titles,
                                                 List<float[]> xValues, List<float[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, titles, xValues, yValues, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles,
                             List<float[]> xValues, List<float[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            float[] xV = xValues.get(i);
            float[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }

    private XYMultipleSeriesRenderer buildRenderer(int[] colors,
                                                   PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
                             PointStyle[] styles) {
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setPointSize(5f);
        renderer.setMargins(new int[] { 30, 30, 30, 30 });
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            renderer.addSeriesRenderer(r);
        }
    }
//グラフの背景
    private void setChartSettings(XYMultipleSeriesRenderer renderer,
                                  String title, String xTitle, String yTitle, double xMin,
                                  double xMax, double yMin, double yMax, int axesColor,
                                  int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }


    private  float[] vData;
    private double addX = 0;
    public int plus = 0;
    private double minus = 0;

    private Handler handler = new Handler();
    private Runnable updateRunnable = new Runnable() {
        String mymail = "a@gmail.com";

        @Override
        public void run() {
            try {
                vData = new float[30];
                if(Global.getvital() != null) {
                    System.out.println("Globalget");
                    vData = Global.getvital();
                    //Global.setvitalflag(plus);
                    System.out.println();
                }

                    if (vData.length != 0 && plus != -1) {

                        System.out.println("vDatalget");
                        System.out.println(vData[plus]);
                        System.out.println(plus);
                        //plus = Global.getvitalflag();
                        //vData = new float[Range];
                        if(minus != 0) {
                            dataset.getSeriesAt(0).add(addX, vData[plus]);
                            //dataset.getSeriesAt(1).add(addX, minus);
                            addX++;
                            plus++;
                        }

                        //Global.setvitalflag(plus);
                        //plus++;
                        minus++;
                        graphicalView.repaint();
                    }
                if( vData[plus] == 0.0){
                    String ttotal=Global.gettotal();

                    TextView textparam = (TextView)findViewById(R.id.vitaltext1);
                    //String valueToString = String.valueOf(ttotal);
                    // テキストを設定
                    textparam.setText(ttotal);
                    progressDialog.dismiss();

                }else  if (addX < vData.length ) {
                    handler.postDelayed(updateRunnable, 100);
                } else {

                    //float ttotal=Global.gettotal();
                    TextView textparam = (TextView)findViewById(R.id.vitaltext1);

                    // テキストを設定
                    textparam.setText("Test TextView");
                    progressDialog.dismiss();
                }
            }catch (Exception e){
                    System.out.println("例外が発生しました。");
                }
    }
};



}

/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementhistory);

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("処理実行中");
        progressDialog.setCancelable(true);
        progressDialog.show();
        vData = new float[30];

        if(Global.getvital() == null){
            handler.postDelayed(updateRunnable, 0);
        }

        chartlayout = (LinearLayout) findViewById(R.id.chart);
        GraphicalView graphicalView = TimeChartView();
        chartlayout.removeAllViews();
        chartlayout.addView(graphicalView);

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
        String[] xStrValue = {"2015/12/22", "2015/12/21", "2015/12/20", "2015/12/18"};
        // Y軸 体重
        vData = Global.getvital();
        float[] yDoubleValue =vData;
        //float[] yDoubleValue = {(float) 71.2, (float) 71.8, (float) 70.8, (float) 70.4};
        // X軸 日付
        //String [] xStrValueTarget={ "2015/12/22","2015/12/21","2015/12/20","2015/12/19", "2015/12/18"};
        // Y軸 目標体重
        //double[] yDoubleValueTarget={ 70.3, 70.3, 70.3, 70.3, 70.3 };

        // 日付を文字形式から Date型へ変換
        int DataCount = xStrValue.length;
        Date[] xDateValue = new Date[DataCount];
        for (int i = 0; i < DataCount; i++) {
            xDateValue[i] = String2date(xStrValue[i]);
        }
/*
        int DataCountTarget=xStrValueTarget.length;
        Date[] xDateValueTarget = new Date[DataCountTarget];
        for (int i = 0; i < DataCountTarget; i++) {
            xDateValueTarget[i] =String2date(xStrValueTarget[i]);
        }
*/
/*
// (2) グラフのタイトル、X軸Y軸ラベル、色等の設定
XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setChartTitle("体重");     // グラフタイトル
                renderer.setChartTitleTextSize(25);             //
                renderer.setXTitle("日付");                     // X軸タイトル
                renderer.setYTitle("体重(Kg)");                     // Y軸タイトル
                renderer.setAxisTitleTextSize(25);              //
                renderer.setLegendTextSize(25);                 // 凡例　テキストサイズ
                renderer.setPointSize(7f);                      // ポイントマーカーサイズ
                renderer.setXAxisMin(xDateValue[xDateValue.length - 1].getTime() - HALF_DAY);  // X軸最小値
                renderer.setXAxisMax(xDateValue[0].getTime() + HALF_DAY);    // X軸最大値
                renderer.setYAxisMin(70.0f);                    // Y軸最小値
                renderer.setYAxisMax(72.0f);                    // Y軸最大値
                renderer.setXLabelsAlign(Paint.Align.CENTER);         // X軸ラベル配置位置
                renderer.setYLabelsAlign(Paint.Align.RIGHT);          // Y軸ラベル配置位置
                renderer.setAxesColor(Color.BLACK);            // X軸、Y軸カラー
                renderer.setLabelsColor(Color.BLACK);          // ラベルカラー
                renderer.setXLabelsColor(Color.BLACK);
                renderer.setYLabelsColor(0, Color.BLACK);
                renderer.setXLabels(5);                         // X軸ラベルのおおよその数
                renderer.setYLabels(5);                         // Y軸ラベルのおおよその数
                renderer.setLabelsTextSize(25);                 // ラベルサイズ
                // グリッド表示
                renderer.setShowGrid(true);
                // グリッド色
                renderer.setGridColor(Color.parseColor("#808080")); // グリッドカラー
                // グラフ描画領域マージン top, left, bottom, right
                renderer.setMargins(new int[]{40, 100, 15, 40});  //
                // 凡例非表示
                // renderer.setShowLegend(false);
                renderer.setMarginsColor(Color.rgb(128, 128, 128));
                // マージンを凡例にフィットさせる
                renderer.setFitLegend(true);

                // (3) データ系列の色、マーク等の設定
                XYSeriesRenderer r1 = new XYSeriesRenderer();
                r1.setColor(Color.rgb(255, 166, 0));
                r1.setPointStyle(PointStyle.CIRCLE);
                r1.setLineWidth(5f);
                r1.setFillPoints(true);
                renderer.addSeriesRenderer(r1);
        /*
        XYSeriesRenderer r2 = new XYSeriesRenderer();
        r2.setColor(Color.rgb(255, 0, 0));
        r2.setPointStyle(PointStyle.SQUARE);
        r2.setLineWidth(5f);
        r2.setFillPoints(true);
        renderer.addSeriesRenderer(r2);
*/

/*
                // (4) データ系列　データの設定 (体重)
                XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
                TimeSeries series1 = new TimeSeries("体重");     // データ系列凡例
                for (int i = 0; i < DataCount; i++) {
        series1.add(xDateValue[i], yDoubleValue[i]);
        }
        dataset.addSeries(series1);

       /* // (4) データ系列　データの設定 (目標値)
        TimeSeries seriesTarget = new TimeSeries("目標値");     //
        for (int i = 0; i < DataCountTarget; i++) {
            seriesTarget.add(xDateValueTarget[i], yDoubleValueTarget[i]);
        }
        dataset.addSeries(seriesTarget);
*/
/*
        // (5)タイムチャートグラフの作成
        GraphicalView mChartView = ChartFactory.getTimeChartView(this, dataset, renderer, "M/d");

        return mChartView;

        }

    /*
 * 日付時刻文字列を Date型に変換
 */
/*
private Date String2date(String strDate) {
        Date dateDate = null;
        // 日付文字列→date型変換フォーマットを指定して
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/M/d");

        try {
        dateDate = sdf1.parse(strDate);
        } catch (ParseException e) {
        Toast.makeText(this, "正しい日付を入力して下さい", Toast.LENGTH_SHORT).show();
        }
        return dateDate;
        }


private float[] vData;
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

        }
 */