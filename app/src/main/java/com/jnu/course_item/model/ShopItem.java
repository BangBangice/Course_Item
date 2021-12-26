package com.jnu.course_item.model;

import java.io.Serializable;

public class ShopItem implements Serializable {

    private String name;
    private double message;
    private int picture;

    public void setMessage(double message) {
        this.message = message;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public void setName(String name) {
        this.name = name;
    }



    public ShopItem(String name, int picture, double message) {
        this.name=name;
        this.picture=picture;
        this.message=message;
    }

    public double getMessage() {
        return message;
    }

    public int getPicture() {
        return picture;
    }

    public String getName() {
        return name;
    }
}
