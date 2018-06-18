package com.restaurant.dealznmealz.fragments;


import android.app.Fragment;
import android.content.Context;
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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.activities.HotelDetailsActivity;
import com.restaurant.dealznmealz.adapter.HotelMenuAdapter;
import com.restaurant.dealznmealz.adapter.HotelPhotosAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.model.ListingModel;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.model.RestaurantDetails;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;
import com.restaurant.dealznmealz.widget.ItemOffsetDecoration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaidHotelDetailsFragment extends Fragment implements OnMapReadyCallback {

    private ViewPager viewPager;
    private ViewPagerMenuAdapter restaurantPagerAdapter;
    int images[] = {R.mipmap.hotel_two, R.mipmap.room};

    private HotelDetailsActivity mActivity;
    private Context mContext;

    private RecyclerView horizontal_recycler_view;
    private HotelMenuAdapter hotelMenuAdapter;

    private RecyclerView horizontal_recycler_photos_view;
    private HotelPhotosAdapter hotelPhotosAdapter;

    private MapView hotelMapView;
    private GoogleMap map;

    private RetrofitNetworkManager retrofitNetworkManager = RetrofitNetworkManager.getNetworkManager();
    private RetrofitNetworkManagerService retrofitNetworkManagerService = retrofitNetworkManager.getNetworkManagerService();

    private String restId;

    private NamedLocation locData = new NamedLocation("Nagpur", new LatLng(21.1458, 79.0882));

    private int currentPage = 0;
    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    /**
     * Location represented by a position ({@link com.google.android.gms.maps.model.LatLng} and a
     * name ({@link java.lang.String}).
     */
    private class NamedLocation {

        public final String name;

        public LatLng location;

        NamedLocation(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }

        public void setRestaurantLocation(LatLng latLng) {
            this.location = latLng;
        }
    }

    private final String TAG = PaidHotelDetailsFragment.class.getSimpleName();

    private RestaurantDetails restaurantDetails;

    private TextView restaurantName;
    private TextView restaurantAddress;
    private TextView restaurantOpenStatus;
    private TextView restaurantTimings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (HotelDetailsActivity) getActivity();
        mContext = mActivity;
        restId = getArguments().getString("REST_ID");
        Log.v(TAG, "Restaurant Id - "+restId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);

        View v = (View) inflater.inflate(R.layout.fragment_paid_hotel_details, container, false);

        hotelMapView = (MapView) v.findViewById(R.id.hotel_map_view);
        fetchRestaurantDetailsData();
        initializeTextViews(v);
        setUpViewPagerMenu(v);
        setUpHorizontalMenuAdapter(v);
        setUpHorizontalPhotosAdapter(v);
        initializeMapView();
        return v;
    }

    private void initializeTextViews(View v) {
        restaurantName = v.findViewById(R.id.txt_hotel_name);
        restaurantAddress = v.findViewById(R.id.txt_hotel_address);
        restaurantOpenStatus = v.findViewById(R.id.txt_hotel_openingtime);
        restaurantTimings = v.findViewById(R.id.txt_hotel_timing);
    }

    private void setUpViewPagerMenu(View view) {
        viewPager = view.findViewById(R.id.pager);

        restaurantPagerAdapter = new ViewPagerMenuAdapter(mContext);
        viewPager.setAdapter(restaurantPagerAdapter);
        loadPaidBannerData();
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

    private void setUpHorizontalMenuAdapter(View view) {

        horizontal_recycler_view = (RecyclerView) view.findViewById(R.id.horizontal_recycler_view);
        hotelMenuAdapter = new HotelMenuAdapter(mContext);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        horizontal_recycler_view.setAdapter(hotelMenuAdapter);
        horizontal_recycler_view.addItemDecoration(new ItemOffsetDecoration(10));
    }

    private void setUpHorizontalPhotosAdapter(View view) {
        horizontal_recycler_photos_view = (RecyclerView) view.findViewById(R.id.horizontal_recycler_view_photos);
        hotelPhotosAdapter = new HotelPhotosAdapter(mContext);
        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_photos_view.setLayoutManager(horizontalLayoutManagaer);
        horizontal_recycler_photos_view.setAdapter(hotelPhotosAdapter);
        horizontal_recycler_photos_view.addItemDecoration(new ItemOffsetDecoration(10));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(mContext);
        map = googleMap;
        setMapLocation(map, locData);
    }

    /**
     * Initialises the MapView by calling its lifecycle methods.
     */
    public void initializeMapView() {
        if (hotelMapView != null) {
            // Initialise the MapView
            hotelMapView.onCreate(null);
            // Set the map ready callback to receive the GoogleMap object
            hotelMapView.getMapAsync(this);
        }
    }

    /**
     * Displays a {@link RestaurantViewHolder.NamedLocation} on a
     * {@link com.google.android.gms.maps.GoogleMap}.
     * Adds a marker and centers the camera on the NamedLocation with the normal map type.
     */
    private static void setMapLocation(GoogleMap map, NamedLocation data) {
        // Add a marker for this item and set the camera
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(data.location, 13f));
        map.addMarker(new MarkerOptions().position(data.location));

        // Set the map type back to normal.
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    private void fetchRestaurantDetailsData() {
        Call<List<RestaurantDetails>> restaurantDetailsUrlCall = retrofitNetworkManagerService.getRestaurantDetails(restId);
        String url = restaurantDetailsUrlCall.request().url().toString();
        Log.v(TAG, "Restaurant Details Url - "+url);
        restaurantDetailsUrlCall.enqueue(new Callback<List<RestaurantDetails>>() {
            @Override
            public void onResponse(Call<List<RestaurantDetails>> call, Response<List<RestaurantDetails>> response) {
                Log.v(TAG, "On Response");
                List<RestaurantDetails> list = response.body();
                restaurantDetails = list.get(0);
                Log.v(TAG, "On Response List :- "+restaurantDetails.toString());
                setRestaurantData();
            }

            @Override
            public void onFailure(Call<List<RestaurantDetails>> call, Throwable t) {
                Log.v(TAG, "onFailure most searched data");
            }
        });
    }

    private void setRestaurantData() {
        restaurantName.setText(restaurantDetails.getRestaurantName());
        restaurantAddress.setText(restaurantDetails.getRestaurantAddress());
        restaurantOpenStatus.setText(restaurantDetails.getRestaurantOpeningHours());
        Double lat = Double.parseDouble(restaurantDetails.getRestaurantLatitude());
        Double lng = Double.parseDouble(restaurantDetails.getRestaurantLongitude());
        locData.setRestaurantLocation(new LatLng(lat, lng));
    }

}
