package com.example.administrator.boomtimer.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.boomtimer.Adapter.ResyclerAdapter;
import com.example.administrator.boomtimer.R;


/**
 * Created by shady on 2017/1/15.
 */

public class IconPickerDialog extends Dialog {
    private static final String TAG = "ColorPickerDialog";
    Activity mContext;
    private static IconPickerDialog colorPickerDialog;
    private RecyclerView mRecyclerView;
    private ResyclerAdapter mAdapter;

    public IconPickerDialog(Activity context) {
        super(context);
        mContext = context;
    }

    public IconPickerDialog(Activity context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public static IconPickerDialog getInstance(Activity context) {
        colorPickerDialog = new IconPickerDialog(context);
        return colorPickerDialog;
    }

    // 利用interface来构造一个回调函数
    public interface ICustomDialogEventListener {
        public void customDialogEvent(int icon);
    }

    private ICustomDialogEventListener onCustomDialogEventListener;

    // 在构造函数中，设置进去回调函数
    public void setListener(Activity context,
                            ICustomDialogEventListener onCustomDialogEventListener) {
        mContext = context;
        this.onCustomDialogEventListener = onCustomDialogEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_iconpicker, null);
        this.setContentView(layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_select_icon);

        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mAdapter = new ResyclerAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ResyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Toast.makeText(mContext, data, Toast.LENGTH_LONG).show();
                onCustomDialogEventListener.customDialogEvent(Integer.parseInt(data));
                dismiss();
            }
        });
    }
}