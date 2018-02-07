package com.restaurant.dealznmealz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.restaurant.dealznmealz.R;
import com.restaurant.dealznmealz.model.Data;

import java.util.ArrayList;

/**
 * Created by ashis on 28-09-2017.
 */

public class HotelPhotosAdapter extends RecyclerView.Adapter<HotelPhotosAdapter.HotelMenuViewHolder> {

    private ArrayList<Data> horizontalList;
    private Context context;


    public HotelPhotosAdapter(Context context) {
        horizontalList = fill_with_data();
        this.context = context;
    }


    public class HotelMenuViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        //TextView txtview;
        public HotelMenuViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.menu_imgView);
            //txtview=(TextView) view.findViewById(R.id.txtview);
        }
    }
    @Override
    public HotelMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_menu_horizontalview, parent, false);

        return new HotelMenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotelMenuViewHolder holder, final int position) {

        holder.imageView.setImageResource(horizontalList.get(position).imageId);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

            }

        });

    }
    @Override
    public int getItemCount()
    {
        return horizontalList.size();
    }

    private ArrayList<Data> fill_with_data() {

        ArrayList<Data> data = new ArrayList<>();

        data.add(new Data(R.mipmap.one));
        data.add(new Data(R.mipmap.two));
        data.add(new Data(R.mipmap.three));
        data.add(new Data(R.mipmap.four));
        data.add(new Data(R.mipmap.five));
        data.add(new Data(R.mipmap.six));
        data.add(new Data(R.mipmap.seven));
        data.add(new Data(R.mipmap.one));
        data.add(new Data(R.mipmap.two));


        return data;
    }
}
