package com.example.tourism.entity;

import java.util.Date;

public class Order {
    private Integer orderId;

    private String orderContent;

    private Integer orderNumber;

    private Integer passengerNumber;

    private Integer orderState;

    private String tripMode;

    private String departDate;

    private String departDays;

    private String tirpInformation;

    private Double orderPrice;

    private Date orderDate;

    private Integer roomDifference;

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

    public Integer getPassengerNumber() {
        return passengerNumber;
    }

    public void setPassengerNumber(Integer passengerNumber) {
        this.passengerNumber = passengerNumber;
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

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
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

    public Integer getRoomDifference() {
        return roomDifference;
    }

    public void setRoomDifference(Integer roomDifference) {
        this.roomDifference = roomDifference;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}