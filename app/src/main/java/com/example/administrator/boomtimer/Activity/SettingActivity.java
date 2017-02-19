package com.example.administrator.boomtimer.Activity;

import android.os.Bundle;

import com.example.administrator.boomtimer.R;

/**
 * Created by shady on 2017/2/19.
 * 设置界面
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_history);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history;
    }
}
