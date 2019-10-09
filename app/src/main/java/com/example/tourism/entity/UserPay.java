package com.example.tourism.entity;

import java.util.Date;

public class UserPay {
    private Integer payId;

    private Integer userId;

    private Integer memberId;

    private Integer orderId;

    private String paySituation;

    private Integer payMode;

    private Date payTime;

    private Double discountMoney;

    private Double shouldMoney;

    private Double realMoney;

    private Double debtMoney;

    private String paymentDescribe;

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaySituation() {
        return paySituation;
    }

    public void setPaySituation(String paySituation) {
        this.paySituation = paySituation;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Double getShouldMoney() {
        return shouldMoney;
    }

    public void setShouldMoney(Double shouldMoney) {
        this.shouldMoney = shouldMoney;
    }

    public Double getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Double realMoney) {
        this.realMoney = realMoney;
    }

    public Double getDebtMoney() {
        return debtMoney;
    }

    public void setDebtMoney(Double debtMoney) {
        this.debtMoney = debtMoney;
    }

    public String getPaymentDescribe() {
        return paymentDescribe;
    }

    public void setPaymentDescribe(String paymentDescribe) {
        this.paymentDescribe = paymentDescribe;
    }
}