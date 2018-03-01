package com.restaurant.dealznmealz.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.activities.RestaurantListActivity;
import com.restaurant.dealznmealz.adapter.OffersRecyclerViewAdapter;
import com.restaurant.dealznmealz.adapter.RestaurantListRecyclerAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.model.ListingModel;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashis on 01-10-2017.
 */

public class RestaurantListFragment extends Fragment {

    private RestaurantListActivity mActivity;
    private String fragmentTitle;
    private Context mContext;

    private ViewPager viewPager;
    private ImageView bannerImage;
    private int images[] = {R.drawable.restaurant_dish1, R.drawable.restaurant_dish2};
    private ViewPagerMenuAdapter restaurantPagerAdapter;
    private String fragmentTitleKey = "FRAGMENT_TITLE_KEY";
    private String fragmentIdentifierKey = "FRAGMENT_IDENTIFIER";
    private String fragmentIdentifier;

    private RetrofitNetworkManager retrofitNetworkManager = RetrofitNetworkManager.getNetworkManager();
    private RetrofitNetworkManagerService retrofitNetworkManagerService = retrofitNetworkManager.getNetworkManagerService();

    private RecyclerView recyclerView;
    private RestaurantListRecyclerAdapter listRecyclerAdapter;

    private String TAG = RestaurantListFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RestaurantListActivity) getActivity();
        mContext = mActivity;
        fragmentTitle = getArguments().getString(fragmentTitleKey);
        fragmentIdentifier = getArguments().getString(fragmentIdentifierKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View v = (View) inflater.inflate(R.layout.fragment_most_searched_restaurant, container, false);

        TextView headerTextView = (TextView) v.findViewById(R.id.header_title);
        headerTextView.setText(fragmentTitle);
        setUpBannerImage(v);
        setUpRestaurantList(v);
        return v;
    }

    private void setUpBannerImage(View v) {
        bannerImage = v.findViewById(R.id.list_banner_image);
        switch(fragmentIdentifier) {
            case "SEARCH":
                Picasso.with(mActivity).load("https://dealznmealz.com/image/home1.jpg").into(bannerImage);
                break;

            case "REVIEWS":
                Picasso.with(mActivity).load("https://dealznmealz.com/image/home2.jpeg").into(bannerImage);
                break;

            case "LATEST":
                Picasso.with(mActivity).load("https://dealznmealz.com/image/home4.jpg").into(bannerImage);
                break;
        }
    }

    private void setUpViewPagerMenu(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        restaurantPagerAdapter = new ViewPagerMenuAdapter(mActivity);
        viewPager.setAdapter(restaurantPagerAdapter);
    }

    private void setUpRestaurantList(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.restaurant_list_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        listRecyclerAdapter = new RestaurantListRecyclerAdapter(mActivity);
        loadRestaurantListData();
    }

    private void loadRestaurantListData() {
        switch(fragmentIdentifier) {
            case "SEARCH":
                String mostSearchedUrl = retrofitNetworkManagerService.getMostSearchedListingData().request().url().toString();
                Log.v(TAG, "Most Searched URL : "+mostSearchedUrl);
                Call<List<ListingModel>> mostSearchedDataCall = retrofitNetworkManagerService.getMostSearchedListingData();
                loadListingData(mostSearchedDataCall);
                break;

            case "REVIEWS":
                Call<List<ListingModel>> mostReviewedDataCall = retrofitNetworkManagerService.getMostReviewedListingData();
                loadListingData(mostReviewedDataCall);
                break;

            case "LATEST":
                Call<List<ListingModel>> latestDataCall = retrofitNetworkManagerService.getLatestListingData();
                loadListingData(latestDataCall);
                break;
        }
    }

    private void loadListingData(Call<List<ListingModel>> loadListingData) {

        loadListingData.enqueue(new Callback<List<ListingModel>>() {
            @Override
            public void onResponse(Call<List<ListingModel>> call, Response<List<ListingModel>> response) {
                Log.v(TAG, "On Response");
                List<ListingModel> listData = response.body();
                Log.v(TAG, "On Response List :- "+listData.size());
                listRecyclerAdapter.setRestaurntListData(listData);
                recyclerView.setAdapter(listRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<List<ListingModel>> call, Throwable t) {
                Log.v(TAG, "onFailure most searched data");
            }
        });
    }
}
