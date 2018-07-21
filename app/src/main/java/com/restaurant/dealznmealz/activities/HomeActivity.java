package com.restaurant.dealznmealz.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.adapter.HotDealzRecyclerViewAdapter;
import com.restaurant.dealznmealz.adapter.OffersRecyclerViewAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.interfaces.RecyclerViewClickListener;
import com.restaurant.dealznmealz.listener.RecyclerViewTouchListener;
import com.restaurant.dealznmealz.model.CategoryModel;
import com.restaurant.dealznmealz.model.DealzMealzUserDetails;
import com.restaurant.dealznmealz.model.DiscountedHotels;
import com.restaurant.dealznmealz.model.HotDealzOffers;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.model.SearchLocationModel;
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

    private TextView searchByCategoryText;
    private TextView searchByPriceText;
    private TextView searchByLocationText;
    private TextView searchView;

    private String searchText = "";

    private List<DiscountedHotels> discountedHotelsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mActivity = this;

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViewPagerMenu();
        setToolBar();
        setUpOffersRecyclerView();
        setUpHotDealzView();
        loadBottomTabImages();
        setUpSearchViewItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopViewPagerAnimation();
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
        ab.setDisplayShowTitleEnabled(false);
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
        navigateToResataurantListActivity("MOSTSEARCH");
    }

    public void onMostReviewsClicked(View view) {
        navigateToResataurantListActivity("REVIEWS");
    }

    public void onLatestRestroClicked(View view) {
        navigateToResataurantListActivity("LATEST");
    }

    private void navigateToResataurantListActivity(String restaurantListIdentifier) {
        Intent i = new Intent(this, RestaurantListActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", restaurantListIdentifier);
        startActivity(i);

    }

    private void navigateToResataurantListActivity(String restaurantListIdentifier, int offerId, String imageUrl) {
        Intent i = new Intent(this, RestaurantListActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", restaurantListIdentifier);
        i.putExtra("OFFER_ID", offerId);
        i.putExtra("OFFER_IMG_URL", imageUrl);
        startActivity(i);
    }

    private void navigateToResataurantListActivity(String restaurantListIdentifier, int discId) {
        Intent i = new Intent(this, RestaurantListActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", restaurantListIdentifier);
        i.putExtra("DISC_ID", discId);
        startActivity(i);
    }

    private void navigateToResataurantListActivity(String restaurantListIdentifier, String searchText) {
        Intent i = new Intent(this, RestaurantListActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", restaurantListIdentifier);
        i.putExtra("SEARCH_TEXT", searchText);
        startActivity(i);
    }

    @Override
    public void onItemClick(View view, int position) {

//        navigateToHotelDetailsActivity("PAID");
        navigateToResataurantListActivity("DISCOUNTEDLIST", discountedHotelsList.get(position).getDiscId());

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
        Log.v(TAG, "Discounts URL : " + discountsUrl);
        Call<List<DiscountedHotels>> disCountsUrlCall = retrofitNetworkManagerService.getDiscountedHotelsList();
        disCountsUrlCall.enqueue(new Callback<List<DiscountedHotels>>() {
            @Override
            public void onResponse(Call<List<DiscountedHotels>> call, Response<List<DiscountedHotels>> response) {
                Log.v(TAG, "Response details : " + response.body());
                discountedHotelsList = response.body();

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
        Log.v(TAG, "HOT DEALZ URL : " + hotDealzUrl);
        Call<List<HotDealzOffers>> hotDealzUrlCall = retrofitNetworkManagerService.getHotDealzList();
        hotDealzUrlCall.enqueue(new Callback<List<HotDealzOffers>>() {
            @Override
            public void onResponse(Call<List<HotDealzOffers>> call, Response<List<HotDealzOffers>> response) {
                Log.v(TAG, "Response details : " + response.body());
                List<HotDealzOffers> hotDealzOffersList = response.body();

                hotDealzRecyclerViewAdapter.setHotDealzOffersList(hotDealzOffersList);
                hotDealzRecyclerView.setAdapter(hotDealzRecyclerViewAdapter);
                addClickListener(hotDealzOffersList);
            }

            @Override
            public void onFailure(Call<List<HotDealzOffers>> call, Throwable t) {

            }
        });
    }

    private void addClickListener(final List<HotDealzOffers> hotDealzOffersList) {
        hotDealzRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this, hotDealzRecyclerView, new RecyclerViewClickListener() {

            @Override
            public void onClick(View view, int position) {
                String hotDealzImgUrl = "https://dealznmealz.com/" + hotDealzOffersList.get(position).getHotDealzImageUrl();
                navigateToResataurantListActivity("HOTDEALZ", hotDealzOffersList.get(position).getHotDealzOfferId(), hotDealzImgUrl);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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

    private void setUpSearchViewItems() {
        searchByCategoryText = findViewById(R.id.search_category_tv);
        searchByPriceText = findViewById(R.id.search_price_tv);
        searchByLocationText = findViewById(R.id.search_location_tv);
        searchView = findViewById(R.id.search_view);

        searchByCategoryText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchCategoryData(view);
            }
        });
        searchByLocationText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchList(view);
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchText.isEmpty()) {
                    navigateToResataurantListActivity("SEARCH", searchText);
                }
            }
        });

    }

    private void fetchCategoryData(final View v) {

        String searchListUrl = retrofitNetworkManagerService.getCategoryData().request().url().toString();
        Log.v(TAG, "Category List Url : " + searchListUrl);
        Call<List<CategoryModel>> hotDealzUrlCall = retrofitNetworkManagerService.getCategoryData();
        hotDealzUrlCall.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                Log.v(TAG, "Response details : " + response.body());
                List<CategoryModel> hotDealzOffersList = response.body();
                ArrayList<String> categoryListData = new ArrayList<>();
                for (CategoryModel dataModel : hotDealzOffersList) {
                    categoryListData.add(dataModel.getCategoryName());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1);
                arrayAdapter.addAll(categoryListData);
                PopupWindow popupWindow = popupWindowDialog(arrayAdapter);
                popupWindow.showAsDropDown(v, -5, 0);
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });
    }

    private void searchList(final View v) {

        String searchListUrl = retrofitNetworkManagerService.getSearchLocationData().request().url().toString();
        Log.v(TAG, "Search List Url : " + searchListUrl);
        Call<List<SearchLocationModel>> hotDealzUrlCall = retrofitNetworkManagerService.getSearchLocationData();
        hotDealzUrlCall.enqueue(new Callback<List<SearchLocationModel>>() {
            @Override
            public void onResponse(Call<List<SearchLocationModel>> call, Response<List<SearchLocationModel>> response) {
                Log.v(TAG, "Response details : " + response.body());
                List<SearchLocationModel> hotDealzOffersList = response.body();
                ArrayList<String> searchLocDataList = new ArrayList<>();
                for (SearchLocationModel dataModel : hotDealzOffersList) {
                    searchLocDataList.add(dataModel.getArea());
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1);
                arrayAdapter.addAll(searchLocDataList);
                PopupWindow popupWindow = popupWindowDialog(arrayAdapter);
                popupWindow.showAsDropDown(v, -5, 0);
            }

            @Override
            public void onFailure(Call<List<SearchLocationModel>> call, Throwable t) {

            }
        });
    }

    public PopupWindow popupWindowDialog(final ArrayAdapter<String> dataSet) {

        // initialize a pop up window type
        PopupWindow popupWindow = new PopupWindow(this);

        // the drop down list is a list view
        ListView listViewSearch = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewSearch.setAdapter(dataSet);

        // set the item click listener
        listViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                searchText = dataSet.getItem(i);
                Log.v(TAG, "Search Text :- "+searchText);
            }
        });

        // some other visual settings
        popupWindow.setFocusable(true);
        popupWindow.setWidth(400);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        // set the list view as pop up window content
        popupWindow.setContentView(listViewSearch);
        return popupWindow;
    }
}
