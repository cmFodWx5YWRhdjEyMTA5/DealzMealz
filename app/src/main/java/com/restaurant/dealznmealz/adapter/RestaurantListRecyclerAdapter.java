package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.RestaurantDetails;
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;

import java.util.ArrayList;

/**
 * Created by ashis on 01-10-2017.
 */

public class RestaurantListRecyclerAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private Context mContext;
    private ArrayList<RestaurantDetails> restaurantDetailsArrayList;

    public RestaurantListRecyclerAdapter(Context context) {
        mContext = context;
    }

    public RestaurantListRecyclerAdapter(ArrayList<RestaurantDetails> restaurantDetailsArrayList, Context context) {
        this.restaurantDetailsArrayList = restaurantDetailsArrayList;
        mContext = context;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_hotel_list_item, parent, false);
        RestaurantViewHolder garnetViewHolder = new RestaurantViewHolder(view, mContext);
        garnetViewHolder.setGarageListDetails(restaurantDetailsArrayList);
        return garnetViewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        holder.handleOnBindViewHolder(position);
    }

    @Override
    public int getItemCount() {

//        return restaurantDetailsArrayList.size();
        return 6;
    }
}
