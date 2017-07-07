package com.yamada.biton.healthtogether;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.yamada.biton.healthtogether.AsyncTasksPackage.ConnectHttpVital;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
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

public class MeasurementHistoryActivity extends AppCompatActivity {

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

    Global global = (Global)this.getApplication();

    //ユーザーのメールアドレスを設定
    String mymail = "a@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurementhistory);
        global = (Global)this.getApplication();
        //履歴全件検索
        ConnectHttpVital postdata = new ConnectHttpVital();
        postdata.MeasurementHistorySelect(this,mymail);

        //int flag = global.getFlag();

        //Allvital = global.getbodyweight();
        Allvital.add("1");
        Allvital.add("10");
        for(int j = 0;Allvital.size() > j ;j++) {
            System.out.println(Allvital.get(j));
        }

        // 画面サイズに合わせたチャート幅を設定する
        float dpp = getResources().getDisplayMetrics().densityDpi;
        dpx= dpp/160;
        lineWidth = (int) (2*dpx);
        barWidth = (int) (2*dpx);

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
        //}

        /*for(int i = 0; 10 > i; i++){
            //yData[i] = -i*i/50+5*i/10+70;
            Allvital.get(i);
            int intdata = Integer.parseInt(Allvital.get(i));
            yData[i] =intdata;

        }*/

        makeChart();

    }

    private void makeChart(){
        chartlayout = (LinearLayout) findViewById(R.id.chart);
        graphicalView = graphMake();
        chartlayout.removeAllViews();
        chartlayout.addView(graphicalView);
    }


    private GraphicalView graphMake(){

        XYMultipleSeriesDataset dataset;

        // データのタイトル
        String title = "Data";

        // Point stype の設定
        PointStyle style = PointStyle.POINT;
        int color = Color.rgb(90, 140, 90) ;
        XYMultipleSeriesRenderer renderer = buildRenderer(color, style);

        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
                    .setFillPoints(true);
        }

        setChartSettings(renderer,
                "体重",    // title
                "日付",                // X-axis title
                "増減",                // y-axis title
                0,                        // x Min
                Range + 10,                 // x Max
                0,                        // y Min
                105,                      // y Max
                Color.LTGRAY,
                Color.LTGRAY);

        renderer.setApplyBackgroundColor(true);

        // this is trick to set transparent background
        // チャートの背景色
        renderer.setBackgroundColor(Color.argb(0x80, 0x01, 0x01, 0xa0));
        // チャート外側余白の背景色
        renderer.setMarginsColor(Color.argb(0x80, 0x01, 0x01, 0x01));

        renderer.setXLabels(10);
        renderer.setYLabels(10);

        // グリッド表示
        renderer.setShowGrid(true);

        renderer.setXLabelsAlign(Paint.Align.LEFT);
        renderer.setYLabelsAlign(Paint.Align.LEFT);

        dataset = buildDataset(title);

        GraphicalView gView;
        if(isLineCurve){
            // Line curve
            gView = ChartFactory.getLineChartView(
                    getApplicationContext(), dataset, renderer);
        }
        else{
            // bar
            gView = ChartFactory.getBarChartView(
                    getApplicationContext(), dataset, renderer, BarChart.Type.DEFAULT);
        }

        LinearLayout cHartEngineLayout = (LinearLayout) findViewById(R.id.chart);
        cHartEngineLayout.addView(gView);
        return gView;
    }

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
        renderer.setBarWidth(barWidth);
    }


    private XYMultipleSeriesDataset buildDataset(String title) {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        addXYSeries(dataset, title, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, String title, int scale) {
        XYSeries series = new XYSeries(title, scale);

        for(int i = 0; i < Range; i++){
            xData[i] = i;
            series.add(xData[i], yData[i]);
        }

        dataset.addSeries(series);

    }

    private XYMultipleSeriesRenderer buildRenderer(int colors, PointStyle styles) {

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int colors,
                             PointStyle styles) {

        renderer.setAxisTitleTextSize(18*dpx);
        renderer.setChartTitleTextSize(24*dpx);

        renderer.setLabelsTextSize(14*dpx);
        renderer.setLegendTextSize(14*dpx);

        renderer.setYLabelsPadding(24*dpx);
        renderer.setXLabelsPadding(0*dpx);

        // グリッド色
        renderer.setGridColor(Color.DKGRAY);
        // upper, left, under, right
        int topmargine = (int)(40*dpx);
        int leftmargine = (int)(50*dpx);
        int bottommargine = (int)(1*dpx);
        int rihgtmargin = (int)(30*dpx);
        renderer.setMargins(new int[] {topmargine, leftmargine, bottommargine, rihgtmargin });

        XYSeriesRenderer r = new XYSeriesRenderer();
        r.setColor(colors);

        // Point stype の設定
        // PointStyle style = PointStyle.POINT;
        // int color = Color.rgb(90, 140, 90) ;
        r.setPointStyle(styles);
        r.setLineWidth(lineWidth);
        // area chart
        r.setFillBelowLine(true);
        r.setFillBelowLineColor(Color.argb(0x88, 0x00, 0x88,0x88));

        renderer.addSeriesRenderer(r);

    }
}

