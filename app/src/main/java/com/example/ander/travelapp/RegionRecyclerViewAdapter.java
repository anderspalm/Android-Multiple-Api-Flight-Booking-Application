package com.example.ander.travelapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ander on 9/1/2016.
 */
public class RegionRecyclerViewAdapter extends RecyclerView.Adapter<RegionViewHolder> {

    private static final String TAG = "RegionRecyclerViewAd";
    ArrayList<RegionObj> mRegionItems;
    Context mContext;

    public RegionRecyclerViewAdapter(Context context, ArrayList<RegionObj> array) {
        mContext = context;
        mRegionItems = array;
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_recycler_view_items, parent, false);
        RegionViewHolder recyclerView = new RegionViewHolder(view);
        return recyclerView;
    }

    @Override
    public void onBindViewHolder(RegionViewHolder holder, final int position) {
        switch (position) {
            case 0:
                Picasso.with(mContext)
                        .load("https://upload.wikimedia.org/wikipedia/commons/5/59/Sultan_Omar_Ali_Saifuddin_Mosque_02.jpg")
                        .fit()
                        .into(holder.mRegionImage);
                break;
            case 1:
                Picasso.with(mContext)
                        .load("http://media1.markusharju.se/2016/01/OluffaGrekland4.jpg")
                        .fit()
                        .into(holder.mRegionImage);
                break;
            case 2:
                Picasso.with(mContext)
                        .load("http://image.sciencenet.cn/album/201510/08/0714100xz3dq92a10yxyi4.jpg")
                        .fit()
                        .into(holder.mRegionImage);
                break;
            case 3:
                Picasso.with(mContext)
                        .load("https://www.lonelyplanet.com/travel-blog/tip-article/wordpress_uploads/2016/01/Bison-crossing-river-in-Yellowstone.-Image-courtesy-of-Wyoming-Office-of-Tourism.jpg")
                        .fit()
                        .into(holder.mRegionImage);
                break;
        }

        holder.mRegionTitle.setText(mRegionItems.get(position).getmName());

        holder.mRegionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InitialValuesSingleton.getInstance().getmInitialValuesObj().setRegion(mRegionItems.get(position).getmName());

                Intent intent = new Intent(mContext, ThirdPage.class);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mRegionItems.size();
    }
}
