package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SKarwatkar on 16/09/2018.
 */

public class PostAndroidTokenResponse {
    @SerializedName("message")
    @Expose
    private String postTokenDataResponse;

    public String getPostTokenDataResponse() {
        return postTokenDataResponse;
    }

    public void setPostTokenDataResponse(String postTokenDataResponse) {
        this.postTokenDataResponse = postTokenDataResponse;
    }
}
