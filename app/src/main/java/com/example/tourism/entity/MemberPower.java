package com.example.tourism.entity;

public class MemberPower {
    private Integer powerId;

    private Integer memberId;

    private String powerEquity;

    private String powerDescribe;

    public Integer getPowerId() {
        return powerId;
    }

    public void setPowerId(Integer powerId) {
        this.powerId = powerId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPowerEquity() {
        return powerEquity;
    }

    public void setPowerEquity(String powerEquity) {
        this.powerEquity = powerEquity;
    }

    public String getPowerDescribe() {
        return powerDescribe;
    }

    public void setPowerDescribe(String powerDescribe) {
        this.powerDescribe = powerDescribe;
    }
}