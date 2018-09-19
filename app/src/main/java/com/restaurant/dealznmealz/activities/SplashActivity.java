package com.restaurant.dealznmealz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pixplicity.easyprefs.library.Prefs;
import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.Utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private int timerTaskInMillis = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setUpTimerTask(timerTaskInMillis);
    }

    private void setUpTimerTask(final int time) {

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                checkIfUserLoggedOrNot();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, time);

    }

    private void checkIfUserLoggedOrNot() {
        boolean isUserLoggedOrNot = Prefs.getBoolean(Utils.IS_USER_LOGGED_OR_NOT, false);
        if (isUserLoggedOrNot) {
            navigateToHomeActivity();
        } else {
            navigateToLoginActivity();
        }
    }

    private void navigateToLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void navigateToHomeActivity() {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}