package com.example.ander.travelapp;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ander on 9/1/2016.
 */
public class RegionViewHolder extends RecyclerView.ViewHolder{

    ImageView mRegionImage;
    TextView mRegionTitle;

    public RegionViewHolder(View itemView) {
        super(itemView);
        mRegionImage = (ImageView) itemView.findViewById(R.id.region_image);
        mRegionTitle = (TextView) itemView.findViewById(R.id.region_name);
    }
}
