package com.example.tourism.entity;

import java.util.List;

/**
 * 游记列表实体类
 * Name:laodai
 * Time:2019.10.17
 *
 */
public class TravelsBean {
    private String travelsId; //文章ID
    private String title; //文章内容
    private String href; //文章详情链接
    private String userPicUrl; //用户头像URL
    private String userHref; //用户主页链接
    private String userName; //用户姓名
    private String nickName; //用户别称
    private String time; //出发时间
    private String days; //天数
    private String photoNumber; //照片数量
    private String price; //金额
    private String relation; //关系
    private String browse; //浏览
    private String foubles; //点赞数量
    private String comment; //评论
    private String channel; //途径
    private String trips; //行程
    private List<String> picUrl; //发表图片URL

    /**
     * 无参构造
     */
    public TravelsBean() {
    }

    public String getTravelsId() {
        return travelsId;
    }

    public void setTravelsId(String travelsId) {
        this.travelsId = travelsId;
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

    public String getUserPicUrl() {
        return userPicUrl;
    }

    public void setUserPicUrl(String userPicUrl) {
        this.userPicUrl = userPicUrl;
    }

    public String getUserHref() {
        return userHref;
    }

    public void setUserHref(String userHref) {
        this.userHref = userHref;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPhotoNumber() {
        return photoNumber;
    }

    public void setPhotoNumber(String photoNumber) {
        this.photoNumber = photoNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getFoubles() {
        return foubles;
    }

    public void setFoubles(String foubles) {
        this.foubles = foubles;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTrips() {
        return trips;
    }

    public void setTrips(String trips) {
        this.trips = trips;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "[travelsId="+travelsId+", title="+title+", href="+href+", userPicUrl="+userPicUrl+", userHref="+userHref+", " +
                "userName="+userName+", nickName="+nickName+", time="+time+", days="+days+", photoNumber="+photoNumber+", " +
                "price="+price+", relation="+relation+", browse="+browse+", foubles="+foubles+", comment="+comment+", " +
                "channel="+channel+", trips="+trips+", picUrl="+picUrl+"]";
    }
}
