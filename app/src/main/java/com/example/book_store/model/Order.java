package com.example.book_store.model;

public class Order {
    String orderId, orderTime, orderCost, orderBy, orderStatus;

    public Order() {
    }

    public Order(String orderId, String orderTime, String orderCost, String orderBy, String orderStatus) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderCost = orderCost;
        this.orderBy = orderBy;
        this.orderStatus = orderStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
