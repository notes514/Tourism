package com.example.tourism.entity;

import java.io.Serializable;

/**
 * 游记目的地实体类
 */
public class TrHeadBean implements Serializable {
    private String title;
    private String href;

    public TrHeadBean() {
    }

    public TrHeadBean(String title, String href) {
        this.title = title;
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "[title="+title+", href="+href+"]";
    }
}
