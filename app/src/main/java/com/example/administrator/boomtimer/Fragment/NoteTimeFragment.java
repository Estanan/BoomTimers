package com.example.administrator.boomtimer.Fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.boomtimer.Activity.MainActivity;
import com.example.administrator.boomtimer.Adapter.TagListAdapter;
import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.model.Activities;
import com.example.administrator.boomtimer.model.ActivityItem4View;
import com.example.administrator.boomtimer.model.MyTime;
import com.example.administrator.boomtimer.model.NoteData;
import com.example.administrator.boomtimer.model.Set;
import com.example.administrator.boomtimer.model.Tag;
import com.example.administrator.boomtimer.util.Constant;
import com.example.administrator.boomtimer.util.SmallUtil;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by shady on 2017/4/17.
 */

public class NoteTimeFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView tagRecyclerView;
    private TextView mChecked;
    private Button listShow;
    private TextView mTvCurrentTime;
    private int currentColor;
    private boolean checkTag = false;
    private Map<Integer, String> mMap;
    private int currentTagId;
    private String currentTagName;
    private Button btnOk;
    private ArrayList<NoteData> mDatas;

    @Override
    public int getLayout() {
        return R.layout.fragment_notetime;
    }

    @Override
    public void initViews(View view) {
        mChecked = (TextView) view.findViewById(R.id.add_content);
        listShow = (Button) view.findViewById(R.id.list_show);
        btnOk = (Button) view.findViewById(R.id.ok);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        mTvCurrentTime = (TextView) view.findViewById(R.id.tv_date);
        mTvCurrentTime.setText(date);
        listShow.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        recyclerView = (RecyclerView) view.findViewById(R.id.timeblock);
        tagRecyclerView = (RecyclerView) view.findViewById(R.id.list_tag);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(new SelectAdapter());
        tagRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TagListAdapter tagListAdapter = new TagListAdapter(getActivity(), false);
        tagRecyclerView.setAdapter(tagListAdapter);
        tagListAdapter.setOnRecyclerViewItemClickListener(new TagListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Tag tag) {
                checkTag = true;
                currentTagId = tag.getId();
                currentTagName = tag.getName();
                currentColor = tag.getColor();
                Toast.makeText(getActivity(), tag.getName() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.list_show:
                tagRecyclerView.setVisibility(tagRecyclerView.getVisibility() != View.GONE ? View.GONE : View.VISIBLE);
                listShow.setText(listShow.getText().equals("-") ? "+" : "-");
                break;
            case R.id.ok:
                addActivity();
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_LONG).show();

                break;
        }
    }

    private void addActivity() {
        MyTime time = SmallUtil.gainTime();
        MyTime time2 = SmallUtil.gainTime();

        MyTime startTime = new MyTime();
        MyTime endTime = new MyTime();

        mDatas.size();
        for (int i = 0; i < mDatas.size(); i++) {
            if (mDatas.get(i).getTagid() != 0) {
                String hour = mDatas.get(i).getName().split(":")[0];
                startTime = time;
                startTime.setHour(Integer.parseInt(hour));
                startTime.setMinute(0);
                startTime.setSecond(0);

                endTime = time2;
                if (i % 2 != 0) {
                    endTime.setHour(Integer.parseInt(hour));
                    endTime.setMinute(30);
                    endTime.setSecond(0);
                } else {
                    endTime.setHour(Integer.parseInt(hour) + 1);
                    endTime.setMinute(0);
                    endTime.setSecond(0);
                }

                Set set = new Set(mDatas.get(i).getTagid(), "", 0, time);
                int setId = MainActivity.mDB.saveSet(set);
                Activities activities = new Activities(setId,
                        startTime,
                        endTime,
                        30 * 60);
                MainActivity.mDB.saveActivities(activities);
            }
        }
    }


    public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<NoteData> mList = new ArrayList<>();

        private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
        private boolean mIsSelectable = false;
        private NoteData mNotedata;


        public SelectAdapter() {
            initAdapterData();
            if (mDatas == null) {
                throw new IllegalArgumentException("model Data must not be null");
            }
            mList = mDatas;
        }

        private void initAdapterData() {
            mDatas = new ArrayList<NoteData>();
            for (int i = 0; i < 48; i++) {
                mNotedata = new NoteData();
                int h = (i + 1) / 2;
                String m = (i + 1) % 2 == 0 ? "00" : "30";
                Log.i("shijian", i + "");
                mNotedata.setName("" + h + ":" + m);
                mDatas.add(mNotedata);
            }
        }

        //更新adpter的数据和选择状态
        public void updateDataSet(ArrayList<String> list) {
            mSelectedPositions = new SparseBooleanArray();
            mChecked.setText("已选择" + 0 + "小时");
        }


        //获得选中条目的结果
        public ArrayList<String> getSelectedItem() {
            ArrayList<String> selectList = new ArrayList<>();
            for (int i = 0; i < mDatas.size(); i++) {
                if (isItemChecked(i)) {
                    selectList.add(mDatas.get(i).getName());
                }
            }
            return selectList;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false);
            return new ListItemViewHolder(itemView);
        }

        //设置给定位置条目的选择状态
        private void setItemChecked(int position, boolean isChecked) {
            mSelectedPositions.put(position, isChecked);
        }

        //根据位置判断条目是否选中
        private boolean isItemChecked(int position) {
            return mSelectedPositions.get(position);
        }

        //根据位置判断条目是否可选
        private boolean isSelectable() {
            return mIsSelectable;
        }

        //设置给定位置条目的可选与否的状态
        private void setSelectable(boolean selectable) {
            mIsSelectable = selectable;
        }

        //绑定界面，设置监听
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
            //设置条目状态
            ((ListItemViewHolder) holder).checkBox.setChecked(isItemChecked(i));
            ((ListItemViewHolder) holder).checkBox.setText(mDatas.get(i).getName());

            //checkBox的监听
            ((ListItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkTag) {
                        mChecked.setText("请先选择事件类型");
                        return;
                    }
                    if (isItemChecked(i)) {
                        setItemChecked(i, false);
                    } else {
                        setItemChecked(i, true);
                    }
                    ((ListItemViewHolder) holder).checkBox.setBackgroundColor(isItemChecked(i) ? currentColor : 0);
                    ((ListItemViewHolder) holder).checkBox.setText(isItemChecked(i) ? mDatas.get(i).getName() + currentTagName : mDatas.get(i).getName());
                    mDatas.get(i).setTagid(currentTagId);
                    mChecked.setText("已选择" + getSelectedItem().size() * 0.5 + "小时");
                }
            });

            //条目view的监听
            ((ListItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isItemChecked(i)) {
                        setItemChecked(i, false);
                    } else {
                        setItemChecked(i, true);
                    }
                    notifyItemChanged(i);
                    mChecked.setText("已选择" + getSelectedItem().size() * 0.5 + "小时");
                }
            });


        }

        @Override
        public int getItemCount() {
            return mDatas == null ? 0 : mDatas.size();
        }

        public class ListItemViewHolder extends RecyclerView.ViewHolder {
            //ViewHolder
            CheckBox checkBox;

            ListItemViewHolder(View view) {
                super(view);
                this.checkBox = (CheckBox) view.findViewById(R.id.half_hour);

            }
        }
    }

}
