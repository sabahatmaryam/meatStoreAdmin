package com.example.meatstoreadmin.Models;

public class CartModel {
    private  String pid;
    private String meatName;

    private String meatPrice;

    private String meatQuantity;



    public CartModel(){

    }



    public CartModel(String meatId, String meatName, String meatPrice, String meatQuantity ) {
        this.pid = meatId;
        this.meatName = meatName;
        this.meatPrice = meatPrice;
        this.meatQuantity = meatQuantity;

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMeatName() {
        return meatName;
    }

    public void setMeatName(String meatName) {
        this.meatName = meatName;
    }

    public String getMeatPrice() {
        return meatPrice;
    }

    public void setMeatPrice(String meatPrice) {
        this.meatPrice = meatPrice;
    }

    public String getMeatQuantity() {
        return meatQuantity;
    }

    public void setMeatQuantity(String meatQuantity) {
        this.meatQuantity = meatQuantity;
    }
}
