package com.example.administrator.boomtimer.util;

import android.app.Application;
import android.content.Context;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.github.mikephil.charting.utils.Utils;

/**
 *
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "cMiTwMRV78RMY1mCt6uKIAO4");
    }

    public static Context getContext() {
        return context;
    }
}
