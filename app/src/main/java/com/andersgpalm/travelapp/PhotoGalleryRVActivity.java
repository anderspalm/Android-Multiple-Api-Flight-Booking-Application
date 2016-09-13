package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ander on 8/30/2016.
 */
public class PhotoGalleryRVActivity extends AppCompatActivity {

    Context mContext;
    private static final String TAG = "PhotoGalleryRVActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);
        mContext = PhotoGalleryRVActivity.this;

        // instantiating the bottom bar

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_bar);
        setSupportActionBar(toolbar);

        // setting recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Photo_galleryRV);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(PhotoGalleryRVActivity.this, 2);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return 1 - (position % 2);
//             }
//        });
        recyclerView.setLayoutManager(gridLayoutManager);
        PhotosRecyclerViewAdapter photos = new PhotosRecyclerViewAdapter(PhotoGalleryRVActivity.this);
        recyclerView.setAdapter(photos);

        if (photos.getItemCount() == 0) {
            ImageView errorPoster = (ImageView) findViewById(R.id.no_results_poster);
            errorPoster.setVisibility(View.VISIBLE);
//            Picasso.with(context).load().fit().into(errorPoster);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information_page:
                Intent intent2 = new Intent(mContext, MainEventPageActivity.class);
                mContext.startActivity(intent2);
                return true;
            case R.id.booking_page:
                Intent intent = new Intent(mContext, AgentsObjRVActivity.class);
                mContext.startActivity(intent);
                return true;
            case R.id.more_photos:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
