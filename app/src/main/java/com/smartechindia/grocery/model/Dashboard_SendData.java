package com.smartechindia.grocery.model;

public class Dashboard_SendData {
    private  String token;

    public Dashboard_SendData(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
