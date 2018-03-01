package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.ListingModel;
import com.restaurant.dealznmealz.model.RestaurantDetails;
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashis on 01-10-2017.
 */

public class RestaurantListRecyclerAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private Context mContext;
    private List<ListingModel> listingModelList;

    public RestaurantListRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setRestaurntListData(List<ListingModel> list) {
        this.listingModelList = list;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hotel_list_item, parent, false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view, mContext);
        restaurantViewHolder.setListingData(listingModelList);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.handleOnBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return listingModelList.size();
    }
}
