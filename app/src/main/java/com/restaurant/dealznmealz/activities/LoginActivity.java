package com.restaurant.dealznmealz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.Utils.Utils;
import com.restaurant.dealznmealz.model.DealzMealzUserDetails;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = LoginActivity.class.getSimpleName();
    private Button fbLogin;
    private Button googleLogin;
    private Button normalLogin;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView registerNow;
    private ProgressBar progressBar;

    private RetrofitNetworkManager retrofitNetworkManager = RetrofitNetworkManager.getNetworkManager();
    private RetrofitNetworkManagerService retrofitNetworkManagerService = retrofitNetworkManager.getNetworkManagerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        fbLogin =  findViewById(R.id.fbLogin);
        googleLogin = findViewById(R.id.googleLogin);
        normalLogin =  findViewById(R.id.normalLogin);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerNow = findViewById(R.id.register_now);
        progressBar = findViewById(R.id.progress_bar);

        normalLogin.setOnClickListener(this);
        registerNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case R.id.normalLogin:
                String userName = emailInput.getText().toString();
                String passWord = passwordInput.getText().toString();

                if (userName.length() == 0 || passWord.length() == 0) {
                    Toast.makeText(LoginActivity.this, "Enter valid credentials", Toast.LENGTH_LONG).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    checkLoginData(userName, passWord);
//                navigateToHomeScreen();
                }
                break;

            case R.id.register_now :
                navigateToSignUpScreen();
                break;

            default:
                break;
        }
    }

    private void checkLoginData(String user, String pass) {

        String loginUrl = retrofitNetworkManagerService.getLoginToken(user, pass).request().url().toString();
        Log.v(TAG, "Login URL : "+loginUrl);
        Call<DealzMealzUserDetails> userDetailsCall = retrofitNetworkManagerService.getLoginToken(user, pass);
        userDetailsCall.enqueue(new Callback<DealzMealzUserDetails>() {
            @Override
            public void onResponse(Call<DealzMealzUserDetails> call, Response<DealzMealzUserDetails> response) {
                Log.v(TAG, "Response details : "+response.body());
                DealzMealzUserDetails userDetails = response.body();
                progressBar.setVisibility(View.GONE);
                if (userDetails.getMessage()!= null && userDetails.getMessage().equals("Please enter valid credentials.")) {
                    Toast.makeText(LoginActivity.this, "Please enter valid credentials.", Toast.LENGTH_LONG).show();
                } else {
                    navigateToHomeScreen();
                }
            }

            @Override
            public void onFailure(Call<DealzMealzUserDetails> call, Throwable t) {
                Log.v(TAG, "onFailure");
            }
        });
    }

    private void navigateToHomeScreen() {
        Prefs.putBoolean(Utils.IS_USER_LOGGED_OR_NOT, true);
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void navigateToSignUpScreen() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }


}
