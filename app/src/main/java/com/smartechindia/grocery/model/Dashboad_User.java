package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboad_User {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("token")
    @Expose
    private String token;

    public Dashboad_User(String name, String mobile, String address, String token) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
