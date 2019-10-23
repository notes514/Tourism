package com.example.tourism.entity;

import java.util.Date;

public class Order {
    private Integer orderId;

    private String orderContent;

    private Integer orderNumber;

    private Integer orderState;

    private String tripMode;

    private Date departDate;

    private String departDays;

    private String tirpInformation;

    private Double orderPrice;

    private Date orderDate;

    private String supplier;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getTripMode() {
        return tripMode;
    }

    public void setTripMode(String tripMode) {
        this.tripMode = tripMode;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public String getDepartDays() {
        return departDays;
    }

    public void setDepartDays(String departDays) {
        this.departDays = departDays;
    }

    public String getTirpInformation() {
        return tirpInformation;
    }

    public void setTirpInformation(String tirpInformation) {
        this.tirpInformation = tirpInformation;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}