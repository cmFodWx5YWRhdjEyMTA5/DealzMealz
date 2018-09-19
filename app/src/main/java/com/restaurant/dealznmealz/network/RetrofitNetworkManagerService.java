package com.restaurant.dealznmealz.network;

import com.restaurant.dealznmealz.model.BookATableResponse;
import com.restaurant.dealznmealz.model.CategoryModel;
import com.restaurant.dealznmealz.model.DealzMealzUserDetails;
import com.restaurant.dealznmealz.model.DiscountedHotels;
import com.restaurant.dealznmealz.model.HotDealzOffers;
import com.restaurant.dealznmealz.model.ListingModel;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.restaurant.dealznmealz.model.PostAndroidTokenResponse;
import com.restaurant.dealznmealz.model.RegistrationResponse;
import com.restaurant.dealznmealz.model.RestaurantDetails;
import com.restaurant.dealznmealz.model.SearchLocationModel;
import com.restaurant.dealznmealz.model.AddReviewResponse;

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

    @GET("?calllocation=1")
    Call<List<SearchLocationModel>> getSearchLocationData();

    @GET("?callcategory=1")
    Call<List<CategoryModel>> getCategoryData();

    @GET("?searched=1")
    Call<List<ListingModel>> getMostSearchedListingData();

    @GET("?reviewed=1")
    Call<List<ListingModel>> getMostReviewedListingData();

    @GET("?latest=1")
    Call<List<ListingModel>> getLatestListingData();

    @GET("?offdetails=1")
    Call<List<ListingModel>> getHotDealzListingData(@Query("offid") int offerId);

    @GET("?discdetails=1")
    Call<List<ListingModel>> getDiscountListingData(@Query("discid") int discId);

    @GET("?srchresto=1")
    Call<List<ListingModel>> getSearchListingData(@Query("srchtxt") String searchText);

    @GET("?offrestdisc=1")
    Call<Object> getRestaurantDetails(@Query("restid") String restId);

    /* Book Table API call */
    @GET("?bookatable=1")
    Call<BookATableResponse> postBookTableData(@QueryMap Map<String, String> restroBookingData);

    /* Add Review call */
    @GET("?addreview=1")
    Call<AddReviewResponse> addReviewForRestaurant(@QueryMap Map<String, String> addReviewData);

    /* Add Android Token call*/
    @GET("?addappcode=1")
    Call<PostAndroidTokenResponse> postAndroidTokenData(@QueryMap Map<String, String> addTokenData);



}
