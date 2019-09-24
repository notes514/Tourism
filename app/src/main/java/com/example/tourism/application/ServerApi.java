package com.example.tourism.application;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * 网络请求接口
 */
public interface ServerApi {
    /**
     * GET异步请求
     * @param url 传入的URL
     * @param map 参数
     * @return ResponseBody
     */
    @GET()
    Call<ResponseBody> getASync(@Url String url, @QueryMap Map<String, Object> map);

    /**
     * POST异步请求
     * @param url 传入的URL
     * @param map 参数
     * @return
     */
    @POST()
    Call<ResponseBody> postASync(@Url String url, @Body Map<String, Object> map);

    /**
     * 文件下载
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> retrofitDownloadFile(@Url String url);

    /**
     * 图片上传
     * @param info 上传文本信息
     * @param map 上传多张图片
     * @return
     */
//    @Multipart
//    @POST("report/saveReport")
//    Observable<ReportResponse> saveReport(@Part("info") RequestBody info,@PartMap Map<String,RequestBody> map);

}
