package com.example.tourism.entity;

import java.io.Serializable;

public class SeachBean implements Serializable {
    private String sTitle;
    private int index;
    private String sContent;

    public SeachBean(String sTitle, int index, String sContent) {
        this.sTitle = sTitle;
        this.index = index;
        this.sContent = sContent;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }
}
