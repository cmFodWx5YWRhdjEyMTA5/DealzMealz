package com.restaurant.dealznmealz.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.adapter.HotDealzRecyclerViewAdapter;
import com.restaurant.dealznmealz.adapter.OffersRecyclerViewAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.model.DealzMealzUserDetails;
import com.restaurant.dealznmealz.model.DiscountedHotels;
import com.restaurant.dealznmealz.model.HotDealzOffers;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends DealznmealzBaseActivity implements OffersRecyclerViewAdapter.ItemClickListener {

    private final String TAG = HomeActivity.class.getSimpleName();
    private ViewPager viewPager;
    private ViewPagerMenuAdapter paidBannersAdapter;
    private RecyclerView offersRecyclerView;
    private OffersRecyclerViewAdapter offersRecyclerViewAdapter;

    private ImageView mostSearchedImageView;
    private ImageView mostReviewedImageView;
    private ImageView latestRestroImageView;

    private HomeActivity mActivity;

    private RetrofitNetworkManager retrofitNetworkManager = RetrofitNetworkManager.getNetworkManager();
    private RetrofitNetworkManagerService retrofitNetworkManagerService = retrofitNetworkManager.getNetworkManagerService();

    private RecyclerView hotDealzRecyclerView;
    private HotDealzRecyclerViewAdapter hotDealzRecyclerViewAdapter;

    private LinearLayout offersSeparator;

    private int currentPage = 0;
    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mActivity = this;

        setUpViewPagerMenu();
        setToolBar();
        setUpOffersRecyclerView();
        setUpHotDealzView();
        loadBottomTabImages();
    }

    private void setUpViewPagerMenu() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        paidBannersAdapter = new ViewPagerMenuAdapter(this);
        loadPaidBannerData();
    }

    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setUpOffersRecyclerView() {
        // set up the RecyclerView
        offersRecyclerView = findViewById(R.id.rv_offers);
        int numberOfColumns = 2;
        offersRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        offersRecyclerViewAdapter = new OffersRecyclerViewAdapter(this);
        offersRecyclerViewAdapter.setClickListener(this);
        loadDiscountedHotelsData();
    }

    private void loadBottomTabImages() {
        mostSearchedImageView = (ImageView) findViewById(R.id.mostSearchedImageView);
        mostReviewedImageView = (ImageView) findViewById(R.id.mostReviewsImageView);
        latestRestroImageView = (ImageView) findViewById(R.id.latestRestroImageView);

        Picasso.with(mActivity).load("https://dealznmealz.com/image/home1.jpg").into(mostSearchedImageView);
        Picasso.with(mActivity).load("https://dealznmealz.com/image/home2.jpeg").into(mostReviewedImageView);
        Picasso.with(mActivity).load("https://dealznmealz.com/image/home4.jpg").into(latestRestroImageView);
    }

    public void onMostSearchClicked(View view) {
        navigateToResataurantListActivity("SEARCH");
    }

    public void onMostReviewsClicked(View view) {
        navigateToResataurantListActivity("REVIEWS");
    }

    private void navigateToResataurantListActivity(String restaurantListIdentifier) {
        Intent i = new Intent(this, RestaurantListActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", restaurantListIdentifier);
        startActivity(i);

    }

    @Override
    public void onItemClick(View view, int position) {
        navigateToHotelDetailsActivity("PAID");
    }

    private void navigateToHotelDetailsActivity(String hotelDetailsIdentifier) {
        Intent i = new Intent(this, HotelDetailsActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", hotelDetailsIdentifier);
        startActivity(i);
    }

    private void loadPaidBannerData() {
        String bannerUrl = retrofitNetworkManagerService.getPaidBannerList().request().url().toString();
        Log.v(TAG, "Banner URL : "+bannerUrl);
        Call<List<PaidBanners>> userDetailsCall = retrofitNetworkManagerService.getPaidBannerList();
        userDetailsCall.enqueue(new Callback<List<PaidBanners>>() {
            @Override
            public void onResponse(Call<List<PaidBanners>> call, Response<List<PaidBanners>> response) {
                Log.v(TAG, "Response details : "+response.body());
                List<PaidBanners> paidBannerList = response.body();
                paidBannersAdapter.setPaidBannersListData(paidBannerList);
                viewPager.setAdapter(paidBannersAdapter);
                animateViewPager();
            }

            @Override
            public void onFailure(Call<List<PaidBanners>> call, Throwable t) {
                Log.v(TAG, "onFailure");
            }
        });
    }

    private void loadDiscountedHotelsData() {
        String discountsUrl = retrofitNetworkManagerService.getDiscountedHotelsList().request().url().toString();
        Log.v(TAG, "Discounts URL : "+discountsUrl);
        Call<List<DiscountedHotels>> disCountsUrlCall = retrofitNetworkManagerService.getDiscountedHotelsList();
        disCountsUrlCall.enqueue(new Callback<List<DiscountedHotels>>() {
            @Override
            public void onResponse(Call<List<DiscountedHotels>> call, Response<List<DiscountedHotels>> response) {
                Log.v(TAG, "Response details : "+response.body());
                List<DiscountedHotels> discountedHotelsList = response.body();

                offersRecyclerViewAdapter.setOffersData(discountedHotelsList);
                offersRecyclerView.setAdapter(offersRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<DiscountedHotels>> call, Throwable t) {

            }
        });
    }

    private void setUpHotDealzView() {
        // set up the RecyclerView
        hotDealzRecyclerView = findViewById(R.id.hotdealz_rv);
        hotDealzRecyclerViewAdapter = new HotDealzRecyclerViewAdapter(mActivity);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        hotDealzRecyclerView.setLayoutManager(horizontalLayoutManagaer);
        loadHotDealzData();
    }

    private void loadHotDealzData() {
        String hotDealzUrl = retrofitNetworkManagerService.getHotDealzList().request().url().toString();
        Log.v(TAG, "HOT DEALZ URL : "+hotDealzUrl);
        Call<List<HotDealzOffers>> hotDealzUrlCall = retrofitNetworkManagerService.getHotDealzList();
        hotDealzUrlCall.enqueue(new Callback<List<HotDealzOffers>>() {
            @Override
            public void onResponse(Call<List<HotDealzOffers>> call, Response<List<HotDealzOffers>> response) {
                Log.v(TAG, "Response details : "+response.body());
                List<HotDealzOffers> hotDealzOffersList = response.body();

                hotDealzRecyclerViewAdapter.setHotDealzOffersList(hotDealzOffersList);
                hotDealzRecyclerView.setAdapter(hotDealzRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<HotDealzOffers>> call, Throwable t) {

            }
        });
    }

    private void animateViewPager() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == paidBannersAdapter.getCount() - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }
}
