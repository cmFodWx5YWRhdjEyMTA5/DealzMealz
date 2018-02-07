package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.DiscountedHotels;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashis on 07-09-2017.
 */

public class OffersRecyclerViewAdapter  extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<DiscountedHotels> discountedHotelsList;
    private ArrayList<String> offersImageUrlList;

    // data is passed into the constructor
    public OffersRecyclerViewAdapter(Context context) {
        mContext = context;
        this.mInflater = LayoutInflater.from(context);
        offersImageUrlList = new ArrayList<>();
    }

    public void setOffersData(List<DiscountedHotels> offersList) {
        discountedHotelsList = offersList;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.offers_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.offersImage.setBackgroundResource(images[position]);

        String imageUrl = "https://dealznmealz.com/" + discountedHotelsList.get(position).getDiscImageUrl();
        Picasso.with(mContext).load(imageUrl).into(holder.offersImage);

        holder.discountTitle.setText(discountedHotelsList.get(position).getDiscTitle());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return discountedHotelsList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView offersImage;
        public TextView discountTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            offersImage = (ImageView) itemView.findViewById(R.id.offers_image);
            discountTitle = (TextView) itemView.findViewById(R.id.discountTextValue);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public int getItem(int id) {
        return id;
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
