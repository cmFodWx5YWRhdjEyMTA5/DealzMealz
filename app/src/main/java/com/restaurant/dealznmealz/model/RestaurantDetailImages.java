package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashish Chaudhary on 7/21/2018.
 */
public class RestaurantDetailImages {

    @SerializedName("images_0")
    @Expose
    private String restaurantImage_0;

    @SerializedName("images_1")
    @Expose
    private String restaurantImage_1;

    @SerializedName("images_2")
    @Expose
    private String restaurantImage_2;

    public String getRestaurantImage_0() {
        return restaurantImage_0;
    }

    public void setRestaurantImage_0(String restaurantImage_0) {
        this.restaurantImage_0 = restaurantImage_0;
    }

    public String getRestaurantImage_1() {
        return restaurantImage_1;
    }

    public void setRestaurantImage_1(String restaurantImage_1) {
        this.restaurantImage_1 = restaurantImage_1;
    }

    public String getRestaurantImage_2() {
        return restaurantImage_2;
    }

    public void setRestaurantImage_2(String restaurantImage_2) {
        this.restaurantImage_2 = restaurantImage_2;
    }
}