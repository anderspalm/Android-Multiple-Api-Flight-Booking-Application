package com.andersgpalm.travelapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ander on 9/1/2016.
 */
public class PhotosRecyclerViewAdapter extends RecyclerView.Adapter<PhotosViewHolder> {

    private static final String TAG = "RegionRecyclerViewAd";
    List<ImageGalleryObj> mPhotoSet;
    Context mContext;

    public PhotosRecyclerViewAdapter(Context context) {
        mContext = context;
        mPhotoSet = MasterListSingleton.getInstance().getmImageGalleryObjs();
    }

    @Override
    public PhotosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photos_recycler_view_items, parent, false);
        PhotosViewHolder recyclerView = new PhotosViewHolder(view);
        return recyclerView;
    }

    @Override
    public void onBindViewHolder(final PhotosViewHolder holder, final int position) {


        holder.mPhotoTitle.setText(mPhotoSet.get(position).getTitle());

        Picasso.with(mContext)
                .load("https://farm" +
                        mPhotoSet.get(position).getFarm() +
                        ".staticflickr.com/" +
                        mPhotoSet.get(position).getServer() + "/" +
                        mPhotoSet.get(position).getId() + "_" +
                        mPhotoSet.get(position).getSecret() + ".jpg")
                .fit()
                .into(holder.mPhoto);

        holder.mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (mPhotoSet == null || mPhotoSet.size() == 0){
            return 0;
        }
        else {
            return mPhotoSet.size();
        }
    }
}


//switch (position) {
//        case 0:
//        Picasso.with(mContext)
//        .load("https://upload.wikimedia.org/wikipedia/commons/5/59/Sultan_Omar_Ali_Saifuddin_Mosque_02.jpg")
//        .fit()
//        .skipMemoryCache()
//        .into(holder.mRegionImage);
//        break;
//        case 1:
//        Picasso.with(mContext)
//        .load("http://media1.markusharju.se/2016/01/OluffaGrekland4.jpg")
//        .fit()
//        .skipMemoryCache()
//        .into(holder.mRegionImage);
//        break;
//        case 2:
//        Picasso.with(mContext)
//        .load("http://image.sciencenet.cn/album/201510/08/0714100xz3dq92a10yxyi4.jpg")
//        .fit()
//        .skipMemoryCache()
//        .into(holder.mRegionImage);
//        break;
//        case 3:
//        Picasso.with(mContext)
//        .load("https://www.lonelyplanet.com/travel-blog/tip-article/wordpress_uploads/2016/01/Bison-crossing-river-in-Yellowstone.-Image-courtesy-of-Wyoming-Office-of-Tourism.jpg")
//        .fit()
//        .skipMemoryCache()
//        .into(holder.mRegionImage);
//        break;
//        }