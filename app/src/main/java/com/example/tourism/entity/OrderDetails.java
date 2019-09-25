package com.example.tourism.entity;

public class OrderDetails {
    private Integer orderDetailsId;

    private Integer orderId;

    private String orderDetailsContent;

    private Integer orderNumber;

    private String orderDetailsDescribe;

    public Integer getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Integer orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailsContent() {
        return orderDetailsContent;
    }

    public void setOrderDetailsContent(String orderDetailsContent) {
        this.orderDetailsContent = orderDetailsContent;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDetailsDescribe() {
        return orderDetailsDescribe;
    }

    public void setOrderDetailsDescribe(String orderDetailsDescribe) {
        this.orderDetailsDescribe = orderDetailsDescribe;
    }
}