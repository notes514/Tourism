package com.example.tourism.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

@Entity
public class SeachContent {
    @Id(autoincrement = true)
    private Long scId;

    @NotNull
    private String url;

    @NotNull
    private String content;

    @NotNull
    private Date sDate;

    @Generated(hash = 120995519)
    public SeachContent(Long scId, @NotNull String url, @NotNull String content,
            @NotNull Date sDate) {
        this.scId = scId;
        this.url = url;
        this.content = content;
        this.sDate = sDate;
    }

    @Generated(hash = 945782743)
    public SeachContent() {
    }

    public Long getScId() {
        return this.scId;
    }

    public void setScId(Long scId) {
        this.scId = scId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSDate() {
        return this.sDate;
    }

    public void setSDate(Date sDate) {
        this.sDate = sDate;
    }


}
