package com.example.tourism.entity;

import java.util.List;

public class MonthBean {
    private String title;
    private int year;
    private List<DateBean> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<DateBean> getList() {
        return list;
    }

    public void setList(List<DateBean> list) {
        this.list = list;
    }

}
