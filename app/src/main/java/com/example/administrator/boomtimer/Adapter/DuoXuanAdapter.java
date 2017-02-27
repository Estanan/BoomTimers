package com.example.administrator.boomtimer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.boomtimer.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 时间多选块
 */
public class DuoXuanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> mList = new ArrayList<>();

    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    private boolean mIsSelectable = false;
    private ArrayList<String> mDatas;


    public DuoXuanAdapter() {
        initData();
        if (mDatas == null) {
            throw new IllegalArgumentException("model Data must not be null");
        }
        mList = mDatas;
    }

    //更新adpter的数据和选择状态
    public void updateDataSet() {
        this.mList = mDatas;
        mSelectedPositions = new SparseBooleanArray();
    }


    //获得选中条目的结果
    public ArrayList<String> getSelectedItem() {
        ArrayList<String> selectList = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(mList.get(i));
            }
        }
        return selectList;
    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 48; i++) {
            int h = (i + 1) / 2;
            String m = (i + 1) % 2 == 0 ? "00" : "30";
            Log.i("shijian", i + "");
            mDatas.add("" + h + ":" + m);
        }
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
        ((ListItemViewHolder) holder).checkBox.setText(mList.get(i));

        //checkBox的监听
        ((ListItemViewHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(i)) {
                    setItemChecked(i, false);
                } else {
                    setItemChecked(i, true);
                }
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
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ListItemViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder
        CheckBox checkBox;
        TextView mainTitle;

        ListItemViewHolder(View view) {
            super(view);
            this.checkBox = (CheckBox) view.findViewById(R.id.half_hour);

        }
    }
}





