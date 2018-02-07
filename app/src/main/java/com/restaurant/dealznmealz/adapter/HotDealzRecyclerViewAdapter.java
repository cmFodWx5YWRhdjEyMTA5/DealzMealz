package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.HotDealzOffers;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by EE207823 on 1/2/2018.
 */

public class HotDealzRecyclerViewAdapter extends RecyclerView.Adapter<HotDealzRecyclerViewAdapter.HotDealzViewHolder> {

    private Context mContext;
    private List<HotDealzOffers> hotDealzOffersList;

    public HotDealzRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setHotDealzOffersList(List<HotDealzOffers> offersList) {
        hotDealzOffersList = offersList;
    }

    @Override
    public HotDealzViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotdealz_card_layout, parent, false);

        return new HotDealzRecyclerViewAdapter.HotDealzViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotDealzViewHolder holder, int position) {
        String imageUrl = "https://dealznmealz.com/" + hotDealzOffersList.get(position).getHotDealzImageUrl();
        Picasso.with(mContext).load(imageUrl).into(holder.hotDealz_Imageview);

//        holder.hotDealz_OfferText.setText(hotDealzOffersList.get(position).getHotDealzOffer());
    }

    @Override
    public int getItemCount() {
        return hotDealzOffersList.size();
    }

    public class HotDealzViewHolder extends RecyclerView.ViewHolder {

        private ImageView hotDealz_Imageview;
//        private TextView hotDealz_OfferText;

        public HotDealzViewHolder(View view) {
            super(view);
            hotDealz_Imageview = (ImageView) view.findViewById(R.id.hotdealz_iv);
//            hotDealz_OfferText = (TextView) view.findViewById(R.id.hotdealz_offer_text);
        }
    }
}
