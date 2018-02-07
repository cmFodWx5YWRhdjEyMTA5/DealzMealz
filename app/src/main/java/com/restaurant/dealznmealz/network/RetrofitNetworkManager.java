package com.restaurant.dealznmealz.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ee207823 on 10-07-2017.
 */

public class RetrofitNetworkManager {

    private static Retrofit retrofit = null;
    private static RetrofitNetworkManager networkManagerBuilder = null;

    private RetrofitNetworkManager() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://dealznmealz.com/mobileapi.php/")
                .build();
    }

    public static RetrofitNetworkManager getNetworkManager() {
        if (networkManagerBuilder == null) {
            networkManagerBuilder = new RetrofitNetworkManager();
        }
        return networkManagerBuilder;
    }

    public RetrofitNetworkManagerService getNetworkManagerService() {
        return retrofit.create(RetrofitNetworkManagerService.class);
    }
}
