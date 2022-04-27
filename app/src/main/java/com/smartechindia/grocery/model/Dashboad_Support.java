package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dashboad_Support {

    @SerializedName("help")
    @Expose
    private String help;

    @SerializedName("mobile")
    @Expose
    private String mobile;


    public Dashboad_Support(String help, String mobile) {
        this.help = help;
        this.mobile = mobile;
    }

    public String getHelp() {
        return help;
    }

    public String getMobile() {
        return mobile;
    }



    public void setHelp(String help) {
        this.help = help;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
