package com.example.tourism.common;

public class RequestURL {
    //参数key: 提示
    public static final String TIPS = "TIPS";
    //参数key: 返回结果
    public static final String RESULT = "RESULT";
    //参数key: 返回数据1
    public static final String ONE_DATA = "ONE_DETAIL";
    //参数key: 返回数据2
    public static final String TWO_DATA = "TWO_DETAIL";
    //参数key: 返回数据3
    public static final String THREE_DATA = "THREE_DATA";
    //参数key: 返回数据4
    public static final String FOUR_DATA = "FOUR_DATA";

    //用户编号(唯一)
    public static String vUserId = "";

    //http://47.100.47.54/TourismServer-1.0-SNAPSHOT(服务器地址)
    //IP地址,端口号
    public static final String ip_port = "http://192.168.2.227:8080/api/";
    //IP地址，视频路径
    public static final String ip_video = "http://192.168.2.227:8080/video";
    //IP地址,图片路径
    public static final String ip_images = "http://192.168.2.227:8080/";
    //首页地址
    public static final String html = "https://travel.qunar.com/";
    //攻略库首页地址
    public static final String library_html = "travelbook/list.htm?order=hot_heat";
    //用户详情页地址
    public static final String user_html = "https://travel.qunar.com/space/";
    //攻略详情页地址
    public static final String stra_html = "https://travel.qunar.com/youji/7541376";

    //酒店预订地址
    public static final String hotel_url = "https://hotel.qunar.com/";
    //机票预订地址
    public static final String flight_url = "https://flight.qunar.com/";
    //火车票预订地址
    public static final String train_url = "https://train.qunar.com/";
    //门票预订地址
    public static final String piao_url = "https://piao.qunar.com/";
    //车票预订地址
    public static final String bus_url = "http://bus.qunar.com/";
}
