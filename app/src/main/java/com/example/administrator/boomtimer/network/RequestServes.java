package com.example.administrator.boomtimer.network;

import com.example.administrator.boomtimer.model.WeatherBean;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by shady on 2017/5/30.
 */

public interface RequestServes {
    @GET("v5/now")
    Call<WeatherBean> getWeather(@QueryMap Map<String, String> map);



}
