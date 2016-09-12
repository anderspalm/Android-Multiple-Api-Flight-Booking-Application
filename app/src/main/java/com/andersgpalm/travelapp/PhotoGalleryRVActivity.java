package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

/**
 * Created by ander on 8/30/2016.
 */
public class PhotoGalleryRVActivity extends AppCompatActivity {

    Context mContext;
    private static final String TAG = "PhotoGalleryRVActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        mContext = PhotoGalleryRVActivity.this;

        // instantiating the bottom bar
//        bottomBar(savedInstanceState, PhotoGalleryRVActivity.this);

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

        if(photos.getItemCount() == 0){
            ImageView errorPoster = (ImageView) findViewById(R.id.no_results_poster);
            errorPoster.setVisibility(View.VISIBLE);
//            Picasso.with(context).load().fit().into(errorPoster);
        }
    }

//    @Override
//    public void bottomBar(Bundle SavedState, final Context context) {
//        BottomBar bottomBar = BottomBar.attach(this, SavedState);
//        bottomBar.setItemsFromMenu(R.menu.bottom_menu_items, new OnMenuTabSelectedListener() {
//            @Override
//            public void onMenuItemSelected( int menuItemId) {
//                switch (menuItemId) {
//                    case R.id.booking_page:
//                        Intent intent = new Intent(context, AgentsObjRVActivity.class);
//                        context.startActivity(intent);
//                        break;
//                    case R.id.information_page:
//                        Intent intent2 = new Intent(context, MainEventPageActivity.class);
//                        context.startActivity(intent2);
//                        break;
//                    case R.id.more_photos:
//                        Intent intent3 = new Intent(context, PhotoGalleryRVActivity.class);
//                        context.startActivity(intent3);
//                    default:
//                }
//            }
//        });
//
//        bottomBar.useDarkTheme(true);
//    }
}
