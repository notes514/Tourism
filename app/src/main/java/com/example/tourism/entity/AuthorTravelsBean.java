package com.example.tourism.entity;

public class AuthorTravelsBean {
    private String releaseDate; //游记发布日期
    private String label; //标签
    private String title; //标题
    private String href; //文章链接地址
    private String picsUrl; //文章图片地址
    private String tripDate; //游记出行日期
    private String days; //出游天数
    private String picNums; //发表图片数
    private String browse; //浏览
    private String foubles; //点赞数量
    private String comment; //评论

    public AuthorTravelsBean() {
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getPicsUrl() {
        return picsUrl;
    }

    public void setPicsUrl(String picsUrl) {
        this.picsUrl = picsUrl;
    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getPicNums() {
        return picNums;
    }

    public void setPicNums(String picNums) {
        this.picNums = picNums;
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

    @Override
    public String toString() {
        return "[releaseDate="+releaseDate+", label="+label+", title="+title+", href="+href+", picsUrl="+picsUrl+", " +
                "tripDate="+tripDate+", days="+days+", picNums="+picNums+", browse="+browse+", foubles="+foubles+", " +
                "comment="+comment+"]";
    }
}
