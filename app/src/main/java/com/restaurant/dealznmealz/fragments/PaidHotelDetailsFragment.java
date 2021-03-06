package com.restaurant.dealznmealz.fragments;


import android.app.AlertDialog;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.pixplicity.easyprefs.library.Prefs;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.Utils.Utils;
import com.restaurant.dealznmealz.activities.HotelDetailsActivity;
import com.restaurant.dealznmealz.adapter.HotelMenuAdapter;
import com.restaurant.dealznmealz.adapter.HotelPhotosAdapter;
import com.restaurant.dealznmealz.adapter.RestaurantListRecyclerAdapter;
import com.restaurant.dealznmealz.adapter.ReviewsRecyclerAdapter;
import com.restaurant.dealznmealz.adapter.ViewPagerMenuAdapter;
import com.restaurant.dealznmealz.model.AddReviewResponse;
import com.restaurant.dealznmealz.model.BookATableResponse;
import com.restaurant.dealznmealz.model.ListingModel;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.model.RestaurantDetailImages;
import com.restaurant.dealznmealz.model.RestaurantDetails;
import com.restaurant.dealznmealz.model.ReviewsModel;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;
import com.restaurant.dealznmealz.viewholder.RestaurantViewHolder;
import com.restaurant.dealznmealz.widget.ItemOffsetDecoration;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.widget.Button;
import android.content.DialogInterface;

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
    private String ratingCount = "1";

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
    private TextView restaurantMorningTimings;
    private TextView restaurantEveningTimings;
    private TextView restaurantRatings;
    private TextView restaurantCategory;
    private TextView restaurantDiscountPer;//txt_discount_per
    private TextView restaurantMinAmount;//txt_min_amount
    private TextView restaurantContact;

    private RecyclerView recyclerView;
    private ReviewsRecyclerAdapter reviewsRecyclerAdapter;
    private List<ReviewsModel> reviewsList;

    private TextView noReviewsText;

    private RelativeLayout detailLayout;
    private ProgressBar progressBar;
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
        detailLayout = (RelativeLayout) v.findViewById(R.id.detail_layout);
        progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        setVisibility(0);

        final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        final Button btn1 = (Button)  v.findViewById(R.id.book_table);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adb.setTitle("Plese Confirm Your Table Booking?");
                //adb.setMessage("");
                adb.setNegativeButton("No", null);
                adb.setPositiveButton("Yes", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        Map<String, String> bookATableData = new TreeMap<>();
                        bookATableData.put("uid", Prefs.getString(Utils.LOGGED_IN_USERID,""));
                        bookATableData.put("rid", restId);
                        postBookATableData(bookATableData);
                    }
                });
                adb.show();

            }
        });

        final Button btnRating1 = (Button)  v.findViewById(R.id.hotel_rating1);
        final Button btnRating2 = (Button)  v.findViewById(R.id.hotel_rating2);
        final Button btnRating3 = (Button)  v.findViewById(R.id.hotel_rating3);
        final Button btnRating4 = (Button)  v.findViewById(R.id.hotel_rating4);
        final Button btnRating5 = (Button)  v.findViewById(R.id.hotel_rating5);
        btnRating1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnRating1.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating2.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                btnRating3.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                btnRating4.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                btnRating5.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                ratingCount ="1";
                showAddReviewScreen();
            }
        });


        btnRating2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnRating1.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating2.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating3.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                btnRating4.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                btnRating5.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                ratingCount ="2";
                showAddReviewScreen();
            }
        });


        btnRating3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnRating1.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating2.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating3.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating4.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                btnRating5.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                ratingCount ="3";
                showAddReviewScreen();
            }
        });


        btnRating4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnRating1.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating2.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating3.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating4.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating5.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_gray)));
                ratingCount ="4";
                showAddReviewScreen();
            }
        });


        btnRating5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btnRating1.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating2.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating3.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating4.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                btnRating5.setBackground((getResources().getDrawable(R.drawable.custom_rectangle_green)));
                ratingCount ="5";
                showAddReviewScreen();
            }
        });

        fetchRestaurantDetailsData();
        setUpRestaurantReviewsList(v);
        initializeTextViews(v);
        setUpViewPagerMenu(v);
        //setUpHorizontalMenuAdapter(v);
        //setUpHorizontalPhotosAdapter(v);
        initializeMapView();
        return v;
    }

    private void showAddReviewScreen() {
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(mContext);
        View promptsView = li.inflate(R.layout.addreview_prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Map<String, String> addReviewData = new TreeMap<>();
                                addReviewData.put("userid", Prefs.getString(Utils.LOGGED_IN_USERID,""));
                                addReviewData.put("restid", restId);
                                addReviewData.put("rating",ratingCount);
                                addReviewData.put("txtreview",userInput.getText().toString());
                                postAddReviewData(addReviewData);
                                // get user input and set it to result
                                // edit text
                                //userInput.getText();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    private void postAddReviewData(Map<String, String> addReviewData) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        String registrationUrl = retrofitNetworkManagerService.addReviewForRestaurant(addReviewData).request().url().toString();
        Log.v(TAG, "Add  Review URL : "+registrationUrl);
        Call<AddReviewResponse> addReviewCall = retrofitNetworkManagerService.addReviewForRestaurant(addReviewData);
        addReviewCall.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(Call<AddReviewResponse> call, Response<AddReviewResponse> response) {
                AddReviewResponse regResponse = response.body();
                adb.setTitle("Your Review Status");
                adb.setMessage(regResponse.getAddReviewResponse());
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                adb.show();
            }

            @Override
            public void onFailure(Call<AddReviewResponse> call, Throwable t) {
                adb.setTitle("Your Review Status");
                adb.setMessage("Something Went Wrong!!");
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                adb.show();

            }
        });
    }

    private void postBookATableData(Map<String, String> bookATableData) {
        final AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
        String registrationUrl = retrofitNetworkManagerService.postBookTableData(bookATableData).request().url().toString();
        Log.v(TAG, "Book A Table URL : "+registrationUrl);
        Call<BookATableResponse> userDetailsCall = retrofitNetworkManagerService.postBookTableData(bookATableData);
        userDetailsCall.enqueue(new Callback<BookATableResponse>() {
            @Override
            public void onResponse(Call<BookATableResponse> call, Response<BookATableResponse> response) {
                Log.v(TAG, "Book A Table : "+response.body().toString());
                BookATableResponse regResponse = response.body();
                adb.setTitle("Booking Status");
                adb.setMessage(regResponse.getBookAtableResponse());
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                adb.show();


            }

            @Override
            public void onFailure(Call<BookATableResponse> call, Throwable t) {
                adb.setTitle("Booking Status");
                adb.setMessage("Error Occured");
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                    }
                });
                adb.show();
            }
        });
    }

    private void initializeTextViews(View v) {
        restaurantName = v.findViewById(R.id.txt_hotel_name);
        restaurantAddress = v.findViewById(R.id.txt_hotel_completeaddress);
        restaurantOpenStatus = v.findViewById(R.id.txt_hotel_opening_status);
        restaurantMorningTimings = v.findViewById(R.id.txt_hotel_timing);
        restaurantEveningTimings = v.findViewById(R.id.txt_hotel_evening_value);
        restaurantRatings = v.findViewById(R.id.hotel_rating);
        restaurantCategory = v.findViewById(R.id.txt_hotel_category_value);
        restaurantContact = v.findViewById(R.id.txt_phone_no);
        restaurantDiscountPer = v.findViewById(R.id.txt_discount_per);
        restaurantMinAmount = v.findViewById(R.id.txt_min_amount);
    }

    private void setUpViewPagerMenu(View view) {
        viewPager = view.findViewById(R.id.pager);

        restaurantPagerAdapter = new ViewPagerMenuAdapter(mContext);
    }

    private void loadPaidBannerData() {

        List<String> hotelDetailImages = new ArrayList<>();
        hotelDetailImages.add(restaurantDetails.getRestaurantDetailImages().getRestaurantImage_0());
        hotelDetailImages.add(restaurantDetails.getRestaurantDetailImages().getRestaurantImage_1());
        hotelDetailImages.add(restaurantDetails.getRestaurantDetailImages().getRestaurantImage_2());
        restaurantPagerAdapter.setHotelDetailsImages(hotelDetailImages);
        viewPager.setAdapter(restaurantPagerAdapter);
        animateViewPager();

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

/*    private void setUpHorizontalMenuAdapter(View view) {

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
    }*/

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
        Call<Object> restaurantDetailsUrlCall = retrofitNetworkManagerService.getRestaurantDetails(restId);
        String url = restaurantDetailsUrlCall.request().url().toString();
        Log.v(TAG, "Restaurant Details Url - "+url);
        restaurantDetailsUrlCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                try {
                    Log.v(TAG, "On Response");
                    Object object = response.body();
                    Gson gson = new Gson();
                    String jsonObjectString = gson.toJson(object);
                    RestaurantDetails[] restaurantDetailsArray = gson.fromJson(jsonObjectString, RestaurantDetails[].class);
                    restaurantDetails = restaurantDetailsArray[0];
                    Log.v(TAG, "On Response List :- " + restaurantDetails.toString());
                    setRestaurantData();
                    loadPaidBannerData();
                    setVisibility(1);
                }
                catch (Exception ex) {}
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.v(TAG, "onFailure most searched data");
            }
        });
    }

    private void setRestaurantData() {
        restaurantName.setText(restaurantDetails.getRestaurantName());
        restaurantAddress.setText(restaurantDetails.getRestaurantAddress());
        restaurantOpenStatus.setText(restaurantDetails.getRestaurantOpeningHours());
        restaurantMorningTimings.setText(restaurantDetails.getRestaurantMorningTime());
        restaurantEveningTimings.setText(restaurantDetails.getRestaurantEveningTime());
        restaurantRatings.setText(String.valueOf(restaurantDetails.getRestaurantRating()));
        restaurantCategory.setText(restaurantDetails.getRestaurantCategory());
        restaurantContact.setText(restaurantDetails.getRestaurantContact());
        restaurantDiscountPer.setText("Discount "+restaurantDetails.getRestaurantDiscountPercent());
        restaurantMinAmount.setText("* Discount Available on minimum billing of Rs:" +restaurantDetails.getRestaurantMinimumBillAmount());


        Double lat = Double.parseDouble(restaurantDetails.getRestaurantLatitude());
        Double lng = Double.parseDouble(restaurantDetails.getRestaurantLongitude());
        locData.setRestaurantLocation(new LatLng(lat, lng));

        if (restaurantDetails.getReviewList().size() != 0) {
            reviewsRecyclerAdapter.setReviewsList(restaurantDetails.getReviewList());
            recyclerView.setAdapter(reviewsRecyclerAdapter);
        } else {
            noReviewsText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void setUpRestaurantReviewsList(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.reviews_recycler);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerAdapter = new ReviewsRecyclerAdapter(mActivity);
        noReviewsText = (TextView) v.findViewById(R.id.no_reviews_text);
    }

    private void setVisibility(int visibility) {
        switch (visibility) {
            case 0:
                progressBar.setVisibility(View.VISIBLE);
                detailLayout.setVisibility(View.GONE);
                break;

            case 1:
                progressBar.setVisibility(View.GONE);
                detailLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
}