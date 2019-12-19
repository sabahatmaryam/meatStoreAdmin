package com.example.meatstoreadmin.Models;

public class Meat {


    private String meatName;
    private String meatImage;
    private String meatPrice;
    private  String pid;
    private String meatQuantity;
    private String date;
    private String time;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Meat()
    {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public Meat(String meatName, String meatImage, String meatPrice, String pid, String meatQuantity, String date, String time,String category) {
        this.meatName = meatName;
        this.meatImage = meatImage;
        this.meatPrice = meatPrice;
        this.pid = pid;
        this.meatQuantity = meatQuantity;
        this.date = date;
        this.time = time;
        this.category = category;
    }

    public String getMeatName() {
        return meatName;
    }

    public void setMeatName(String meatName) {
        this.meatName = meatName;
    }

    public String getMeatImage() {
        return meatImage;
    }

    public void setMeatImage(String meatImage) {
        this.meatImage = meatImage;
    }

    public String getMeatPrice() {
        return meatPrice;
    }

    public void setMeatPrice(String meatPrice) {
        this.meatPrice = meatPrice;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMeatQuantity() {
        return meatQuantity;
    }

    public void setMeatQuantity(String meatQuantity) {
        this.meatQuantity = meatQuantity;
    }
}
