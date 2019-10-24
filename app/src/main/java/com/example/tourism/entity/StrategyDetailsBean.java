package com.example.tourism.entity;

import java.util.List;

/**
 * 攻略详情实体类
 * Name:laodai
 * Time:2019.10.23
 */
public class StrategyDetailsBean {
    private String strategyId; //攻略详情编号
    private String backgroundUrl; //背景URL地址
    private String content; //web网页URL地址
    private String share; //分享
    private String collection; //收藏
    private List<String> forewordList; //日期行程数据集
    private TravelsBean travelsBean; //列表信息

    /**
     * 无参构造
     */
    public StrategyDetailsBean() {
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getBackgroundUrl() {
        return backgroundUrl;
    }

    public void setBackgroundUrl(String backgroundUrl) {
        this.backgroundUrl = backgroundUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public List<String> getForewordList() {
        return forewordList;
    }

    public void setForewordList(List<String> forewordList) {
        this.forewordList = forewordList;
    }

    public TravelsBean getTravelsBean() {
        return travelsBean;
    }

    public void setTravelsBean(TravelsBean travelsBean) {
        this.travelsBean = travelsBean;
    }

    @Override
    public String toString() {
        return "[strategyId="+strategyId+", backgroundUrl="+backgroundUrl+", content="+content+", " +
                "share="+share+", collection="+collection+", forewordList="+forewordList+", " +
                "travelsBean="+travelsBean+"]";
    }
}
