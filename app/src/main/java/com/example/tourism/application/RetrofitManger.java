package com.example.tourism.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.Date;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit网络请求管理类
 *
 */
public class RetrofitManger {
    public static RetrofitManger retrofitManger = null;
    private Retrofit retrofit;
    private Gson gson;

    /**
     * 请求单例(单例模式)
     * @return
     */
    public static RetrofitManger getInstance() {
        if (retrofitManger == null) retrofitManger = new RetrofitManger();
        return retrofitManger;
    }

    /**
     * 构造方法
     */
    public RetrofitManger() {
    }

    /**
     * 请求头
     * @param url 传入的URL(变化的)
     * @return
     */
    public Retrofit getRetrofit(String url) {
        //创建Retrofit请求对象
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .baseUrl(url)
                .build();
        return retrofit;
    }

    /**
     * 文件下载请求头
     * @param url
     * @return
     */
    public Retrofit getFileRetrofit(String url) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .build();
        return retrofit;
    }

    /**
     * Gson无法解析数位太长的时间格式：
     * 解决方案：自定义和注册Gson适配器
     *
     * 注：涉及到datetime字段解析时需要使用该方法
     */
    public Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json1, typeOfT, context) -> new Date(json1.getAsJsonPrimitive().getAsLong()));
        gson = builder.create();
        return gson;
    }

}
