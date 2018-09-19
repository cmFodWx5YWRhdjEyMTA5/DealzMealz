package com.restaurant.dealznmealz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.Utils.Utils;
import com.restaurant.dealznmealz.model.DealzMealzUserDetails;
import com.restaurant.dealznmealz.model.RegistrationResponse;
import com.restaurant.dealznmealz.network.RetrofitNetworkManager;
import com.restaurant.dealznmealz.network.RetrofitNetworkManagerService;

import java.util.Map;
import java.util.TreeMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by EE207823 on 1/3/2018.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = SignUpActivity.class.getSimpleName();

    private EditText nameInput;
    private EditText mobileNumberInput;
    private EditText emailInput;
    private EditText birthDateInput;
    private EditText userNameInput;
    private EditText passwordInput;
    private Button registerMe;

    private RetrofitNetworkManager retrofitNetworkManager = RetrofitNetworkManager.getNetworkManager();
    private RetrofitNetworkManagerService retrofitNetworkManagerService = retrofitNetworkManager.getNetworkManagerService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
    }

    private void initView() {
        nameInput = findViewById(R.id.nameInput);
        mobileNumberInput = findViewById(R.id.mobileNumInput);
        emailInput = findViewById(R.id.emailInput);
        birthDateInput = findViewById(R.id.birthDateInput);
        userNameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        passwordInput.setVisibility(View.INVISIBLE);
        passwordInput.setText("default123");
        registerMe = findViewById(R.id.registerMe);
        registerMe.setOnClickListener(this);
        String userID = Prefs.getString(Utils.LOGGED_IN_USERID,"");
        if(Prefs.getBoolean(Utils.IS_USER_LOGGED_OR_NOT,false) && userID.trim().length() > 0)
        {
            navigateToLoginScreen();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registerMe:
                registerUser();
                break;


        }
    }

    private void registerUser() {
        String name = nameInput.getText().toString();
        String mobileNumber = mobileNumberInput.getText().toString();
        String emailId = emailInput.getText().toString();
        String birthDate = birthDateInput.getText().toString();
        String userName = userNameInput.getText().toString();
        String passWord = passwordInput.getText().toString();

        if (name.length() != 0 && mobileNumber.length() == 10
                && emailId.length() != 0  && birthDate.length() != 0
                && userName.length() != 0 && passWord.length() != 0) {
            Map<String, String> userRegistrationData = new TreeMap<>();
            userRegistrationData.put("name", name);
            userRegistrationData.put("mob", mobileNumber);
            userRegistrationData.put("email", emailId);
            userRegistrationData.put("birth", birthDate);
            userRegistrationData.put("username", userName);
            userRegistrationData.put("password", passWord);
            postUserRegistrationData(userRegistrationData);
        } else {

        }
    }

    private void postUserRegistrationData(Map<String, String> userRegistrationData) {
        String registrationUrl = retrofitNetworkManagerService.postUserRegistrationData(userRegistrationData).request().url().toString();
        Log.v(TAG, "Registration URL : "+registrationUrl);
        Call<RegistrationResponse> userDetailsCall = retrofitNetworkManagerService.postUserRegistrationData(userRegistrationData);
        userDetailsCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                Log.v(TAG, "Registration response : "+response.body().toString());
                RegistrationResponse regResponse = response.body();
                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                Prefs.putString(Utils.LOGGED_IN_USERID,regResponse.getRegistrationResponse());
                Prefs.putBoolean(Utils.IS_USER_LOGGED_OR_NOT, true);
                navigateToLoginScreen();

            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Something went wrong try after sometime...", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateToLoginScreen() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
