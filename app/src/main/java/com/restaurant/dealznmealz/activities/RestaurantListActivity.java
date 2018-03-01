package com.restaurant.dealznmealz.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.fragments.MostReviewsRestaurantListFragment;
import com.restaurant.dealznmealz.fragments.RestaurantListFragment;

/**
 * Created by ashis on 21-09-2017.
 */

public class RestaurantListActivity extends DealznmealzBaseActivity {

    private String restaurantListIdentifier;
    private String fragmentTitleKey = "FRAGMENT_TITLE_KEY";
    private String fragmentIdentifierKey = "FRAGMENT_IDENTIFIER";

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
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
    }

    private void setUpFragements(String restaurantListIdentifier) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(fragmentTitleKey, "Most Searched Restaurants");
        bundle.putString(fragmentIdentifierKey, restaurantListIdentifier);
        switch(restaurantListIdentifier) {
            case "SEARCH":
                bundle.putString(fragmentTitleKey, "Most Searched Restaurants");
                fragment.setArguments(bundle);
                break;

            case "REVIEWS":
                bundle.putString(fragmentTitleKey, "Most Reviewed Restaurants");
                fragment.setArguments(bundle);
                break;

            case "LATEST":
                bundle.putString(fragmentTitleKey, "Latest Restaurants");
                fragment.setArguments(bundle);
        }
        fragmentTransaction.add(R.id.restaurant_list_layout, fragment, "RestaurantListFragment");
        fragmentTransaction.commit();
    }
}
