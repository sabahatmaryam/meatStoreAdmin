package com.example.meatstoreadmin.Models;

public class AdminOrders {

    private String date,phone,state,time,totalAmount,address;
    public  AdminOrders(){


    }

    public AdminOrders(String date, String phone, String state, String time, String totalAmount,String address) {
        this.date = date;
        this.phone = phone;
        this.state = state;
        this.time = time;
        this.totalAmount = totalAmount;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
