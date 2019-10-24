package com.example.tourism.entity;

public class OrderDetails {
    private Integer orderDetailsId;

    private Integer orderId;

    private Integer contactsId;

    private Integer passengerId;

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

    public Integer getContactsId() {
        return contactsId;
    }

    public void setContactsId(Integer contactsId) {
        this.contactsId = contactsId;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public String getOrderDetailsDescribe() {
        return orderDetailsDescribe;
    }

    public void setOrderDetailsDescribe(String orderDetailsDescribe) {
        this.orderDetailsDescribe = orderDetailsDescribe;
    }
}