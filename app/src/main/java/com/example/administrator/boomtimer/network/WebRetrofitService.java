package com.example.administrator.boomtimer.network;

import com.example.administrator.boomtimer.model.WeatherBean;
import com.example.administrator.boomtimer.util.Constant;
import com.example.administrator.boomtimer.util.SmallUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/1/16.
 * 网络请求服务类
 */
public class WebRetrofitService {
    private static final String TAG = "WebRetrofitService";

    public static WebRetrofitService retrofitService;
    private static RequestServes retrofitInterface = null;
    private HashMap<Object, Call> reqMaps = new HashMap<Object, Call>();

    private WebRetrofitService() {
        initRetrofit();
    }


    public Call getWeather(Object reqTag, Map<String, String> map, BaseCallBack<WeatherBean> baseCallBack) {
        Call<WeatherBean> call = retrofitInterface.getWeather(map);
        addRequest(reqTag, call, baseCallBack);
        return call;
    }


    /**
     * 获取WebRetrofitService实例
     *
     * @return
     */
    public synchronized static WebRetrofitService getInstance() {
        if (retrofitService == null) {
            retrofitService = new WebRetrofitService();
        }
        return retrofitService;
    }

    private void initRetrofit() {
//打印LOG
        //设置Header头传参数
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader("platform", "android")
                        .addHeader("Cache-Control", "max-age=640000")
                        .addHeader("Signature-Time", SmallUtil.getSignatureTime())
                        .addHeader("Signature", SmallUtil.getSignature())
                        .addHeader("Language", "SC")
                        .build();

                okhttp3.Response response = chain.proceed(newRequest);

                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(charset);
                }
                String bodyString = buffer.clone().readString(charset);


                return response;
            }
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(headerInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitInterface = retrofit.create(RequestServes.class);
    }

    /**
     * 取消所有网络请求
     */
    public synchronized void cancelAllRequest() {
        Iterator iter = reqMaps.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Call<BaseResponse> call = (Call<BaseResponse>) entry.getValue();
            call.cancel();
        }
        reqMaps.clear();
    }

    /**
     * 增加网络请求
     *
     * @param reqTag
     * @param call
     */
    public synchronized void addRequest(Object reqTag, Call call, BaseCallBack cb) {
        call.enqueue(cb);
        reqMaps.put(reqTag, call);
    }

    /**
     * 取消网络请求
     *
     * @param reqTag 请求TAG
     */
    public synchronized void cancelRequest(final Object reqTag) {
        Call<BaseResponse> call = (Call<BaseResponse>) reqMaps.get(reqTag);
        if (call != null) {
            call.cancel();
            reqMaps.remove(reqTag);
        }
    }

    /**
     * 从队列中移除Call对象
     *
     * @param call
     */
    public synchronized void removeRequest(final Call call) {
        Iterator iter = reqMaps.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            Call myCall = (Call) entry.getValue();
            if (myCall.equals(call)) {
                reqMaps.remove(key);
                return;
            }
        }
    }

}