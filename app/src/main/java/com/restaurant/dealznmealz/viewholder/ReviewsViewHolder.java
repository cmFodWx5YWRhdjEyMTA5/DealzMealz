package com.restaurant.dealznmealz.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.ReviewsModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Ashish Chaudhary on 9/15/2018.
 */
public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    private List<ReviewsModel> reviewsList;
    private TextView reviewerName;
    private TextView reviewRating;
    private TextView reviewText;

    public ReviewsViewHolder(View itemView, Context context) {
        super(itemView);
        initializeViews(itemView);
    }

    public void setReviewsList(List<ReviewsModel> reviewsList) {
        this.reviewsList = reviewsList;
    }

    private void initializeViews(View itemView) {
        reviewerName = (TextView) itemView.findViewById(R.id.reviewerName);
        reviewRating = (TextView) itemView.findViewById(R.id.reveiws_rating);
        reviewText = (TextView) itemView.findViewById(R.id.reviews_text);
    }

    public void handleOnBindViewHolder(int position) {
        ReviewsModel reviewsModel = reviewsList.get(position);

        reviewerName.setText(reviewsModel.getName());
        reviewRating.setText(reviewsModel.getRating());
        reviewText.setText(reviewsModel.getReview());
    }

}
