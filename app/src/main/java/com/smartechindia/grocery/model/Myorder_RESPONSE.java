package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Myorder_RESPONSE {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Myorder_Dataa> data;



    public Myorder_RESPONSE(boolean status, String message, List<Myorder_Dataa> data) {
        this.status = status;
        this.message = message;
        this.data = data;

    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


    public List<Myorder_Dataa> getData() {
        return data;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(List<Myorder_Dataa> data) {
        this.data = data;
    }

}
