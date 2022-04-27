package com.smartechindia.grocery.model;

import java.util.List;

public class ORDER_SendData {
    private String token;
    private List<order_product> data;

    public ORDER_SendData(String token, List<order_product> data) {
        this.token = token;
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public List<order_product> getData() {
        return data;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setData(List<order_product> data) {
        this.data = data;
    }
}
