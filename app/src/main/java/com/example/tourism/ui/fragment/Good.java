package com.example.tourism.ui.fragment;

import java.util.ArrayList;
import java.util.List;

public class Good {
    private int imagePic;
    private String accountName;
    private String time;
    private String content;
    private List<GoodPic> goodPicList;
    private int collection;

    public Good(int imagePic, String accountName, String time, String content, List<GoodPic> goodPicList, int collection) {
        this.imagePic = imagePic;
        this.accountName = accountName;
        this.time = time;
        this.content = content;
        this.goodPicList = goodPicList;
        this.collection = collection;
    }

    public int getImagePic() {
        return imagePic;
    }

    public void setImagePic(int imagePic) {
        this.imagePic = imagePic;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<GoodPic> getGoodPicList() {
        return goodPicList;
    }

    public void setGoodPicList(List<GoodPic> goodPicList) {
        this.goodPicList = goodPicList;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }
}
