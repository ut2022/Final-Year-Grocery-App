package com.smartechindia.grocery.api;

import com.smartechindia.grocery.model.DASHBOARD_RESPONSE;
import com.smartechindia.grocery.model.Dashboard_SendData;
import com.smartechindia.grocery.model.Myorder_RESPONSE;
import com.smartechindia.grocery.model.ORDER_SendData;
import com.smartechindia.grocery.model.Otp_Response;
import com.smartechindia.grocery.model.Otp_SendData;
import com.smartechindia.grocery.model.register_SendData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface api {

    // For Login and Registered Otp
    @Headers("Content-Type: application/json")
    @POST("otp.php")
    Call<Otp_Response> OTP_RESPONSE_CALL(@Body Otp_SendData body);

    // For Login and Registered Otp
    @Headers("Content-Type: application/json")
    @POST("login.php")
    Call<Otp_Response> LOGIN_RESPONSE_CALL(@Body Otp_SendData body);


    // Register Form
    @Headers("Content-Type: application/json")
    @POST("register.php")
    Call<Otp_Response> REGISTER_RESPONSE_CALL(@Body register_SendData body);
    // Dashboard Form
    @Headers("Content-Type: application/json")
    @POST("dashboard.php")
    Call<DASHBOARD_RESPONSE> DASHBOARD_RESPONSE_CALL(@Body Dashboard_SendData body);

    // ORDER PLACE
    @Headers("Content-Type: application/json")
    @POST("product-order.php")
    Call<Otp_Response> ORDER_RESPONSE_CALL(@Body ORDER_SendData body);


    // ORDER PLACE
    @Headers("Content-Type: application/json")
    @POST("order-list.php")
    Call<Myorder_RESPONSE> MYORDER_RESPONSE_CALL(@Body Dashboard_SendData body);

}
