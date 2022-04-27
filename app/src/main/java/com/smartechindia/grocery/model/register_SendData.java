package com.smartechindia.grocery.model;

public class register_SendData {
    private  String mobile;
    private  String otp;
    private  String address;
    private  String name;

    public register_SendData(String mobile, String otp, String address, String name) {
        this.mobile = mobile;
        this.otp = otp;
        this.address = address;
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOtp() {
        return otp;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
}
