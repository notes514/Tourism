package com.example.tourism.entity;

public class Member {
    private Integer memberId;

    private Integer userId;

    private Integer memberType;

    private Integer memberIntegral;

    private Double memberDiscount;

    private String memberDescribe;

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getMemberIntegral() {
        return memberIntegral;
    }

    public void setMemberIntegral(Integer memberIntegral) {
        this.memberIntegral = memberIntegral;
    }

    public Double getMemberDiscount() {
        return memberDiscount;
    }

    public void setMemberDiscount(Double memberDiscount) {
        this.memberDiscount = memberDiscount;
    }

    public String getMemberDescribe() {
        return memberDescribe;
    }

    public void setMemberDescribe(String memberDescribe) {
        this.memberDescribe = memberDescribe;
    }
}