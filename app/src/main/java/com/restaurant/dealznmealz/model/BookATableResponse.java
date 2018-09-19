package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SKarwatkar on 27/08/2018.
 */

public class BookATableResponse {
    @SerializedName("message")
    @Expose
    private String bookAtableResponse;

    public String getBookAtableResponse() {
        return bookAtableResponse;
    }

    public void setBookAtableResponse(String registrationResponse) {
        this.bookAtableResponse = registrationResponse;
    }
}
