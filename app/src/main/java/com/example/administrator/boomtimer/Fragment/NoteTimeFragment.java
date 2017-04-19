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
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.boomtimer.Activity.MainActivity;
import com.example.administrator.boomtimer.Adapter.TagListAdapter;
import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.model.Tag;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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

    @Override
    public int getLayout() {
        return R.layout.fragment_notetime;
    }

    @Override
    public void initViews(View view) {
        mChecked = (TextView) view.findViewById(R.id.add_content);
        listShow = (Button) view.findViewById(R.id.list_show);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new java.util.Date());
        mTvCurrentTime = (TextView) view.findViewById(R.id.tv_date);
        mTvCurrentTime.setText(date);
        listShow.setOnClickListener(this);
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
        }
    }


    public class SelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<String> mList = new ArrayList<>();

        private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
        private boolean mIsSelectable = false;
        private ArrayList<String> mDatas;


        public SelectAdapter() {
            initAdapterData();
            if (mDatas == null) {
                throw new IllegalArgumentException("model Data must not be null");
            }
            mList = mDatas;
        }

        private void initAdapterData() {
            mDatas = new ArrayList<String>();
            for (int i = 0; i < 48; i++) {
                int h = (i + 1) / 2;
                String m = (i + 1) % 2 == 0 ? "00" : "30";
                Log.i("shijian", i + "");
                mDatas.add("" + h + ":" + m);
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
                    selectList.add(mDatas.get(i));
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
            ((ListItemViewHolder) holder).checkBox.setText(mDatas.get(i));

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
