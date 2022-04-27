package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class order_product {

    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("qty")
    @Expose
    private String qty;

    public order_product(String pid, String qty) {
        this.pid = pid;
        this.qty = qty;
    }

    public String getPid() {
        return pid;
    }

    public String getQty() {
        return qty;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}

