package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EE207823 on 12/30/2017.
 */

public class HotDealzOffers {

    @SerializedName("images")
    @Expose
    private String hotDealzImageUrl;

    @SerializedName("offer")
    @Expose
    private String hotDealzOffer;

    @SerializedName("offerid")
    @Expose
    private int hotDealzOfferId;

    public String getHotDealzImageUrl() {
        return hotDealzImageUrl;
    }

    public void setHotDealzImageUrl(String hotDealzImageUrl) {
        this.hotDealzImageUrl = hotDealzImageUrl;
    }

    public String getHotDealzOffer() {
        return hotDealzOffer;
    }

    public void setHotDealzOffer(String hotDealzOffer) {
        this.hotDealzOffer = hotDealzOffer;
    }

    public int getHotDealzOfferId() {
        return hotDealzOfferId;
    }

    public void setHotDealzOfferId(int hotDealzOfferId) {
        this.hotDealzOfferId = hotDealzOfferId;
    }
}
