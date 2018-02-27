package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EE207823 on 2/8/2018.
 */

public class SearchLocationModel {

    @SerializedName("area")
    @Expose
    private String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
