package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.activities.HotelDetailsActivity;
import com.restaurant.dealznmealz.model.PaidBanners;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashis on 05-09-2017.
 */

public class ViewPagerMenuAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<PaidBanners> paidBannerListData;
    private ArrayList<String> bannerImageUrl;

    public ViewPagerMenuAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bannerImageUrl = new ArrayList<>();
    }

    public void setPaidBannersListData(List<PaidBanners> listData) {
        this.paidBannerListData = listData;

        Log.v("ViewPagerAdapter", "Paid Banner Object - "+paidBannerListData.get(0));

        for (PaidBanners paidBanners : paidBannerListData) {
            bannerImageUrl.add(paidBanners.getPaidHotelImageUrl());
        }

        Log.v("ViewPagerAdapter", "setPaidBannersListData");
    }

    public void setHotelDetailsImages(List<String> hotelDetailsImages) {
        bannerImageUrl.addAll(hotelDetailsImages);
    }

    @Override
    public int getCount() {
        return bannerImageUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.viewpager_menu_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        imageView.setBackgroundResource(images[position]);

        String imageUrl = "https://dealznmealz.com/" + bannerImageUrl.get(position);

        Log.v("ViewPagerAdapter", "Image Url - "+imageUrl);
        Picasso.with(context).load(imageUrl).into(imageView);

        Log.v("ViewPagerAdapter", "instantiateItem");

        container.addView(itemView);

        if (paidBannerListData != null) {
            final String restId = String.valueOf(paidBannerListData.get(position).getPaidHotelRestoId());
            Log.v("View Pager", "Restro Id - "+restId);
            //listening to image click
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navigateToHotelDetailsActivity("PAID", restId);
                }
            });
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    private void navigateToHotelDetailsActivity(String hotelDetailsIdentifier, String restId) {
        Intent i = new Intent(context, HotelDetailsActivity.class);
        i.putExtra("FRAGMENT_IDENTIFIER", hotelDetailsIdentifier);
        i.putExtra("REST_ID", restId);
        context.startActivity(i);
    }
}
