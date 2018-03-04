package com.restaurant.dealznmealz.interfaces;

import android.view.View;

/**
 * Created by Ashish Chaudhary on 3/3/2018.
 */

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);

}
