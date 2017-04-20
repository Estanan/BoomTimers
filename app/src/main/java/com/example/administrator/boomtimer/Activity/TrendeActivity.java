package com.example.administrator.boomtimer.Activity;

import android.os.Bundle;

import com.example.administrator.boomtimer.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by shady on 2017/2/19.
 * 趋势界面
 */

public class TrendeActivity extends BaseActivity {

    private PieChart chart;
    private PieData data;
    private ArrayList<String> xVals;
    private ArrayList<Entry>  yVals;
    private PieDataSet dataSet;
    private Random random;
    private LineChart lineChart;
    private LineData dataline;
    private ArrayList<String> xValsline;
    private ArrayList<Entry> yValsline;
    private LineDataSet dataSetline;
    private Random randomline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_history);
        lineChart= (LineChart) findViewById(R.id.spread_line_chart);
        chart= (PieChart) findViewById(R.id.spread_pie_chart);
        yVals=new ArrayList<>();
        xVals=new ArrayList<String>();
        random=new Random();
        yValsline=new ArrayList<>();
        xValsline=new ArrayList<String>();
        randomline=new Random();
        for(int i=0;i<6;i++){
            float profix=random.nextFloat()*1000;
            yVals.add(new Entry(profix,i));
            xVals.add((i+1)+"月");
        }

        dataSet=new PieDataSet(yVals,"事件时间");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data=new PieData(xVals,dataSet);
        chart.setDescription("事件时间");
        chart.setData(data);
        chart.animateY(3000);

        for(int i=0;i<12;i++){
            float profix=randomline.nextFloat();;
            yValsline.add(new Entry(profix,i));
            xValsline.add((i+1)+"月");
        }
        dataSetline=new LineDataSet(yValsline,"事件时间");
        dataSetline.setColors(ColorTemplate.COLORFUL_COLORS);
        dataline=new LineData(xValsline,dataSetline);
        lineChart.setData(dataline);
        lineChart.setDescription("事件时间");
        lineChart.animateY(3000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trende;
    }
}
