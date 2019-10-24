package com.example.tourism.entity;

/**
 * 用户作者实体类
 * Name:laodai
 * Time:2019.10.17
 */
public class AuthorBean {
    private String authorId; //用户编号
    private String name; //作者名字
    private String avatarPic; //作者头像
    private String score; //积分
    private String follow; //关注
    private String fans; //粉丝
    private TrHeadBean trHeadBean;

    public AuthorBean() {
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarPic() {
        return avatarPic;
    }

    public void setAvatarPic(String avatarPic) {
        this.avatarPic = avatarPic;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public TrHeadBean getTrHeadBean() {
        return trHeadBean;
    }

    public void setTrHeadBean(TrHeadBean trHeadBean) {
        this.trHeadBean = trHeadBean;
    }

    @Override
    public String toString() {
        return "[authorId="+authorId+", name="+name+", avatarPic="+avatarPic+", score="+score+", " +
                "follow="+follow+", fans="+fans+", trHeadBean="+trHeadBean+"]";
    }
}
