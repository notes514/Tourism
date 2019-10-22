package com.example.tourism.utils;

/**
 * 图片地址截取工具类
 */
public class ImageUtils {

    /**
     * 字符串图片地址截断 这边按照?号分隔
     * @param picUrl
     * @return 返回字符串数据
     */
    public static String getCutImgUrl(String picUrl) {
        if (picUrl.contains("?")) { //判断该字符串是否含有?号，如果含有，则进行截取
            //因为正则表达式中的?号是特殊字符，所以要进行转义，在Java中转义要加上//
            String[] urlsStrings = picUrl.split("\\?");
            return urlsStrings[0];
        }
        return picUrl;
    }

    /**
     * 字符串图片地址截断 这边按照 _ 分隔
     * @param picUrl
     * @return
     */
    public static String getCutPicUrl(String picUrl) {
        if (picUrl.contains("_")) {
            String[] array = picUrl.split("_");
            return array[0];
        }
        return picUrl;
    }

}
