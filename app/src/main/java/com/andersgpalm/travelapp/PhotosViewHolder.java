package com.andersgpalm.travelapp;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ander on 9/1/2016.
 */
public class PhotosViewHolder extends RecyclerView.ViewHolder{

    ImageView mPhoto;
    TextView mPhotoTitle;

    public PhotosViewHolder(View itemView) {
        super(itemView);
        mPhoto = (ImageView) itemView.findViewById(R.id.rec_view_photo);
        mPhotoTitle = (TextView) itemView.findViewById(R.id.photo_title);
    }
}
