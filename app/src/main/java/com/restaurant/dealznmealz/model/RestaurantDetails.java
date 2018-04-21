package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    @SerializedName("review")
    @Expose
    private String restaurantReviews;

    @SerializedName("rating")
    @Expose
    private String restaurantRating;

    @SerializedName("paid")
    @Expose
    private String restaurantPaidCategory;

}
