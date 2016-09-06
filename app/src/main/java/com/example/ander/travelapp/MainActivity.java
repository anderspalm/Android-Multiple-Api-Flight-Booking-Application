package com.example.ander.travelapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    public String mInboundDate;
    public String mOutboundDate;
    public String mOutboundAirport;
    public double mMaxPrice;
    private static final String TAG = "MainActivity";
    Button mToSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        mContext = MainActivity.this;

        mOutboundDate = "2016-12-20";
        mInboundDate = "2016-12-29";

        final Spinner spinner = (Spinner) findViewById(R.id.airport_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter(this,R.layout.spinner_item,R.id.airport_spinner_item,DBHelper.getInstance(this).getAllAirportCodes());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayList<String> priceArray = new ArrayList<>();
        priceArray.add("Below 500");
        priceArray.add("500 - 1000");
        priceArray.add("1000 - 1500");
        priceArray.add("1500 - 2500");
        priceArray.add("Above 2500");
        final Spinner spinner2 = (Spinner) findViewById(R.id.price_spinner);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter(this,R.layout.spinner_item,R.id.airport_spinner_item,priceArray);
        spinner2.setAdapter(adapter2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mOutboundAirport = spinner.getSelectedItem().toString();
                int color = Color.argb(255, 255, 175, 64);
//                view.setBackgroundColor(color);
                Log.i(TAG, "onItemClick: destination airport: " + mOutboundAirport);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        mMaxPrice = 500;
                        break;
                    case 1:
                        mMaxPrice = 1000;
                        break;
                    case 2:
                        mMaxPrice = 1500;
                        break;
                    case 3:
                        mMaxPrice = 2500;
                        break;
                    case 4:
                        mMaxPrice = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mToSecondActivity = (Button) findViewById(R.id.toSecondActivity);

        mToSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOutboundAirport != null) {
                    Log.i(TAG, "onClick: " + mOutboundAirport);
                    String outbound = mOutboundAirport.substring(mOutboundAirport.length()-4,mOutboundAirport.length()-1);

                    InitialValuesObj initialValuesObj = new InitialValuesObj(mMaxPrice,mOutboundDate,mInboundDate,outbound,"");
                    InitialValuesSingleton.getInstance().setmInitialValuesObj(initialValuesObj);

                    Log.i(TAG, "onClick: outboundFlight" + outbound);
                    Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this, " Please Choose a Destination ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}


