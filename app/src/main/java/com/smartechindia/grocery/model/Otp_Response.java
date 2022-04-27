package com.smartechindia.grocery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Otp_Response {

        @SerializedName("status")
        @Expose
        private boolean status;
        @SerializedName("message")
        @Expose
        private String message;
    @SerializedName("token")
    @Expose
    private String token;

        public Otp_Response(boolean status, String message,String token) {
            this.status = status;
            this.message = message;
            this.token = token;
        }

        public boolean isStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    public String getToken() {
        return token;
    }

}
