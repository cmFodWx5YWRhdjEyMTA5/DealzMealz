package com.restaurant.dealznmealz.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EE207823 on 2/28/2018.
 */

public class CategoryModel {

    @SerializedName("catid")
    @Expose
    private int catID;

    @SerializedName("category_name")
    @Expose
    private String categoryName;

    public int getCatID() {
        return catID;
    }

    public void setCatID(int catID) {
        this.catID = catID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
