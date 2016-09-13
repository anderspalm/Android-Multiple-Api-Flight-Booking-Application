package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class LandingPageActivity extends AppCompatActivity {

    Context mContext;
    LinearLayout pageLayout;
    public String mInboundDate, mOutboundDate, mOutboundAirport;
    public double mMaxPrice;
    private static final String TAG = "LandingPageActivity";
    Button mToSecondActivity, mInDate, mOutDate;
    static final int date_ID_OUT = 1;
    static final int date_ID_IN = 0;
    int inYear, inMonth, inDay, outDay, outMonth, outYear;
    DatePicker inDatepicker, outDatepicker;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);
        mContext = LandingPageActivity.this;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_bar);
//        toolbar.setTitle(null);
//        setSupportActionBar(toolbar);

//        bottomBar(savedInstanceState, LandingPageActivity.this);

        pageLayout = (LinearLayout) findViewById(R.id.landing_page);

        pageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inDatepicker.setVisibility(View.GONE);
                outDatepicker.setVisibility(View.GONE);
                String inAdjDay;
                String inAdjMonth;
                int inMonth = inDatepicker.getMonth();
                int tempMonth = inMonth + 1;
                inAdjMonth = String.valueOf(tempMonth);
                if (tempMonth < 10) {
                    inAdjMonth = "0" + inAdjMonth;
                }

                int inDay = inDatepicker.getDayOfMonth();
                inAdjDay = String.valueOf(inDay);
                if (inDay < 10) {
                    inAdjDay = "0" + inDay;
                }
                int inYear = inDatepicker.getYear();
                mInboundDate = inYear + "-" + inAdjMonth + "-" + inAdjDay;

//

                String outAdjDay;
                String outAdjMonth;
                int outMonth = outDatepicker.getMonth();
                int tempMonth2 = outMonth + 1;
                outAdjMonth = String.valueOf(tempMonth2);
                if (tempMonth2 < 10) {
                    outAdjMonth = "0" + tempMonth2;
                }

                int outDay = outDatepicker.getDayOfMonth();
                outAdjDay = String.valueOf(outDay);
                if (outDay < 10) {
                    outAdjDay = "0" + outDay;
                }
                int outYear = outDatepicker.getYear();
                mOutboundDate = outYear + "-" + outAdjMonth + "-" + outAdjDay;


                Log.i(TAG, "onDateSet: out" + outYear + "-" + outAdjMonth + "-" + outDay);
                Log.i(TAG, "onDateSet: in" + inYear + "-" + inAdjMonth + "-" + inDay);
            }
        });

        mInDate = (Button) findViewById(R.id.in_date);
        mOutDate = (Button) findViewById(R.id.out_date);

        outDatepicker = (DatePicker) findViewById(R.id.outDatePicker);
        inDatepicker = (DatePicker) findViewById(R.id.inDatePicker);

        mInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialog(date_ID_IN);
                inDatepicker.setVisibility(View.VISIBLE);
                Log.i(TAG, "onClick: inList" + inDatepicker.getYear() + "-" + inDatepicker.getMonth() + "-" + inDatepicker.getDayOfMonth());
            }
        });

        mOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showDialog(date_ID_OUT);
                outDatepicker.setVisibility(View.VISIBLE);
                Log.i(TAG, "onClick: outList" + outDatepicker.getYear() + "-" + outDatepicker.getMonth() + "-" + outDatepicker.getDayOfMonth());
            }
        });

        final Spinner airportSpinner = (Spinner) findViewById(R.id.airport_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,
                R.id.spinner_item, DBHelper.getInstance(this).getAllAirportCodes());
        airportSpinner.setAdapter(adapter);
        airportSpinner.setBackgroundColor(View.INVISIBLE);
        ArrayList<String> priceArray = new ArrayList<>();
        priceArray.add("1000");
        priceArray.add("1500");
        priceArray.add("2500");
        priceArray.add("More");
        airportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mOutboundAirport = airportSpinner.getSelectedItem().toString();
                Log.i(TAG, "onItemClick: destination airport: " + mOutboundAirport);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final Spinner priceSpinner = (Spinner) findViewById(R.id.price_spinner);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_item,
                R.id.spinner_item, priceArray);
        priceSpinner.setAdapter(adapter2);
        priceSpinner.setBackgroundColor(View.INVISIBLE);
        priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        mMaxPrice = 1000;
                        break;
                    case 1:
                        mMaxPrice = 1500;
                        break;
                    case 2:
                        mMaxPrice = 2500;
                        break;
                    case 3:
                        mMaxPrice = 2501;
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        mToSecondActivity = (Button) findViewById(R.id.toSecondActivity);

        mToSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOutboundAirport != null && mInboundDate != null && mOutboundDate != null) {
                    Log.i(TAG, "onClick: " + mOutboundAirport);
                    Log.i(TAG, "onClick: " + mOutboundDate + " " + mInboundDate);
                    String outbound = mOutboundAirport.substring(mOutboundAirport.length() - 4, mOutboundAirport.length() - 1);

                    InitialValuesObj initialValuesObj = new InitialValuesObj(mMaxPrice, mOutboundDate, mInboundDate, outbound);
                    MasterListSingleton.getInstance().setmInitialValuesObj(initialValuesObj);

                    Log.i(TAG, "onClick: outboundFlight" + outbound);
                    Intent intent = new Intent(LandingPageActivity.this, MainEventPageActivity.class);
                    startActivity(intent);
                } else {
//                    Toast.makeText(LandingPageActivity.this, " Please Choose Your Dates ", Toast.LENGTH_SHORT).show();
                   Snackbar.make(view,"Please Choose Your Dates",Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).setAction("Action",null).show();
                }
            }
        });

    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        cal = Calendar.getInstance();
//        switch (id) {
//            case 0:
//                DatePickerDialog datePickerDialog1 = new DatePickerDialog(this, inDateListener, inYear, inMonth, inDay);
//                datePickerDialog1.getDatePicker().setMinDate(cal.getTimeInMillis());
//                return datePickerDialog1;
//            case 1:
//                DatePickerDialog datePickerDialog = new DatePickerDialog(this, outDateListener, outYear, outMonth, outDay);
//                long currentTime = new Date().getTime();
//                datePickerDialog.getDatePicker().setMinDate(currentTime);
//                return datePickerDialog;
//        }
//        return null;
//    }

//
//    DatePickerDialog.OnDateSetListener inDateListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//            String adjustedDay;
//            String adjustedMonth;
//            int tempMonth = month + 1;
//            adjustedMonth = String.valueOf(tempMonth);
//            if (tempMonth < 10) {
//                adjustedMonth = "0" + adjustedMonth;
//            }
//            adjustedDay = String.valueOf(day);
//            if (day < 10) {
//                adjustedDay = "0" + day;
//            }
//            mInboundDate = year + "-" + adjustedMonth + "-" + adjustedDay;
//            Log.i(TAG, "onDateSet: " + year + "-" + adjustedMonth + "-" + day);
//        }
//    };
//
//    DatePickerDialog.OnDateSetListener outDateListener = new DatePickerDialog.OnDateSetListener() {
//        @Override
//        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//            String adjustedDay;
//            String adjustedMonth;
//            int tempMonth = month + 1;
//            adjustedMonth = String.valueOf(tempMonth);
//            if (tempMonth < 10) {
//                adjustedMonth = "0" + adjustedMonth;
//            }
//            adjustedDay = String.valueOf(day);
//            if (day < 10) {
//                adjustedDay = "0" + day;
//            }
//            mOutboundDate = year + "-" + adjustedMonth + "-" + adjustedDay;
//            Log.i(TAG, "onDateSet: " + year + "-" + adjustedMonth + "-" + day);
//        }
//    };
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.bottom_menu_items, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.information_page:
//                Intent intent2 = new Intent(mContext, MainEventPageActivity.class);
//                mContext.startActivity(intent2);
//                return true;
//            case R.id.booking_page:
//                Intent intent = new Intent(mContext, AgentsObjRVActivity.class);
//                mContext.startActivity(intent);
//                return true;
//            case R.id.more_photos:
//                Intent intent3 = new Intent(mContext, PhotoGalleryRVActivity.class);
//                mContext.startActivity(intent3);
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}


