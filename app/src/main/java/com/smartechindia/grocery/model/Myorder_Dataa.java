package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Myorder_Dataa implements Serializable {

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("data")
    @Expose
    private List<Myorder_Data> data;

    public Myorder_Dataa(String date, String status, List<Myorder_Data> data) {
        this.date = date;
        this.status = status;
        this.data = data;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public List<Myorder_Data> getData() {
        return data;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(List<Myorder_Data> data) {
        this.data = data;
    }
}
