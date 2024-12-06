package com.example.sscapp.models;

import java.util.List;

public class Order {
    private String orderId;
    private String srCode;
    private String date;
    private String status;
    private double totalAmount;
    private List<GroupedCartItem> items;

    public Order(String orderId, String srCode, String date, String status, double totalAmount, List<GroupedCartItem> items) {
        this.orderId = orderId;
        this.srCode = srCode;
        this.date = date;
        this.status = status;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSrCode() {
        return srCode;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<GroupedCartItem> getItems() {
        return items;
    }

    public void setItems(List<GroupedCartItem> items) {
        this.items = items;
    }
}
