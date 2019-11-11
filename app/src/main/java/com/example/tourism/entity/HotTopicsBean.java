package com.example.tourism.entity;

/**
 * 热门主题类
 */
public class HotTopicsBean {
    private String hPic;
    private String hTheme;
    private String hExplainOne;
    private String hExplainTwo;
    private String hExplainThree;

    public HotTopicsBean(String hPic, String hTheme, String hExplainOne, String hExplainTwo, String hExplainThree) {
        this.hPic = hPic;
        this.hTheme = hTheme;
        this.hExplainOne = hExplainOne;
        this.hExplainTwo = hExplainTwo;
        this.hExplainThree = hExplainThree;
    }

    public String gethPic() {
        return hPic;
    }

    public void sethPic(String hPic) {
        this.hPic = hPic;
    }

    public String gethTheme() {
        return hTheme;
    }

    public void sethTheme(String hTheme) {
        this.hTheme = hTheme;
    }

    public String gethExplainOne() {
        return hExplainOne;
    }

    public void sethExplainOne(String hExplainOne) {
        this.hExplainOne = hExplainOne;
    }

    public String gethExplainTwo() {
        return hExplainTwo;
    }

    public void sethExplainTwo(String hExplainTwo) {
        this.hExplainTwo = hExplainTwo;
    }

    public String gethExplainThree() {
        return hExplainThree;
    }

    public void sethExplainThree(String hExplainThree) {
        this.hExplainThree = hExplainThree;
    }
}
