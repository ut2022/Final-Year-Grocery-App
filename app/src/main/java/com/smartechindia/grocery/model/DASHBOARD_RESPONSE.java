package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DASHBOARD_RESPONSE {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Dashboard_Data> data;

    @SerializedName("user")
    @Expose
    private List<Dashboad_User> user;

    @SerializedName("support")
    @Expose
    private List<Dashboad_Support> support;


    public DASHBOARD_RESPONSE(boolean status, String message, List<Dashboard_Data> data, List<Dashboad_User> user,List<Dashboad_Support> support) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.user = user;
        this.support = support;

    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Dashboad_Support> getSupport() {
        return support;
    }

    public List<Dashboard_Data> getData() {
        return data;
    }

    public List<Dashboad_User> getUser() {
        return user;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setSupport(List<Dashboad_Support> support) {
        this.support = support;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<Dashboard_Data> data) {
        this.data = data;
    }

    public void setUser(List<Dashboad_User> user) {
        this.user = user;
    }
}
