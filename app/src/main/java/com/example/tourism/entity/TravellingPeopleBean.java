package com.example.tourism.entity;

import java.io.Serializable;

/**
 * 出行人实体类
 */
public class TravellingPeopleBean implements Serializable {
    private String tType;
    private String tName;
    private String tIdentitycard;
    private int type; //返回类型

    public TravellingPeopleBean(String tType, String tName, String tIdentitycard, int type) {
        this.tType = tType;
        this.tName = tName;
        this.tIdentitycard = tIdentitycard;
        this.type = type;
    }

    public String gettType() {
        return tType;
    }

    public void settType(String tType) {
        this.tType = tType;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettIdentitycard() {
        return tIdentitycard;
    }

    public void settIdentitycard(String tIdentitycard) {
        this.tIdentitycard = tIdentitycard;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
