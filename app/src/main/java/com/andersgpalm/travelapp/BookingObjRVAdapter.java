package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ander on 9/2/2016.
 */
public class BookingObjRVAdapter extends RecyclerView.Adapter<BookingObjViewHolder> {

    Context mContext;
    ArrayList<BookingObj> mList;
    BookingObjViewHolder BDVH;

    public BookingObjRVAdapter(Context context, ArrayList<BookingObj> arrayList){
        mContext = context;
        mList = arrayList;
    }

    @Override
    public BookingObjViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.booking_rec_view_items,parent,false);
        BDVH = new BookingObjViewHolder(view);
        return BDVH;
    }

    @Override
    public void onBindViewHolder(BookingObjViewHolder holder, final int position) {

        BDVH.mWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AgentWebView.class);
                intent.putExtra("url", mList.get(position).getmDeepLinkUrl());
                mContext.startActivity(intent);
            }
        });

        // agent photo
        Picasso.with(mContext).load(mList.get(position).getmAgentPic()).fit().into(BDVH.mAgentPic);

        BDVH.mPrice.setText(mList.get(position).getmPrice());

        BDVH.mDestinedAirportName.setText(mList.get(position).getdAirName());
        BDVH.mDestinedCity.setText(mList.get(position).getmDestinedCity());
//        BDVH.mInDate.setText(mList.get(position).getmInboundDate());
        BDVH.mDestinedCountry.setText(mList.get(position).getmDcountry());

        BDVH.mOriginAirportName.setText(mList.get(position).getoAirName());
//        BDVH.mOutDate.setText(mList.get(position).getmOutboundDate());
        BDVH.mOriginCity.setText(mList.get(position).getmOriginCity());
        BDVH.mOriginCountry.setText(mList.get(position).getmOCountry());

    }

    @Override
    public int getItemCount() {
        if (mList == null || mList.size() == 0) {
            return 0;
        }
        else{ return mList.size();}
    }
}
