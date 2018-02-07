package com.restaurant.dealznmealz.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.fragments.MostSearchedRestaurantListFragment;
import com.restaurant.dealznmealz.fragments.PaidHotelDetailsFragment;

public class HotelDetailsActivity extends AppCompatActivity {

    private String hotelDetailsIdentifier = "PAID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        hotelDetailsIdentifier = getIntent().getStringExtra("FRAGMENT_IDENTIFIER");

        setUpFragements(hotelDetailsIdentifier);
    }

    private void setUpFragements(String hotelDetailsIdentifier) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(hotelDetailsIdentifier) {
            case "FREE":
                fragmentTransaction.add(R.id.hotel_details_layout, new MostSearchedRestaurantListFragment(), "SearchFragment");
                break;

            case "PAID":
                fragmentTransaction.add(R.id.hotel_details_layout, new PaidHotelDetailsFragment(), "PaidFragment");
                break;
        }

        fragmentTransaction.commit();
    }
}
