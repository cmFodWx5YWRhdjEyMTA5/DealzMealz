package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EE207823 on 12/30/2017.
 */

public class DiscountedHotels {

    @SerializedName("disc_id")
    @Expose
    private int discId;

    @SerializedName("numberofresto")
    @Expose
    private int numOfRestros;

    @SerializedName("title")
    @Expose
    private String discTitle;

    @SerializedName("bg_img")
    @Expose
    private String discImageUrl;

    public int getDiscId() {
        return discId;
    }

    public void setDiscId(int discId) {
        this.discId = discId;
    }

    public int getNumOfRestros() {
        return numOfRestros;
    }

    public void setNumOfRestros(int numOfRestros) {
        this.numOfRestros = numOfRestros;
    }

    public String getDiscTitle() {
        return discTitle;
    }

    public void setDiscTitle(String discTitle) {
        this.discTitle = discTitle;
    }

    public String getDiscImageUrl() {
        return discImageUrl;
    }

    public void setDiscImageUrl(String discImageUrl) {
        this.discImageUrl = discImageUrl;
    }
}
