package com.restaurant.dealznmealz.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.RestaurantDetails;

import java.util.ArrayList;

/**
 * Created by ashis on 01-10-2017.
 */

public class RestaurantViewHolder extends RecyclerView.ViewHolder implements OnMapReadyCallback {

    private Context mContext;

    private ImageView hotelImageView;
    private TextView hotelNameTextView;
    private TextView hotelAddressTextView;
    private TextView hotelPhoneNumberTextView;
    private TextView hotelReviewsCount;
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

    public RestaurantViewHolder(View itemView, Context context) {
        super(itemView);
        initializeViews(itemView);
        initializeMapView();
        mContext = context;
    }

    private void initializeViews(View itemView) {
        hotelImageView = (ImageView) itemView.findViewById(R.id.hotel_image_view);
        hotelNameTextView = (TextView) itemView.findViewById(R.id.hotel_name);
        hotelAddressTextView = (TextView) itemView.findViewById(R.id.hotel_address);
        hotelPhoneNumberTextView = (TextView) itemView.findViewById(R.id.hotel_contact);
        hotelReviewsCount = (TextView) itemView.findViewById(R.id.hotel_reviews_count);
        hotelMapView = (MapView) itemView.findViewById(R.id.hotel_map_view);
    }

    public void setGarageListDetails(ArrayList<RestaurantDetails> garageDetailsList) {
    }

    public void handleOnBindViewHolder(int position) {

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
     * Displays a {@link NamedLocation} on a
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
