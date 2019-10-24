package com.example.tourism.entity;

public class Exhibits {
    private Integer exhibitsId;

    private Integer exhibitionAreaId;

    private String exhibitsName;

    private String exhibitsVideoUrl;

    private String exhibitsInformation;

    private String exhibitsAuthor;

    private Integer exhibitsPraisePoints;

    public Integer getExhibitsId() {
        return exhibitsId;
    }

    public void setExhibitsId(Integer exhibitsId) {
        this.exhibitsId = exhibitsId;
    }

    public Integer getExhibitionAreaId() {
        return exhibitionAreaId;
    }

    public void setExhibitionAreaId(Integer exhibitionAreaId) {
        this.exhibitionAreaId = exhibitionAreaId;
    }

    public String getExhibitsName() {
        return exhibitsName;
    }

    public void setExhibitsName(String exhibitsName) {
        this.exhibitsName = exhibitsName;
    }

    public String getExhibitsVideoUrl() {
        return exhibitsVideoUrl;
    }

    public void setExhibitsVideoUrl(String exhibitsVideoUrl) {
        this.exhibitsVideoUrl = exhibitsVideoUrl;
    }

    public String getExhibitsInformation() {
        return exhibitsInformation;
    }

    public void setExhibitsInformation(String exhibitsInformation) {
        this.exhibitsInformation = exhibitsInformation;
    }

    public String getExhibitsAuthor() {
        return exhibitsAuthor;
    }

    public void setExhibitsAuthor(String exhibitsAuthor) {
        this.exhibitsAuthor = exhibitsAuthor;
    }

    public Integer getExhibitsPraisePoints() {
        return exhibitsPraisePoints;
    }

    public void setExhibitsPraisePoints(Integer exhibitsPraisePoints) {
        this.exhibitsPraisePoints = exhibitsPraisePoints;
    }
}