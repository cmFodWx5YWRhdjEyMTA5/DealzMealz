package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.ReviewsModel;
import com.restaurant.dealznmealz.viewholder.ReviewsViewHolder;

import java.util.List;


/**
 * Created by Ashish Chaudhary on 9/15/2018.
 */
public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {


    private List<ReviewsModel> reviewsList;
    private Context mContext;

    public ReviewsRecyclerAdapter(Context context) {
        mContext = context;
    }

    public void setReviewsList(List<ReviewsModel> reviewsList) {
        this.reviewsList = reviewsList;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_recycler_layout, parent, false);
        ReviewsViewHolder reviewsViewHolder = new ReviewsViewHolder(v, mContext);
        reviewsViewHolder.setReviewsList(reviewsList);
        return reviewsViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.handleOnBindViewHolder(position);
    }

    @Override
    public int getItemCount() {
        return reviewsList.size();
    }
}
