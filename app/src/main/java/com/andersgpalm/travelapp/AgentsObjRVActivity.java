package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabSelectedListener;

public class AgentsObjRVActivity extends BottomBarAbstractClass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_screen);

        bottomBar(savedInstanceState, AgentsObjRVActivity.this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.booking_items_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AgentsObjRVActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        BookingObjRVAdapter arrayAdapter = new BookingObjRVAdapter(AgentsObjRVActivity.this);
        recyclerView.setAdapter(arrayAdapter);
    }


    @Override
    public void bottomBar(Bundle SavedState, final Context context) {
        BottomBar bottomBar = BottomBar.attach(this, SavedState);
        bottomBar.setItemsFromMenu(R.menu.bottom_menu_items, new OnMenuTabSelectedListener() {
            @Override
            public void onMenuItemSelected(int menuItemId) {
                switch (menuItemId) {
                    case R.id.booking_page:
                        Intent intent = new Intent(context, AgentsObjRVActivity.class);
                        context.startActivity(intent);
                        break;
                    case R.id.information_page:
                        Intent intent2 = new Intent(context, MainEventPageActivity.class);
                        context.startActivity(intent2);
                        break;
                    case R.id.more_photos:
                        Intent intent3 = new Intent(context, PhotoGalleryRVActivity.class);
                        context.startActivity(intent3);
                    default:
                }
            }
        });

        bottomBar.useDarkTheme(true);
    }
}
