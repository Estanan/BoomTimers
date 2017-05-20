package com.example.administrator.boomtimer.Activity;

import android.content.res.Resources;
import android.os.Bundle;

import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.db.DB;
import com.example.administrator.boomtimer.model.ActivityItem4View;
import com.example.administrator.boomtimer.model.History4View;
import com.example.administrator.boomtimer.model.MyTime;
import com.example.administrator.boomtimer.model.Tag;
import com.example.administrator.boomtimer.util.SmallUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by shady on 2017/2/19.
 * 趋势界面
 */

public class TrendeActivity extends BaseActivity {

    private PieChart chart;
    private PieData data;
    private ArrayList<String> xVals;
    private ArrayList<Entry> yVals;
    private PieDataSet dataSet;
    private Random random;
    private LineChart lineChart;
    private LineData dataline;
    private ArrayList<String> xValsline;
    private ArrayList<Entry> yValsline;
    private LineDataSet dataSetline;
    private Random randomline;
    private int tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_history);
        Bundle extras = getIntent().getExtras();
        tagId = extras.getInt("tagId");
        initData();
        lineChart = (LineChart) findViewById(R.id.spread_line_chart);
        chart = (PieChart) findViewById(R.id.spread_pie_chart);
        yVals = new ArrayList<>();
        xVals = new ArrayList<String>();
        random = new Random();
        yValsline = new ArrayList<>();
        xValsline = new ArrayList<String>();
        randomline = new Random();
        if (historyList == null || historyList.size() == 0) {
            yVals.add(new Entry(0, 0));
            xVals.add(0 + "月");
        } else {
            for (int i = 0; i < historyList.size(); i++) {
                yVals.add(new Entry(historyList.get(i).getActivities().getDuration(), i));
                xVals.add((historyList.get(i).getActivities().getBeginTime()).getMonth() + "月" +
                        historyList.get(i).getActivities().getBeginTime().getDay() + "日");
            }

        }

        dataSet = new PieDataSet(yVals, "持续时间");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new PieData(xVals, dataSet);
        chart.setData(data);

        chart.animateY(3000);

        dataSetline = new LineDataSet(yVals, "持续时间");
        dataSetline.setColors(ColorTemplate.COLORFUL_COLORS);
        dataline = new LineData(xVals, dataSetline);
        lineChart.setData(dataline);
        lineChart.animateY(3000);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        initData();
    }

    public static List<History4View> historyList;
    public static DB mDB;

    private void initData() {
        mDB = DB.getInstance(this);
        historyList = mDB.searchTag(tagId);
        historyList.size();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_trende;
    }
}
