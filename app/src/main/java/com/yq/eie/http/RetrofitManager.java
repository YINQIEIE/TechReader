package com.yq.eie.http;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Administrator on 2017/10/13.
 */

public class RetrofitManager {

    private static RetrofitManager ourInstance = null;
    private Retrofit retrofit = null;
    private Retrofit retrofitNew = null;
    private static HttpService httpService;

    private static final String GANK_URL = "http://gank.io/api/";
    private static final String API_TING = "http://tingapi.ting.baidu.com/v1/restserver/";
    public static final String DOUBAN_URL = "https://api.douban.com/";

    public static RetrofitManager getInstance() {
        if (null == ourInstance)
            ourInstance = new RetrofitManager();
        return ourInstance;
    }

    private RetrofitManager() {
    }

    /**
     * baseUrl 有变化时调用此方法
     *
     * @param newBaseUrl 新的 baseUrl
     * @return
     */
    public Retrofit getNewRetrofit(String newBaseUrl) {
        retrofitNew = new Retrofit.Builder()
                .baseUrl(newBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofitNew;
    }

    private static Retrofit getGankRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(GANK_URL)
                .client(OkHttpClientManager.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit getBannerRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_TING)
                .client(OkHttpClientManager.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static Retrofit getDouBanRetrofit(Context context) {
        return new Retrofit.Builder()
                .baseUrl(DOUBAN_URL)
                .client(OkHttpClientManager.getHttpsClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static HttpService getGankHttpService() {
        return getGankRetrofit().create(HttpService.class);
    }

    public static HttpService getBannerHttpService() {
        return getBannerRetrofit().create(HttpService.class);
    }

    public static HttpService getDouBanHttpService(Context context) {
        return getDouBanRetrofit(context).create(HttpService.class);
    }

}
