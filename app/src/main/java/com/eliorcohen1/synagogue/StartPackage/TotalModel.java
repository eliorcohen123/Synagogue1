package com.eliorcohen1.synagogue.StartPackage;

import java.io.Serializable;

public class TotalModel implements Serializable {

    public String name;
    public String hyphen;
    public String phone;

    public TotalModel(String name, String hyphen, String phone) {
        this.name = name;
        this.hyphen = hyphen;
        this.phone = phone;
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

}
