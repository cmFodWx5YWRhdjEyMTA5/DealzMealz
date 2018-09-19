package com.restaurant.dealznmealz.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.activities.HotelDetailsActivity;
import com.restaurant.dealznmealz.activities.RestaurantListActivity;
import com.restaurant.dealznmealz.adapter.RestaurantListRecyclerAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;

/**
 * Created by ashis on 01-10-2017.
 */

public class MostReviewsRestaurantListFragment extends Fragment implements RestaurantViewHolder.ItemClickListener {

    private HotelDetailsActivity mActivity;
    private Context mContext;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (HotelDetailsActivity) getActivity();
        mContext = mActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = (View) inflater.inflate(R.layout.fragment_most_searched_restaurant, container, false);

        TextView headerTextView = (TextView) v.findViewById(R.id.header_title);
        headerTextView.setText("Most Reviews Restaurants");
        setUpRestaurantList(v);
        return v;
    }



    private void setUpRestaurantList(View v) {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.restaurant_list_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new RestaurantListRecyclerAdapter(mActivity, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position, String restId) {
        
    }
}
