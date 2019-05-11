package com.eliorcohen1.synagogue.StartPackage;

import java.io.Serializable;

public class TotalModel implements Serializable {

    private String name;
    private String hyphen;
    private String phone;
    private String distance;
    private String imageView;
    private int image_drawable;

    public TotalModel(String name, String hyphen, String phone) {
        this.name = name;
        this.hyphen = hyphen;
        this.phone = phone;
    }

    public TotalModel() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHyphen() {
        return hyphen;
    }

    public void setHyphen(String hyphen) {
        this.hyphen = hyphen;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;
    }

}
