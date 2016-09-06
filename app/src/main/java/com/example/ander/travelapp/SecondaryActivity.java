package com.example.ander.travelapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by ander on 8/30/2016.
 */
public class SecondaryActivity extends AppCompatActivity {

    Context mContext;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        mContext = SecondaryActivity.this;

        ArrayList<RegionObj> array = DBHelper.getInstance(mContext).getRegionItems();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.regionRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        RegionRecyclerViewAdapter adapter2 = new RegionRecyclerViewAdapter(SecondaryActivity.this,array);
        recyclerView.setAdapter(adapter2);
    }

}
