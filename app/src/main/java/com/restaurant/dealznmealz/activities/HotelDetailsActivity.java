package com.restaurant.dealznmealz.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.fragments.RestaurantListFragment;
import com.restaurant.dealznmealz.fragments.PaidHotelDetailsFragment;

public class HotelDetailsActivity extends AppCompatActivity {

    private String hotelDetailsIdentifier = "PAID";
    private String restId;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_details);
        hotelDetailsIdentifier = getIntent().getStringExtra("FRAGMENT_IDENTIFIER");
        restId = getIntent().getStringExtra("REST_ID");
        setUpFragements(hotelDetailsIdentifier);
    }

    private void setUpFragements(String hotelDetailsIdentifier) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        bundle = new Bundle();
        switch(hotelDetailsIdentifier) {
            case "FREE":
                fragmentTransaction.add(R.id.hotel_details_layout, new RestaurantListFragment(), "SearchFragment");
                break;

            case "PAID":
                PaidHotelDetailsFragment paidHotelDetailsFragment = new PaidHotelDetailsFragment();
                bundle.putString("REST_ID", restId);
                paidHotelDetailsFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.hotel_details_layout, paidHotelDetailsFragment, "PaidFragment");
                break;
        }

        fragmentTransaction.commit();
    }
}
