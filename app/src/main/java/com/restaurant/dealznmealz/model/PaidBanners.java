package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EE207823 on 12/30/2017.
 */

public class PaidBanners {

    @SerializedName("image")
    @Expose
    private String paidHotelImageUrl;

    @SerializedName("category")
    @Expose
    private String paidHotelCategory;

    @SerializedName("nameres")
    @Expose
    private String paidHotelName;

    @SerializedName("uname")
    @Expose
    private String paidHotelUName;

    @SerializedName("status")
    @Expose
    private int paidHotelStatus;

    @SerializedName("restoid")
    @Expose
    private int paidHotelRestoId;

    public String getPaidHotelImageUrl() {
        return paidHotelImageUrl;
    }

    public void setPaidHotelImageUrl(String paidHotelImageUrl) {
        this.paidHotelImageUrl = paidHotelImageUrl;
    }

    public String getPaidHotelCategory() {
        return paidHotelCategory;
    }

    public void setPaidHotelCategory(String paidHotelCategory) {
        this.paidHotelCategory = paidHotelCategory;
    }

    public String getPaidHotelName() {
        return paidHotelName;
    }

    public void setPaidHotelName(String paidHotelName) {
        this.paidHotelName = paidHotelName;
    }

    public String getPaidHotelUName() {
        return paidHotelUName;
    }

    public void setPaidHotelUName(String paidHotelUName) {
        this.paidHotelUName = paidHotelUName;
    }

    public int getPaidHotelStatus() {
        return paidHotelStatus;
    }

    public void setPaidHotelStatus(int paidHotelStatus) {
        this.paidHotelStatus = paidHotelStatus;
    }

    public int getPaidHotelRestoId() {
        return paidHotelRestoId;
    }

    public void setPaidHotelRestoId(int paidHotelRestoId) {
        this.paidHotelRestoId = paidHotelRestoId;
    }
}
