package com.example.administrator.boomtimer.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.boomtimer.R;
import com.example.administrator.boomtimer.model.SelectIconItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ResyclerAdapter extends RecyclerView.Adapter<ResyclerAdapter.MyViewHolder> {
    private Context context;

    public ResyclerAdapter(Context context) {
        initDatas();
        this.context = context;
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_icon, parent,
                false));

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SelectIconItem item = mItems.get(position);
        holder.imageView.setBackgroundResource(getResId(item.getItem(), R.drawable.class));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, getResId(item.getItem(), R.drawable.class) + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.select_item_icon);
        }
    }


    private final static int VIEW_TYPE_HEADER = 0;
    private final static int VIEW_TYPE_CONTENT = 1;
    private static final int LINEAR = 0;
    private List<SelectIconItem> mItems;

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return R.drawable.ic_launcher;
        }
    }

    private String one2three(int i) {
        if (i < 10) {
            return "00" + i;
        } else if (i < 100) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    private void initDatas() {
        int sectionManager = -1;
        int itemCount = 0;
        int sectionFirstPosition = 0;
        mItems = new ArrayList<>();
        String[] strings = {"Default", "Eat and Cook", "Family",
                "Finance and Shopping", "Home and Personal Care",
                "Music", "Relax and Party", "Sport", "Study", "Transport and Travel"};
        SelectIconItem item;
        SelectIconItem header;
        for (int i = 0; i < strings.length; i++) {
            sectionManager = (sectionManager + 1) % 2;
            switch (i) {
                case 0:
                    for (int j = 1; j <= 228; j++) {
                        item = new SelectIconItem("cat_" + j, VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 1:
                    for (int j = 1; j <= 74; j++) {
                        item = new SelectIconItem("ec_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 2:
                    for (int j = 1; j <= 56; j++) {
                        item = new SelectIconItem("fam_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 3:
                    for (int j = 1; j <= 43; j++) {
                        item = new SelectIconItem("fs_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 4:
                    for (int j = 1; j <= 108; j++) {
                        item = new SelectIconItem("hpcd_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 5:
                    for (int j = 1; j <= 48; j++) {
                        item = new SelectIconItem("mi_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 6:
                    for (int j = 1; j <= 53; j++) {
                        item = new SelectIconItem("rph_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 7:
                    for (int j = 1; j <= 96; j++) {
                        item = new SelectIconItem("sp_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 8:
                    for (int j = 1; j <= 67; j++) {
                        item = new SelectIconItem("st_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
                case 9:
                    for (int j = 1; j <= 59; j++) {
                        item = new SelectIconItem("tt_" + one2three(j), VIEW_TYPE_CONTENT,
                                sectionManager, sectionFirstPosition);
                        mItems.add(item);
                        itemCount++;
                    }
                    break;
            }
            sectionFirstPosition = i + itemCount;
            header = new SelectIconItem(strings[i], VIEW_TYPE_HEADER,
                    sectionManager, sectionFirstPosition);
            mItems.add(header);
        }
    }
}