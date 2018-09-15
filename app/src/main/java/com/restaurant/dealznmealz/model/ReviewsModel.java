package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashish Chaudhary on 9/14/2018.
 */
public class ReviewsModel {


    @SerializedName("review")
    @Expose
    private String review;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("name")
    @Expose
    private String name;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
