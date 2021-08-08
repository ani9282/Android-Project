package com.ecs.hindfarmfreshchicken;

public class ModelOrders {
    private int id;
    private String phoneNumber;
    private String customerName;
    private String customerAddress;
    private String orderStatus;
    private String quantity;
    private float cost;
    private String modeOfPayment;
    private String paymentStatus;
    private String date;
    private String time;

    public ModelOrders(int id, String phoneNumber, String customerName, String customerAddress, String orderStatus, String quantity, float cost, String modeOfPayment, String paymentStatus, String date, String time) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.orderStatus = orderStatus;
        this.quantity = quantity;
        this.cost = cost;
        this.modeOfPayment = modeOfPayment;
        this.paymentStatus = paymentStatus;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getQuantity() {
        return quantity;
    }

    public float getCost() {
        return cost;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
