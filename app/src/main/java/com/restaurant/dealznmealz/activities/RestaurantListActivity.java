package com.restaurant.dealznmealz.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.fragments.MostReviewsRestaurantListFragment;
import com.restaurant.dealznmealz.fragments.MostSearchedRestaurantListFragment;

/**
 * Created by ashis on 21-09-2017.
 */

public class RestaurantListActivity extends DealznmealzBaseActivity {

    private String restaurantListIdentifier;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        restaurantListIdentifier = getIntent().getStringExtra("FRAGMENT_IDENTIFIER");
        setToolBar();
        setUpFragements(restaurantListIdentifier);
    }

    private void setToolBar() {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_left_menu_24dp);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("dealznmealz");
    }

    private void setUpFragements(String restaurantListIdentifier) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch(restaurantListIdentifier) {
            case "SEARCH":
                fragmentTransaction.add(R.id.restaurant_list_layout, new MostSearchedRestaurantListFragment(), "SearchFragment");
                break;

            case "REVIEWS":
                fragmentTransaction.add(R.id.restaurant_list_layout, new MostReviewsRestaurantListFragment(), "ReviewsFragment");
                break;
        }

        fragmentTransaction.commit();
    }
}
