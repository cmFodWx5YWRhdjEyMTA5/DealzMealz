package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EE207823 on 12/31/2017.
 */

public class DealzMealzUserDetails {

    @SerializedName("id")
    @Expose
    private int dealzNMealzId;

    @SerializedName("user_id")
    @Expose
    private String dealzNMealzUserId;

    @SerializedName("email")
    @Expose
    private String userEmail;

    @SerializedName("mobileno")
    @Expose
    private double mobileNo;

    @SerializedName("username")
    @Expose
    private String userName;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("identifier")
    @Expose
    private String identifier;

    @SerializedName("message")
    @Expose
    private String message;

    public int getDealzNMealzId() {
        return dealzNMealzId;
    }

    public void setDealzNMealzId(int dealzNMealzId) {
        this.dealzNMealzId = dealzNMealzId;
    }

    public String getDealzNMealzUserId() {
        return dealzNMealzUserId;
    }

    public void setDealzNMealzUserId(String dealzNMealzUserId) {
        this.dealzNMealzUserId = dealzNMealzUserId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(double mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
