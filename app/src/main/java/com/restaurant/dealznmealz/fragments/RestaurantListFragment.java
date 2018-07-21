package com.restaurant.dealznmealz.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.restaurant.dealznmealz.activities.HotelDetailsActivity;
import com.restaurant.dealznmealz.activities.RestaurantListActivity;
import com.restaurant.dealznmealz.adapter.OffersRecyclerViewAdapter;
import com.restaurant.dealznmealz.adapter.RestaurantListRecyclerAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.model.ListingModel;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ashis on 01-10-2017.
 */

public class RestaurantListFragment extends Fragment implements RestaurantViewHolder.ItemClickListener {

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

    private int offerId;
    private String hotDealzImgUrl;

    private int discId;
    private String searchText;
    private int currentPage = 0;
    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (RestaurantListActivity) getActivity();
        mContext = mActivity;
        fragmentTitle = getArguments().getString(fragmentTitleKey);
        fragmentIdentifier = getArguments().getString(fragmentIdentifierKey);

        if (fragmentIdentifier.equals("HOTDEALZ")) {
            offerId = getArguments().getInt("OFFER_ID", 0);
            hotDealzImgUrl = getArguments().getString("OFFER_IMG_URL");
        } else if (fragmentIdentifier.equals("DISCOUNTEDLIST")) {
            discId = getArguments().getInt("DISC_ID", 0);
        } else if(fragmentIdentifier.equals("SEARCH")) {
            searchText = getArguments().getString("SEARCH_TEXT");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View v = (View) inflater.inflate(R.layout.fragment_most_searched_restaurant, container, false);

        TextView headerTextView = (TextView) v.findViewById(R.id.header_title);
        headerTextView.setText(fragmentTitle);
        setUpViewPagerMenu(v);
//        setUpBannerImage(v);
        setUpRestaurantList(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPaidBannerData();
        loadRestaurantListData();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopViewPagerAnimation();
    }

    private void setUpViewPagerMenu(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        restaurantPagerAdapter = new ViewPagerMenuAdapter(mActivity);
    }

    private void loadPaidBannerData() {
        String bannerUrl = retrofitNetworkManagerService.getPaidBannerList().request().url().toString();
        Log.v(TAG, "Banner URL : " + bannerUrl);
        Call<List<PaidBanners>> userDetailsCall = retrofitNetworkManagerService.getPaidBannerList();
        userDetailsCall.enqueue(new Callback<List<PaidBanners>>() {
            @Override
            public void onResponse(Call<List<PaidBanners>> call, Response<List<PaidBanners>> response) {
                Log.v(TAG, "Response details : " + response.body());
                List<PaidBanners> paidBannerList = response.body();
                restaurantPagerAdapter.setPaidBannersListData(paidBannerList);
                viewPager.setAdapter(restaurantPagerAdapter);
                animateViewPager();
            }

            @Override
            public void onFailure(Call<List<PaidBanners>> call, Throwable t) {
                Log.v(TAG, "onFailure");
            }
        });
    }

    private void animateViewPager() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == restaurantPagerAdapter.getCount() - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void stopViewPagerAnimation() {
        timer.cancel();
    }

    private void setUpRestaurantList(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.restaurant_list_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        listRecyclerAdapter = new RestaurantListRecyclerAdapter(mActivity, this);
    }

    private void loadRestaurantListData() {
        switch(fragmentIdentifier) {
            case "MOSTSEARCH":
                String mostSearchedUrl = retrofitNetworkManagerService.getMostSearchedListingData().request().url().toString();
                Log.v(TAG, "Most Searched URL : "+mostSearchedUrl);
                Call<List<ListingModel>> mostSearchedDataCall = retrofitNetworkManagerService.getMostSearchedListingData();
                loadListingData(mostSearchedDataCall);
                break;

            case "REVIEWS":
                String mostReviewedUrl = retrofitNetworkManagerService.getMostReviewedListingData().request().url().toString();
                Log.v(TAG, "Most Reviewed Data Call : "+mostReviewedUrl);
                Call<List<ListingModel>> mostReviewedDataCall = retrofitNetworkManagerService.getMostReviewedListingData();
                loadListingData(mostReviewedDataCall);
                break;

            case "LATEST":
                String latestRestaurantsUrl = retrofitNetworkManagerService.getLatestListingData().request().url().toString();
                Log.v(TAG, "Latest Restaurant Data Call : "+latestRestaurantsUrl);
                Call<List<ListingModel>> latestDataCall = retrofitNetworkManagerService.getLatestListingData();
                loadListingData(latestDataCall);
                break;

            case "HOTDEALZ":
                String hotDealzUrlCall = retrofitNetworkManagerService.getHotDealzListingData(offerId).request().url().toString();
                Log.v(TAG, "Latest Restaurant Data Call : "+hotDealzUrlCall);
                Call<List<ListingModel>> hotdealzDataCall = retrofitNetworkManagerService.getHotDealzListingData(offerId);
                loadListingData(hotdealzDataCall);
                break;

            case "DISCOUNTEDLIST":
                String discountedListUrlCall = retrofitNetworkManagerService.getDiscountListingData(discId).request().url().toString();
                Log.v(TAG, "Discounted Restaurant Data Call : "+discountedListUrlCall);
                Call<List<ListingModel>> discountedDataCall = retrofitNetworkManagerService.getDiscountListingData(discId);
                loadListingData(discountedDataCall);
                break;

            case "SEARCH":
                Call<List<ListingModel>> searchListUrlCall = retrofitNetworkManagerService.getSearchListingData(searchText);
                String searchListUrl = searchListUrlCall.request().url().toString();
                Log.v(TAG, "Search Listing Restaurant Data Call : "+searchListUrl);
                loadListingData(searchListUrlCall);
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

    @Override
    public void onItemClick(View view, int position, String restId) {
        Log.v(TAG, "Restaurnat List Clicked - "+restId);
        navigateToHotelDetailsActivity("PAID", restId);
    }

    private void navigateToHotelDetailsActivity(String hotelDetailsIdentifier, String restId) {
        Intent i = new Intent(mActivity, HotelDetailsActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", hotelDetailsIdentifier);
        i.putExtra("REST_ID", restId);
        startActivity(i);
    }
}
