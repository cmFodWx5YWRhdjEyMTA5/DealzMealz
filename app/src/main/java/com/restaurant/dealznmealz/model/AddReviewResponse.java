package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SKarwatkar on 27/08/2018.
 */

public class AddReviewResponse {
    @SerializedName("message")
    @Expose
    private String addReviewResponse;

    public String getAddReviewResponse() {
        return addReviewResponse;
    }

    public void setAddReviewResponse(String addReviewResponse) {
        this.addReviewResponse = addReviewResponse;
    }
}
