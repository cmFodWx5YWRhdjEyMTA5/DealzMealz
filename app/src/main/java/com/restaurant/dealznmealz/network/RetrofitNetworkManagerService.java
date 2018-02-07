package com.restaurant.dealznmealz.network;

import com.restaurant.dealznmealz.model.DealzMealzUserDetails;
import com.restaurant.dealznmealz.model.DiscountedHotels;
import com.restaurant.dealznmealz.model.HotDealzOffers;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.model.RegistrationResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by ee207823 on 10-07-2017.
 */

public interface RetrofitNetworkManagerService {

    /* Login API call to get auth token */
    @GET("?loginuser=1")
    Call<DealzMealzUserDetails> getLoginToken(@Query("username") String username, @Query("pass") String password);

    /* Generate OTP API call */
    @GET("?forgotpassword=1")
    Call<ResponseBody> getForgotPasswordOTP(@Query("mob") long mobNum);

    /* Reset old password */
    @GET("?changepass=1")
    Call<ResponseBody> postNewPassword(@QueryMap Map<String, Object> newPasswordParams);

    /* SignUp API call */
    @GET("?signup=1")
    Call<RegistrationResponse> postUserRegistrationData(@QueryMap Map<String, String> userRegData);

    @GET("?paidbanners=1")
    Call<List<PaidBanners>> getPaidBannerList();

    @GET("?hotdealz=1")
    Call<List<HotDealzOffers>> getHotDealzList();

    @GET("?discount=1")
    Call<List<DiscountedHotels>> getDiscountedHotelsList();

}
