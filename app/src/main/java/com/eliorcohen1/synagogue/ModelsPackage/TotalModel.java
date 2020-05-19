package com.eliorcohen1.synagogue.ModelsPackage;

import java.io.Serializable;

public class TotalModel implements Serializable {

    private String id;
    private String name;
    private String numPhone;
    private String distance;
    private String imageView;
    private int image_drawable;

    public TotalModel(String name, String numPhone) {
        this.name = name;
        this.numPhone = numPhone;
    }

    public TotalModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
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
