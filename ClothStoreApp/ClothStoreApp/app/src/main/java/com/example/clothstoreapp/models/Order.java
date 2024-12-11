package com.example.clothstoreapp.models;

public class Order {
    private String OrderId;
    private String OrderDate;
    private String OrderStatus;
    private String OrderTotal;
    private String OrderItems;
    private String PaymentMode;


    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getOrderItems() {
        return OrderItems;
    }

    public void setOrderItems(String orderItems) {
        OrderItems = orderItems;
    }

    public void Order(String OrderId, String OrderDate, String OrderStatus, String OrderTotal, String OrderItems) {
        this.OrderId = OrderId;
        this.OrderDate = OrderDate;
        this.OrderStatus = OrderStatus;
        this.OrderTotal = OrderTotal;
        this.OrderItems = OrderItems;
        this.PaymentMode = PaymentMode;
    }
}
