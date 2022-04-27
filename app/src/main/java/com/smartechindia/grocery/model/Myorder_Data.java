package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Myorder_Data implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("qty")
    @Expose
    private String qty;

    @SerializedName("price")
    @Expose
    private String price;

    public Myorder_Data(String name, String qty, String price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
