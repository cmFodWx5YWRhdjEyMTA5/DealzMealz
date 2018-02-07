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
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;
import com.restaurant.dealznmealz.widget.ItemOffsetDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaidHotelDetailsFragment extends Fragment implements OnMapReadyCallback {

    private ViewPager viewPager;
    private ViewPagerMenuAdapter myCustomPagerAdapter;
    int images[] = {R.mipmap.hotel_two, R.mipmap.room};

    private HotelDetailsActivity mActivity;
    private Context mContext;

    private RecyclerView horizontal_recycler_view;
    private HotelMenuAdapter hotelMenuAdapter;

    private RecyclerView horizontal_recycler_photos_view;
    private HotelPhotosAdapter hotelPhotosAdapter;

    private MapView hotelMapView;
    private GoogleMap map;

    private NamedLocation locData = new NamedLocation("Nagpur", new LatLng(21.1458, 79.0882));

    /**
     * Location represented by a position ({@link com.google.android.gms.maps.model.LatLng} and a
     * name ({@link java.lang.String}).
     */
    private class NamedLocation {

        public final String name;

        public final LatLng location;

        NamedLocation(String name, LatLng location) {
            this.name = name;
            this.location = location;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (HotelDetailsActivity) getActivity();
        mContext = mActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);

        View v = (View) inflater.inflate(R.layout.fragment_paid_hotel_details, container, false);

        hotelMapView = (MapView) v.findViewById(R.id.hotel_map_view);
        setUpViewPagerMenu(v);
        setUpHorizontalMenuAdapter(v);
        setUpHorizontalPhotosAdapter(v);
        initializeMapView();
        return v;
    }

    private void setUpViewPagerMenu(View view) {
        viewPager = view.findViewById(R.id.pager);

        myCustomPagerAdapter = new ViewPagerMenuAdapter(mContext);
        viewPager.setAdapter(myCustomPagerAdapter);
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

}
