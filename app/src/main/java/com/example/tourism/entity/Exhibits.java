package com.example.tourism.entity;

import java.util.List;

public class Exhibits {
    private Integer exhibitsId;

    private Integer exhibitionAreaId;

    private String exhibitsName;

    private String exhibitsVideoUrl;

    private String exhibitsInformation;

    private String exhibitsAuthor;

    private String cellPhoneNumber;

    private String guidanceTeacher;

    private String teamMembers;

    private List<FabulousDetails> fabulousDetailsList;

    private int likeCount;

    private List<ExhibitsPic> exhibitsPicList;

    private List<ExhibitsComment> exhibitsCommentList;

    public List<ExhibitsComment> getExhibitsCommentList() {
        return exhibitsCommentList;
    }

    public void setExhibitsCommentList(List<ExhibitsComment> exhibitsCommentList) {
        this.exhibitsCommentList = exhibitsCommentList;
    }

    public List<ExhibitsPic> getExhibitsPicList() {
        return exhibitsPicList;
    }

    public void setExhibitsPicList(List<ExhibitsPic> exhibitsPicList) {
        this.exhibitsPicList = exhibitsPicList;
    }

    public int getLikeCount() {
        int sum = 0;
        for (int i = 0; i < getFabulousDetailsList().size(); i++) {
            if (getFabulousDetailsList().get(i).getFlag()==1){
                sum++;
            }
        }
        return sum;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public List<FabulousDetails> getFabulousDetailsList() {
        return fabulousDetailsList;
    }

    public void setFabulousDetailsList(List<FabulousDetails> fabulousDetailsList) {
        this.fabulousDetailsList = fabulousDetailsList;
    }

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

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getGuidanceTeacher() {
        return guidanceTeacher;
    }

    public void setGuidanceTeacher(String guidanceTeacher) {
        this.guidanceTeacher = guidanceTeacher;
    }

    public String getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(String teamMembers) {
        this.teamMembers = teamMembers;
    }
}