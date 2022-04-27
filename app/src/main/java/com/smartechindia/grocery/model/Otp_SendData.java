package com.smartechindia.grocery.model;

public class Otp_SendData {
    private  String mobile;
    private  String otp;

    public Otp_SendData(String mobile, String otp) {
        this.mobile = mobile;
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOtp() {
        return otp;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
