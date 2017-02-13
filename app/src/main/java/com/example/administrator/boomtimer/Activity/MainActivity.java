package com.example.administrator.boomtimer.Activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.administrator.boomtimer.Adapter.ActivitiesListAdapter;
import com.example.administrator.boomtimer.Adapter.HistoryListAdapter;
import com.example.administrator.boomtimer.Adapter.MyFragmentPagerAdapter;
import com.example.administrator.boomtimer.Adapter.TagListAdapter;
import com.example.administrator.boomtimer.Fragment.AddActivitiesFragment;
import com.example.administrator.boomtimer.Fragment.AddTagFragment;
import com.example.administrator.boomtimer.Fragment.HistoryFragment;
import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.db.DB;
import com.example.administrator.boomtimer.model.ActivityItem4View;
import com.example.administrator.boomtimer.model.History4View;
import com.example.administrator.boomtimer.model.MyTime;
import com.example.administrator.boomtimer.model.SetItemInOrder;
import com.example.administrator.boomtimer.model.Tag;
import com.example.administrator.boomtimer.util.SmallUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TagListAdapter tagListAdapter;
    private ListAdapter adapter;
    MyFragmentPagerAdapter pagerAdapter;

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout mDrawerLayout;
    private ListView lvLeftMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] lvs = {"统计", "趋势", "设置"};
//    private List<Tag> mDatas = init();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), 4, this);
        initView();
//        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.cancel, R.string.ok) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lvs);
        lvLeftMenu.setAdapter(adapter);
        mDB = DB.getInstance(this);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
    }

//    public void setTitle(String title) {
//        toolbar.setTitle(title);
//    }

    public static DB mDB;
    public static List<Tag> tagList;
    public static List<ActivityItem4View> setList;
    public static List<History4View> historyList;
    private static final int VIEWTYPE = 1;
    public static ActivitiesListAdapter activitiesListAdapter;
    public static HistoryListAdapter historyListAdapter;
    public static TagListAdapter tagListAdapter1;
    public static TagListAdapter tagListAdapter2;

    @Override
    protected void onStart() {
        super.onStart();
        /*******************AddTagFragment's data****************************************/
        updateTagList();
        updateSetList();
        /********************HistoryFragment's data**************************************/
        updateActivity();
        /********************刷新*********************************/
        changedAll();
    }

    public static void changedActivity() {
        if (HistoryFragment.adapter != null) {
            HistoryFragment.adapter.notifyDataSetChanged();
        }
    }

    public static void changedAll() {
        if (AddActivitiesFragment.adapter != null) {
            AddActivitiesFragment.adapter.notifyDataSetChanged();
        }
        if (AddActivitiesFragment.activitiesAdapter != null) {
            AddActivitiesFragment.activitiesAdapter.notifyDataSetChanged();
        }
        if (AddTagFragment.adapter != null) {
            AddTagFragment.adapter.notifyDataSetChanged();
        }
        changedActivity();
    }

    public static void updateActivity() {
        try {
            historyList = mDB.loadAllHistory();
            MyTime begin = historyList.get(0).getActivities().getBeginTime();
            String beginStr = SmallUtil.yearMouthDay(begin);
            History4View firstTitle = new History4View(VIEWTYPE, beginStr);
            historyList.add(0, firstTitle);
            for (int i = 1; i < historyList.size(); i++) {
                String then = SmallUtil.yearMouthDay(historyList.get(i).getActivities().getBeginTime());
                if (!then.equals(beginStr)) {
                    History4View thenTitle = new History4View(VIEWTYPE, then);
                    historyList.add(i, thenTitle);
                    i++;
                    beginStr = then;
                }
            }
        } catch (Resources.NotFoundException e) {
            historyList = new ArrayList<>();
        }
    }

    public static void updateTagList() {
        try {
            tagList = mDB.loadTagList();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            tagList = new ArrayList<>();
        }
    }

    public static void updateSetList() {
        try {
            setList = mDB.loadSetListNotEnded();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            setList = new ArrayList<>();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        //退出前应先更新SetTable，SetOrder
        mDB.updateSetOrder(view2Order(setList));
        for (int i = 0; i < setList.size(); i++) {
            mDB.updateSet(setList.get(i).getSet());
        }
    }

    private List<SetItemInOrder> view2Order(List<ActivityItem4View> customSet4ViewList) {
        List<SetItemInOrder> list = new ArrayList<>();
        for (int i = 0; i < customSet4ViewList.size(); i++) {
            SetItemInOrder setItemInOrder = new SetItemInOrder(
                    customSet4ViewList.get(i).getSet().getSetID(),
                    customSet4ViewList.get(i).getState());
            list.add(setItemInOrder);
        }
        return list;
    }
}
