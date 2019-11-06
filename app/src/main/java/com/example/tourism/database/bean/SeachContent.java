package com.example.tourism.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SeachContent {

    @Id(autoincrement = true)
    private Long scId;
    @NotNull
    private String url;
    @NotNull
    private String content;
    @Generated(hash = 167749554)
    public SeachContent(Long scId, @NotNull String url, @NotNull String content) {
        this.scId = scId;
        this.url = url;
        this.content = content;
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

}
