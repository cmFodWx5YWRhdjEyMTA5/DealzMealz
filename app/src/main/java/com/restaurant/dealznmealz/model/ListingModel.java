package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ashish Chaudhary on 3/1/2018.
 */

public class ListingModel {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("img")
    @Expose
    private String image;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("paid")
    @Expose
    private String paidStatus;

    @SerializedName("co-ordinates")
    @Expose
    private Location locationData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public Location getLocationData() {
        return locationData;
    }

    public void setLocationData(Location locationData) {
        this.locationData = locationData;
    }

    public class Location {

        @SerializedName("latitude")
        @Expose
        private double latitude;

        @SerializedName("longitude")
        @Expose
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(long latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(long longitude) {
            this.longitude = longitude;
        }
    }


}
