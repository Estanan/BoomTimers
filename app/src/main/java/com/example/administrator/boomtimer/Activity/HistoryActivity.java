package com.example.administrator.boomtimer.Activity;

import android.os.Bundle;
import android.util.Log;

import com.example.administrator.boomtimer.Adapter.HistoryListAdapter;
import com.example.administrator.boomtimer.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Random;

import de.halfbit.pinnedsection.PinnedSectionListView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class HistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_history);
        initViews();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }

    private PinnedSectionListView psListView;
    public static HistoryListAdapter adapter;
    private LineChart lineChart;
    private LineData data;
    private ArrayList<String> xVals;
    private LineDataSet dataSet;
    private ArrayList<Entry> yVals;
    private Random random;
    public void initViews() {
        lineChart= (LineChart) findViewById(R.id.spread_line_chart);
        psListView = (PinnedSectionListView) findViewById(R.id.pinned_section_list_view);
        psListView.setEmptyView(findViewById(R.id.empty_view));
        loadData();
    }

    public void loadData() {
        adapter = new HistoryListAdapter(this);
        psListView.setAdapter(adapter);
        Log.e("AddActivitiesFragment", "loadData");
//        MainActivity.setAdapter(adapter);
//        getActivity().setTitle("History");
        xVals=new ArrayList<>();
        yVals=new ArrayList<>();
        random=new Random();
        for(int i=0;i<12;i++){
            float profix=random.nextFloat();
            yVals.add(new Entry(profix,i));
            xVals.add((i+1)+"月");
        }
        dataSet=new LineDataSet(yVals,"事件时间");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data=new LineData(xVals,dataSet);
        lineChart.setData(data);
        lineChart.setDescription("事件时间");
        lineChart.animateY(3000);
    }

    @Override
    protected void onDestroy() {
        adapter = null;
        super.onDestroy();
    }

}
