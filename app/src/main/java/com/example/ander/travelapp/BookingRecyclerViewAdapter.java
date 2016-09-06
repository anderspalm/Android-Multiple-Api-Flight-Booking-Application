package com.example.ander.travelapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ander on 9/2/2016.
 */
public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingDetailViewHolder> {

    Context mContext;
    List mList;

    public BookingRecyclerViewAdapter(Context context,List list){
        mContext = context;
        mList = list;
    }

    @Override
    public BookingDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.booking_items_recycler_view_items,parent,false);
        BookingDetailViewHolder BDVH = new BookingDetailViewHolder(view);
        return BDVH;
    }

    @Override
    public void onBindViewHolder(BookingDetailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
