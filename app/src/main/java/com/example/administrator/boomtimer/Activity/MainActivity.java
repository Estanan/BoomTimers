package com.example.administrator.boomtimer.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.administrator.boomtimer.R.id.toolbar;

public class MainActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private TagListAdapter tagListAdapter;
    MyFragmentPagerAdapter pagerAdapter;

    ViewPager viewPager;
    TabLayout tabLayout;
    DrawerLayout mDrawerLayout;
    private ListView lvLeftMenu;
    private ActionBarDrawerToggle mDrawerToggle;
    private List lvs;
    //    private List<Tag> mDatas = init();
    private SimpleAdapter simpleAdapter;
    private ArrayList<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        mDB = DB.getInstance(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_left);
        lvLeftMenu = (ListView) findViewById(R.id.lv_left_menu);
        recyclerView = (RecyclerView) findViewById(R.id.timeblock);
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), 4, this);
//        viewPager.setOffscreenPageLimit(4);
        initData();
        setView();
    }

    //设置view
    private void setView() {
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setAdapter(new HomeAdapter());
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
        simpleAdapter = new SimpleAdapter(this, setData(), R.layout.listitem,
                new String[]{"title", "img"}, new int[]{R.id.menu_title, R.id.menu_image});
        lvLeftMenu.setOnItemClickListener(new MyListener());
        lvLeftMenu.setAdapter(simpleAdapter);
    }

    class MyListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Map<String, Object> mMap = (Map<String, Object>) simpleAdapter.getItem(position);
            Toast.makeText(MainActivity.this, mMap.get("title").toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            switch (mMap.get("title").toString()) {
                case "设置":
                    intent = new Intent(MainActivity.this, SettingActivity.class);
                    break;
                case "趋势":
                    intent = new Intent(MainActivity.this, HistoryActivity.class);
                    break;
                case "统计":
                    intent = new Intent(MainActivity.this, TrendeActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }

    }

    private List<Map<String, Object>> setData() {
        //map.put(参数名字,参数值)
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "设置");
        map.put("img", R.drawable.icon);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "趋势");
        map.put("img", R.drawable.icon);
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "统计");
        map.put("img", R.drawable.icon);
        list.add(map);
        return list;
    }

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

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 48; i++) {
            mDatas.add("" + i);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_home, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            Button tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (Button) view.findViewById(R.id.half_hour);
            }
        }
    }
}