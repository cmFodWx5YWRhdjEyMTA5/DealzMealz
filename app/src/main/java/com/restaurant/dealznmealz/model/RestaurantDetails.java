package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashis on 01-10-2017.
 */

public class RestaurantDetails {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String restaurantName;

    @SerializedName("user_name")
    @Expose
    private String restaurantUserName;

    @SerializedName("contact")
    @Expose
    private String restaurantContact;

    @SerializedName("address")
    @Expose
    private String restaurantAddress;

    @SerializedName("latitude")
    @Expose
    private String restaurantLatitude;

    @SerializedName("longitude")
    @Expose
    private String restaurantLongitude;

    @SerializedName("category")
    @Expose
    private String restaurantCategory;

    @SerializedName("area")
    @Expose
    private String restaurantArea;

    @SerializedName("cost")
    @Expose
    private String restaurantCost;

    @SerializedName("morning_time")
    @Expose
    private String restaurantMorningTime;

    @SerializedName("evening_time")
    @Expose
    private String restaurantEveningTime;

    @SerializedName("openinghours")
    @Expose
    private String restaurantOpeningHours;

    @SerializedName("mobile2")
    @Expose
    private String restaurantAlternateContact;

    @SerializedName("land_line1")
    @Expose
    private String restaurantLandLine1;

    @SerializedName("land_line2")
    @Expose
    private String restaurantLandLine2;

    @SerializedName("booktable")
    @Expose
    private String restaurantBookTable;

    @SerializedName("rating")
    @Expose
    private int restaurantRating;

    @SerializedName("paid")
    @Expose
    private String restaurantPaidCategory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantUserName() {
        return restaurantUserName;
    }

    public void setRestaurantUserName(String restaurantUserName) {
        this.restaurantUserName = restaurantUserName;
    }

    public String getRestaurantContact() {
        return restaurantContact;
    }

    public void setRestaurantContact(String restaurantContact) {
        this.restaurantContact = restaurantContact;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public void setRestaurantLatitude(String restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    public String getRestaurantLongitude() {
        return restaurantLongitude;
    }

    public void setRestaurantLongitude(String restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

    public String getRestaurantCategory() {
        return restaurantCategory;
    }

    public void setRestaurantCategory(String restaurantCategory) {
        this.restaurantCategory = restaurantCategory;
    }

    public String getRestaurantArea() {
        return restaurantArea;
    }

    public void setRestaurantArea(String restaurantArea) {
        this.restaurantArea = restaurantArea;
    }

    public String getRestaurantCost() {
        return restaurantCost;
    }

    public void setRestaurantCost(String restaurantCost) {
        this.restaurantCost = restaurantCost;
    }

    public String getRestaurantMorningTime() {
        return restaurantMorningTime;
    }

    public void setRestaurantMorningTime(String restaurantMorningTime) {
        this.restaurantMorningTime = restaurantMorningTime;
    }

    public String getRestaurantEveningTime() {
        return restaurantEveningTime;
    }

    public void setRestaurantEveningTime(String restaurantEveningTime) {
        this.restaurantEveningTime = restaurantEveningTime;
    }

    public String getRestaurantOpeningHours() {
        return restaurantOpeningHours;
    }

    public void setRestaurantOpeningHours(String restaurantOpeningHours) {
        this.restaurantOpeningHours = restaurantOpeningHours;
    }

    public String getRestaurantAlternateContact() {
        return restaurantAlternateContact;
    }

    public void setRestaurantAlternateContact(String restaurantAlternateContact) {
        this.restaurantAlternateContact = restaurantAlternateContact;
    }

    public String getRestaurantLandLine1() {
        return restaurantLandLine1;
    }

    public void setRestaurantLandLine1(String restaurantLandLine1) {
        this.restaurantLandLine1 = restaurantLandLine1;
    }

    public String getRestaurantLandLine2() {
        return restaurantLandLine2;
    }

    public void setRestaurantLandLine2(String restaurantLandLine2) {
        this.restaurantLandLine2 = restaurantLandLine2;
    }

    public String getRestaurantBookTable() {
        return restaurantBookTable;
    }

    public void setRestaurantBookTable(String restaurantBookTable) {
        this.restaurantBookTable = restaurantBookTable;
    }

    public int getRestaurantRating() {
        return restaurantRating;
    }

    public void setRestaurantRating(int restaurantRating) {
        this.restaurantRating = restaurantRating;
    }

    public String getRestaurantPaidCategory() {
        return restaurantPaidCategory;
    }

    public void setRestaurantPaidCategory(String restaurantPaidCategory) {
        this.restaurantPaidCategory = restaurantPaidCategory;
    }
}
