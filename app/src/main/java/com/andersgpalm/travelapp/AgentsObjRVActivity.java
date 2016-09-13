package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class AgentsObjRVActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agents);

        mContext = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_bar);
        setSupportActionBar(toolbar);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.booking_items_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AgentsObjRVActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        BookingObjRVAdapter arrayAdapter = new BookingObjRVAdapter(AgentsObjRVActivity.this);
        recyclerView.setAdapter(arrayAdapter);
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
                return true;
            case R.id.more_photos:
                Intent intent3 = new Intent(mContext, PhotoGalleryRVActivity.class);
                mContext.startActivity(intent3);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
