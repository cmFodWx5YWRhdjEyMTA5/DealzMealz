package com.restaurant.dealznmealz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.Utils.Utils;
import com.restaurant.dealznmealz.model.AddReviewResponse;
import com.restaurant.dealznmealz.model.RegistrationResponse;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;

import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by EE207823 on 1/3/2018.
 */

public class AddReviewActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = AddReviewActivity.class.getSimpleName();

    private EditText reviewTextInput;
    private Button btnAddReview;

    private RetrofitNetworkManager retrofitNetworkManager = RetrofitNetworkManager.getNetworkManager();
    private RetrofitNetworkManagerService retrofitNetworkManagerService = retrofitNetworkManager.getNetworkManagerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreview);
        initView();
    }

    private void initView() {
        reviewTextInput = findViewById(R.id.reviewTextInput);
        btnAddReview = findViewById(R.id.btnAddReview);
        btnAddReview.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddReview:
                addReview();
                break;


        }
    }

    private void addReview() {
        String reviewText = reviewTextInput.getText().toString();
        Map<String, String> addReviewData = new TreeMap<>();
        addReviewData.put("txtreview", reviewText);
        addReviewData.put("rating", "4");
        addReviewData.put("userid",Prefs.getString(Utils.LOGGED_IN_USERID,""));
        addReviewData.put("restid","1");
        postAddReviewData(addReviewData);
    }

    private void postAddReviewData(Map<String, String> addReviewData) {
        String registrationUrl = retrofitNetworkManagerService.addReviewForRestaurant(addReviewData).request().url().toString();
        Log.v(TAG, "Add  Review URL : "+registrationUrl);
        Call<AddReviewResponse> addReviewCall = retrofitNetworkManagerService.addReviewForRestaurant(addReviewData);
        addReviewCall.enqueue(new Callback<AddReviewResponse>() {
            @Override
            public void onResponse(Call<AddReviewResponse> call, Response<AddReviewResponse> response) {
                navigateToLoginScreen();
            }

            @Override
            public void onFailure(Call<AddReviewResponse> call, Throwable t) {
                Toast.makeText(AddReviewActivity.this, "Something went wrong try after sometime...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
