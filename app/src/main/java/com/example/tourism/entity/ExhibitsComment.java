package com.example.tourism.entity;

public class ExhibitsComment {
    private Integer exhibitsCommentId;

    private Integer exhibitsId;

    private String exhibitsCommentContent;

    private Integer commentPraisePoints;

    public Integer getExhibitsCommentId() {
        return exhibitsCommentId;
    }

    public void setExhibitsCommentId(Integer exhibitsCommentId) {
        this.exhibitsCommentId = exhibitsCommentId;
    }

    public Integer getExhibitsId() {
        return exhibitsId;
    }

    public void setExhibitsId(Integer exhibitsId) {
        this.exhibitsId = exhibitsId;
    }

    public String getExhibitsCommentContent() {
        return exhibitsCommentContent;
    }

    public void setExhibitsCommentContent(String exhibitsCommentContent) {
        this.exhibitsCommentContent = exhibitsCommentContent;
    }

    public Integer getCommentPraisePoints() {
        return commentPraisePoints;
    }

    public void setCommentPraisePoints(Integer commentPraisePoints) {
        this.commentPraisePoints = commentPraisePoints;
    }
}