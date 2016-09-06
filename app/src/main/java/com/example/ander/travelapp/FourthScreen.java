package com.example.ander.travelapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class FourthScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourth_screen);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.booking_items_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(FourthScreen.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<BookingObj> bookingObjs = InitialValuesSingleton.getInstance().getAllBookingObjs();
        BookingRecyclerViewAdapter arrayAdapter = new BookingRecyclerViewAdapter(FourthScreen.this,bookingObjs);
        recyclerView.setAdapter(arrayAdapter);
    }

}
