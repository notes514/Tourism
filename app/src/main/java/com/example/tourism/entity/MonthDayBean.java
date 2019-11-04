package com.example.tourism.entity;

/**
 * 商品详情月份日期价格类
 * Name:laodai
 * Time:2019.10.29
 */
public class MonthDayBean {
    private String month;
    private String price;

    public MonthDayBean(String month, String price) {
        this.month = month;
        this.price = price;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
