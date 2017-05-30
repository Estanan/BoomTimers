package com.example.administrator.boomtimer.network;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/1/16.
 */
public class BaseCallBack<T> implements Callback<T> {
    private static final String TAG = "BaseCallBack";
 
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.d(TAG, "onResponse: do success cmd!");
        WebRetrofitService.getInstance().removeRequest(call);
 
    }
 
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d(TAG, "onFailure: do failure cmd!");
        WebRetrofitService.getInstance().removeRequest(call);
 
    }
}