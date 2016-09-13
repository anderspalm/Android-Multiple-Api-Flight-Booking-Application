package com.andersgpalm.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by anders on 9/1/2016.
 */
public class MainEventPageActivity extends AppCompatActivity {

    ImageView mApiButton, mFilterImage, mFilterButton, mCity_image_one;
    LinearLayout mFilterLayout, mFilterLContainer;
    Button mToFlightOptions, mMorePhotos;
    Context mContext;
    HttpURLConnection conn, connection2;
    SeekBar mPriceBar, mAgentSeekBar;
    EditText mOriginEditText;
    public boolean mFilterBoolean;
    public String mLocale, mAdults, mMarket, mCurrency, mRegion, mUrlBooking, mPreCabinClass, mCabinClass;
    public String mDestinationLocation, mDestinationCountry, mDestAirportCode, mInboundDate;
    public String mWikiExtract, innerKey;
    public String mOriginAirportCode, mOutboundDate, mOutboundLegId, mInboundLegId;
    public String mSessionID, mBookingSession;
    public int mErrorCount, mAgentSeekBarPosition, mNoFlightCount, mWikiParCount;
    public int mWikiLastPosition;
    public double mMaxPrice, mRegionCount, mCabinCount, mAirportCount;

    String mApiKey = ApiClass.skyscannerApi;

    private static final String TAG = "MainEventPageActivity";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_bar);
        setSupportActionBar(toolbar);


        // setting defaults
        mContext = MainEventPageActivity.this;
        mErrorCount = 0;
        mNoFlightCount = 0;
        mMaxPrice = 0;
        conn = null;
        mMarket = "US";
        mCurrency = "USD";
        mLocale = "en-US";
        mAdults = "1";
        mPreCabinClass = "Economy";

        mOriginEditText = (EditText) findViewById(R.id.code_edit);
        mOriginEditText.setSelected(false);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );

        // getting answers from users for default filters
        InitialValuesObj initialValuesObj = MasterListSingleton.getInstance().getmInitialValuesObj();
        if (MasterListSingleton.getInstance().getmInitialValuesObj() != null) {
            String temp = initialValuesObj.getOutboundAirport();
            mOriginAirportCode = temp + "-sky";
            mOutboundDate = "2016-12-20";
            mInboundDate = "2016-12-29";
            mOutboundDate = initialValuesObj.getOutboundDate();
            mInboundDate = initialValuesObj.getInboundDate();
            mMaxPrice = initialValuesObj.getPrice();
            Log.i(TAG, "onCreate: o" + mOutboundDate);
            Log.i(TAG, "onCreate: i" + mInboundDate);
        }


        mCity_image_one = (ImageView) findViewById(R.id.city_img_1);

//                Picasso.with(MainEventPageActivity.this)
//                        .load("http://i.telegraph.co.uk/multimedia/archive/01768/BA_1768204b.jpg").placeholder(R.drawable.arrival).resize(mCity_image_one.getWidth(), mCity_image_one.getHeight())
//                        .centerCrop()
//                        .into(mCity_image_one, new Callback() {
//                            @Override
//                            public void onSuccess() {
//                                Log.i(TAG, "onSuccess: success 1");
//                            }
//
//                            @Override
//                            public void onError() {
//
//                            }
//                        });

        mPriceBar = (SeekBar) findViewById(R.id.price_seek_bar);

        // setting expandable filters functionality
        mFilterButton = (ImageView) findViewById(R.id.filter_button);
        mFilterLayout = (LinearLayout) findViewById(R.id.filters_layout);
        mFilterBoolean = false;
        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFilterBoolean) {
                    mFilterLayout.setVisibility(View.GONE);
                    mFilterBoolean = false;
                } else {
                    mFilterLayout.setVisibility(View.VISIBLE);
                    mFilterBoolean = true;
                }
            }
        });

        // cabin class filter logic
        ArrayList<String> cabinClassArray = new ArrayList<>();
        cabinClassArray.add("Economy");
        cabinClassArray.add("PremiumEconomy");
        cabinClassArray.add("Business");
        cabinClassArray.add("First");
        mCabinCount = 0;
        final Spinner cabinClass = (Spinner) findViewById(R.id.cabin_class_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.spinner_item, cabinClassArray);
        cabinClass.setAdapter(adapter);
        cabinClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mCabinCount == 1) {
                    mPreCabinClass = cabinClass.getSelectedItem().toString();
                } else {
                    mCabinCount = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // airport spinner filter button
        mAirportCount = 0;
        final Spinner airportSpinner = (Spinner) findViewById(R.id.airport_spinner);
        final ArrayAdapter<String> airportAdapter = new ArrayAdapter(this, R.layout.spinner_item, R.id.spinner_item, DBHelper.getInstance(this).getAllAirportCodes());
        airportSpinner.setAdapter(airportAdapter);
        airportSpinner.setBackgroundColor(View.INVISIBLE);
        airportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (mAirportCount == 1) {
                    String temp = airportSpinner.getItemAtPosition(i).toString();
                    String temp2 = temp.substring(temp.length() - 4, temp.length() - 1);
                    Log.i(TAG, "onItemSelected: spinner airport code after substring: " + temp2);
                    mOriginAirportCode = temp2;
                } else {
                    mAirportCount = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        if(mRegion == null ){
            mRegion = "Anywhere";
        }

        // region spinner filter button
        mRegionCount = 0;
        ArrayList<String> regionArray = new ArrayList<>();
        regionArray.add("Anywhere");
        regionArray.add("Europe");
        regionArray.add("NorthAmerica");
        regionArray.add("SouthAmerica");
        regionArray.add("SouthEastAsia");
        final Spinner regionSpinner = (Spinner) findViewById(R.id.region_spinner);
        ArrayAdapter regionAdapter = new ArrayAdapter(MainEventPageActivity.this, R.layout.spinner_item, R.id.spinner_item, regionArray);
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(mRegionCount == 1) {
                mRegion = regionSpinner.getSelectedItem().toString();
//                }
//                else {
//                    mRegionCount = 1;
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        regionSpinner.setAdapter(regionAdapter);

        // price slider filter logic
        mPriceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int price = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                price = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textView = (TextView) findViewById(R.id.seek_bar_value);
                textView.setText("$" + price);
                mMaxPrice = price;
            }
        });

        if (mMaxPrice != 0 && mPreCabinClass != null && mOriginAirportCode != null) {
            // randomize button logic
            mApiButton = (ImageView) findViewById(R.id.api_call);
            mApiButton.setClickable(true);
            mApiButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (!mOriginEditText.getText().toString().equals("") || mOriginEditText.getText() == null) {
                        String temp =  mOriginEditText.getText().toString().toUpperCase();
                        mOriginAirportCode = temp + "-sky";
                        mOriginEditText.setSelected(false);
//                        getWindow().setSoftInputMode(
//                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
//                        );
                    } else {
                        destinationRandomizer();
                        MasterListSingleton.getInstance().clearBookingObjects();
                        // starting the session connection, which will be called multiple times
                        SessionCreator sessionCreator = new SessionCreator();
                        sessionCreator.execute();
                    }
                    return true;
                }
            });
        } else {

        }

        // flight options activity swipe bar
        mAgentSeekBar = (SeekBar) findViewById(R.id.flight_agent_swipe_bar);

        mAgentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mAgentSeekBarPosition = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mAgentSeekBarPosition > 95) {
                    Intent intent = new Intent(mContext, AgentsObjRVActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        mMorePhotos = (Button) findViewById(R.id.more_photos);

        mMorePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PhotoGalleryRVActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    // *********************************************************************

    // Below: step 1 of Skyscanner

    // *********************************************************************

    public class SessionCreator extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            Log.i(TAG, "onResponse: jnum1 ");
            String request = "http://partners.api.skyscanner.net/apiservices/pricing/v1.0/";
            URL url = null;
            try {
                url = new URL(request);
                Log.i(TAG, "doInBackground: url requested: " + url);
                Log.i(TAG, "onCreate: connection open");
                assert url != null;
                conn = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert conn != null;
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);

            Log.i(TAG, "onCreate: Post requested");
            try {
                conn.setRequestMethod("POST");
                Log.i(TAG, "doInBackground: Posted");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("charset", "utf-8");

            String urlParameters = mApiKey +
                    "&country=" + mMarket +
                    "&currency=" + mCurrency +
                    "&locale=" + mLocale +
                    "&originplace=" + mOriginAirportCode +
                    "&destinationplace=" + mDestAirportCode +
                    "&outbounddate=" + mOutboundDate +
                    "&inbounddate=" + mInboundDate +
                    "&locationschema=" + "Iata" +
                    "&cabinclass=" + mPreCabinClass +
                    "&adults=" + mAdults;

            DataOutputStream outputStream = null;
            try {
                Log.i(TAG, "doInBackground: Output stream");
                outputStream = new DataOutputStream(conn.getOutputStream());
                outputStream.writeBytes(urlParameters);
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mSessionID = conn.getHeaderField("Location");

            return mSessionID;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "onPostExecute: The session ID: " + s);

            sessionPolling();
        }
    }

    // *********************************************************************

    // Below: step 2 of Skyscanner

    // *********************************************************************


    public void sessionPolling() {
        Log.i(TAG, "onResponse: jnum2 ");
        Log.i(TAG, "doInBackground: inside doInBackground with key: " + mSessionID + "?" + mApiKey);
        Log.i(TAG, "doInBackground: check key above follows this key: http://partners.api.skyscanner.net/apiservices/pricing/v1.0/{sessionKey}?apiKey={apiKey}");
        final RequestQueue queue = Volley.newRequestQueue(mContext);
        if(mSessionID == null){
            destinationRandomizer();
            SessionCreator sessionCreator = new SessionCreator();
            sessionCreator.execute();
            return;
        }

        JsonObjectRequest objectReq = new JsonObjectRequest(Request.Method.GET, mSessionID + "?" + mApiKey, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

//                Log.i(TAG, "onResponse: step 2 response 1: " + response);
                Gson gson = new Gson();
                ItineraryClassGson itinerary = gson.fromJson(response.toString(), ItineraryClassGson.class);

                if (itinerary.getItineraries().size() < 1) {
                    Log.i(TAG, "onResponse: ");
                    queue.cancelAll(this);
                } else {
                    mOutboundLegId = itinerary.getItineraries().get(0).getOutboundLegId();
                    mInboundLegId = itinerary.getItineraries().get(0).getInboundLegId();
                }

                if (itinerary.getItineraries().isEmpty()) {
                    destinationRandomizer();
                    SessionCreator sessionCreator = new SessionCreator();
                    sessionCreator.execute();
                } else {
                    mUrlBooking = itinerary.getItineraries().get(0).getBookingDetailsLink().getUri();
                    BookingSessionCreator bSC = new BookingSessionCreator();
                    bSC.execute();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: api call stopped working again first poll");
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
        queue.add(objectReq);
    }

    // *********************************************************************

    // Below: step 3 of Skyscanner

    // *********************************************************************

    public class BookingSessionCreator extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            Log.i(TAG, "onResponse: jnum3 ");
            if (mUrlBooking != null) {
//                URL url = null;
                String request = "http://partners.api.skyscanner.net" + mUrlBooking + "?" + mApiKey;
                try {
                    URL url = new URL(request);
                    Log.i(TAG, "doInBackground: url requested: " + url);
                    Log.i(TAG, "onCreate: connection open");
                    assert url != null;
                    connection2 = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert conn != null;
                connection2.setDoOutput(true);
                connection2.setInstanceFollowRedirects(false);

                Log.i(TAG, "onCreate: Post requested");
                try {
                    connection2.setRequestMethod("PUT");
                    Log.i(TAG, "doInBackground: Posted");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "doInBackground: " + mOutboundLegId);
                Log.i(TAG, "doInBackground: " + mInboundLegId);

                String urlParameters = mApiKey +
                        "&outboundlegid=" + mOutboundLegId +
                        "&inboundlegid=" + mInboundLegId;

                DataOutputStream outputStream = null;
                try {
                    Log.i(TAG, "doInBackground: Output stream");
                    outputStream = new DataOutputStream(connection2.getOutputStream());
                    outputStream.writeBytes(urlParameters);
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mBookingSession = conn.getHeaderField("Location");
                Log.i(TAG, "doInBackground: Booking Session ID = " + mBookingSession);
            }
            return mBookingSession;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (mBookingSession == null && mNoFlightCount < 5) {
                destinationRandomizer();
                MasterListSingleton.getInstance().clearBookingObjects();
                SessionCreator sessionCreator = new SessionCreator();
                sessionCreator.execute();
                Toast.makeText(mContext,"There are no flights for your filters",Toast.LENGTH_SHORT).show();
                mNoFlightCount = mNoFlightCount + 1;
                cancel(true);
            } else {
                Log.i(TAG, "onPostExecute: The session ID: " + s);
                bookingPollingSession();
                if (connection2 != null) {
                    connection2.disconnect();
                }
            }
        }
    }

    // *********************************************************************

    // Below: Skyscanner step 4

    // *********************************************************************

    public void bookingPollingSession() {

        final RequestQueue queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest objectReq = new JsonObjectRequest(Request.Method.GET, mBookingSession + "?" + mApiKey, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG, "onResponse: " + response);
                Gson gson = new Gson();
                ItineraryClassGson itinerary;
                itinerary = gson.fromJson(response.toString(), ItineraryClassGson.class);
                AgentItemsObjGson agentObjClass = gson.fromJson(response.toString(), AgentItemsObjGson.class);

                if (itinerary.getItineraries().isEmpty()) {

                } else {

                    mDestinationLocation = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(0);
                    mDestinationCountry = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(1);

                    for (int j = 0; j < itinerary.getItineraries().get(0).getPricingOptions().size(); j++) {
                        Log.i(TAG, "onResponse: jnum4 " + j);
                        double num = Double.parseDouble(itinerary.getItineraries().get(0).getPricingOptions().get(0).getPrice());
                        if (num > mMaxPrice) {
                            if (mNoFlightCount < (itinerary.getItineraries().get(0).getPricingOptions().size()) && mNoFlightCount < 5) {
                                destinationRandomizer();
                                MasterListSingleton.getInstance().clearBookingObjects();
                                SessionCreator sessionCreator = new SessionCreator();
                                sessionCreator.execute();
                                mNoFlightCount = mNoFlightCount + 1;
                                Log.i(TAG, "onResponse: mNoFlightCount " + mNoFlightCount);
                            } else {
                                Toast.makeText(MainEventPageActivity.this, " Sorry there are no flights for those filters", Toast.LENGTH_LONG).show();
                                mNoFlightCount = 0;
                                Log.i(TAG, "onResponse: mNoFlightCount " + mNoFlightCount);
                                Log.i(TAG, "onResponse: quitting flight search");
                                queue.cancelAll(this);
                            }
                        } else {
                            if (j < itinerary.getItineraries().size()) {
                                mUrlBooking = itinerary.getItineraries().get(j).getBookingDetailsLink().getUri();
                                String price = itinerary.getItineraries().get(j).getPricingOptions().get(0).getPrice();
                                String bookingurl = itinerary.getItineraries().get(j).getPricingOptions().get(0).getDeeplinkUrl();
                                String agentName = agentObjClass.getAgents().get(j).getName();
                                String agentpic = agentObjClass.getAgents().get(j).getImageUrl();

                                Log.i(TAG, "onResponse: flight price: " + price + " " + mCurrency);
                                Log.i(TAG, "onResponse: flight bookingurl: " + bookingurl);
                                Log.i(TAG, "onResponse: flight urlBooking: " + mUrlBooking);
                                Log.i(TAG, "onResponse: agentName: " + agentName);
                                Log.i(TAG, "onResponse: agentpic: " + agentpic);

                                try {
                                    TextView outbound = (TextView) findViewById(R.id.outbound);
                                    TextView inbound = (TextView) findViewById(R.id.inbound);
                                    TextView origin = (TextView) findViewById(R.id.origin);
                                    TextView destination = (TextView) findViewById(R.id.destination);
                                    TextView region = (TextView) findViewById(R.id.region_cardview);
                                    TextView priceCV = (TextView) findViewById(R.id.price_cardview);
                                    TextView country = (TextView) findViewById(R.id.country);
                                    TextView country2 = (TextView) findViewById(R.id.country_2);

                                    if (!itinerary.getItineraries().get(j).getPricingOptions().equals(0)) {
                                        String price2 = itinerary.getItineraries().get(0).getPricingOptions().get(0).getPrice();

                                        JSONObject mQuery = response.getJSONObject("Query");
                                        mOutboundDate = mQuery.optString("OutboundDate");
                                        mInboundDate = mQuery.getString("InboundDate");
                                        mCabinClass = mQuery.getString("CabinClass");

                                        DBHelper.getInstance(MainEventPageActivity.this).removeCardVItems();
                                        DBHelper.getInstance(MainEventPageActivity.this).insertCardVItems(price2, mOutboundDate, mInboundDate, mDestinationCountry);

                                        outbound.setText(mInboundDate);
                                        inbound.setText(mOutboundDate);
                                        country.setText(mDestinationLocation + "/" + mDestinationCountry);
                                        country2.setText(mDestinationLocation);
                                        origin.setText(mOriginAirportCode);
                                        destination.setText(mDestAirportCode);
                                        region.setText(mRegion);
                                        priceCV.setText("From $" + price2);
                                        Log.i(TAG, "onResponse: flight outbound:" + mOutboundDate + ", Return: " + mInboundDate);
                                        Log.i(TAG, "onResponse: flight origin:" + mDestinationCountry + ", Cabin: " + mCabinClass);
                                        WikipediaInformation();
                                        flickrImages(mDestAirportCode, mRegion, mDestinationCountry);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                String originAirporstname, destinedAirportName;
                                originAirporstname = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mOriginAirportCode).get(0);
                                destinedAirportName = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(0);

                                String oCountry, dCountry;
                                oCountry = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mOriginAirportCode).get(1);
                                dCountry = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(1);

                                String origincity, destinedCity;
                                if (DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mOriginAirportCode).size() == 3) {
                                    // city column if exists
                                    origincity = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mOriginAirportCode).get(2);
                                } else {
                                    // airport name is that of the city
                                    origincity = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mOriginAirportCode).get(0);
                                }

                                if (DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).size() == 3) {
                                    // city column if exists
                                    destinedCity = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(2);
                                } else {
                                    // airport name is that of the city
                                    destinedCity = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(0);
                                }

//                              Adding origin city

                                TextView oCH = (TextView) findViewById(R.id.origin_city_home);
                                TextView dCH = (TextView) findViewById(R.id.destined_city_home);

                                oCH.setText(origincity);
                                dCH.setText(destinedCity);

                                BookingObj bookingObj = new BookingObj(
                                        mDestAirportCode, mOriginAirportCode, price, mOutboundDate,
                                        mInboundDate, mDestinationCountry, bookingurl, agentName,
                                        agentpic, origincity, destinedCity, originAirporstname,
                                        destinedAirportName, oCountry, dCountry);
                                MasterListSingleton.getInstance().addBookingObjToList(bookingObj);
                            }
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: api call stopped working again second poll");
                bookingPollingSession();
                mErrorCount = mErrorCount + 1;
                Log.i(TAG, "doInBackground: location booking post execute checkpoint " + mDestinationLocation);

            }
        });
        queue.add(objectReq);
    }


    // *********************************************************************

    // Below: setting the new destination

    // *********************************************************************

    public void destinationRandomizer() {
        mDestAirportCode = null;
        if (mRegion.contains("South") && mRegion.contains("America")) {
            Log.i(TAG, "onClick: " + mRegion);
            String dest = DBHelper.getInstance(MainEventPageActivity.this).getRandSAmericaAirportCode();
            Log.i(TAG, "onClick: destination " + dest);
            mDestAirportCode = dest + "-sky";
        } else if (mRegion.contains("South") && mRegion.contains("Asia")) {
            Log.i(TAG, "onClick: " + mRegion);
            String dest = DBHelper.getInstance(MainEventPageActivity.this).getRandSAsiaAirportCode();
            Log.i(TAG, "onClick: destination " + dest);
            mDestAirportCode = dest + "-sky";
        } else if (mRegion.contains("North") && mRegion.contains("America")) {
            Log.i(TAG, "onClick: " + mRegion);
            String dest = DBHelper.getInstance(MainEventPageActivity.this).getRandNAmericaAirportCode();
            Log.i(TAG, "onClick: destination " + dest);
            mDestAirportCode = dest + "-sky";
        } else if (mRegion.contains("Europe")) {
            Log.i(TAG, "onClick: " + mRegion);
            String dest = DBHelper.getInstance(MainEventPageActivity.this).getRandEUAirportCode();
            Log.i(TAG, "onClick: destination " + dest);
            mDestAirportCode = dest + "-sky";
        } else if (mRegion.contains("Anywhere") || mRegion.isEmpty()) {
            String dest = DBHelper.getInstance(MainEventPageActivity.this).getRandomAirportCode();
            mDestAirportCode = dest + "-sky";
        } else {
            String dest = DBHelper.getInstance(MainEventPageActivity.this).getRandomAirportCode();
            mDestAirportCode = dest + "-sky";
        }
    }

    // *********************************************************************

    // Below: retrieving json objects from wikipedia

    // *********************************************************************

    public void WikipediaInformation() {
//        public ArrayList<String> array = new ArrayList<>();
        mWikiExtract = "";

        String city = "";

        if (DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).size() == 3) {
            // city column if exists
            city = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(2);
        } else {
            // airport name is that of the city
            city = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(mDestAirportCode).get(0);
        }


        String encodedCity = null;
        try {
            encodedCity = URLEncoder.encode(city, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(MainEventPageActivity.this);
        Log.i(TAG, "doInBackground: encoded city: " + encodedCity);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://en.wikipedia.org/w/api.php?action=query&format=json&meta=&continue=&prop=extracts&exintro=&explaintext=&titles=" +
                        encodedCity, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();

                try {
                    innerKey = null;
                    JSONObject query = response.getJSONObject("query");
                    JSONObject pages = query.getJSONObject("pages");

                    Type mapType = new TypeToken<Map<String, Map<String, String>>>() {
                    }.getType();
                    Map<String, Map<String, String>> map = gson.fromJson(pages.toString(), mapType);
//                    Log.i(TAG, "onResponse: my map " + map);

                    for (String key : map.keySet()) {
//                        Log.i(TAG, "onResponse: for loop key " + key);
                        innerKey = key;
                    }

                    JSONObject numberKeyObj = pages.getJSONObject(innerKey);
                    mWikiExtract = numberKeyObj.getString("extract");

                    String lastValue = "";
                    String currentValue = "";
                    int lastNum = 0;
                    mWikiParCount = 1;
                    for (int i = 0; i < mWikiExtract.length() - 3; i++) {
                        if (mWikiExtract.length() > 3 && mWikiParCount < 3) {
                            if (i == 0) {
                                lastValue = String.valueOf(mWikiExtract.charAt(i));
                            } else {
//                                Log.i(TAG, "onResponse: wiki else");
                                ;
                                currentValue = String.valueOf(mWikiExtract.charAt(i));
                                if (lastValue.equals(".") && (currentValue.equals(" "))) {
                                    TextView city_blurb1 = (TextView) findViewById(R.id.city_blurb1);
                                    TextView city_blurb2 = (TextView) findViewById(R.id.city_blurb2);
                                    if (mWikiParCount == 1) {
//                                        Log.i(TAG, "onResponse: wiki info " + i);
                                        city_blurb1.setText(mWikiExtract.substring(0, i - 2));
                                        lastNum = i;
                                    } else {
//                                        Log.i(TAG, "onResponse: wiki " + lastNum);
                                        city_blurb2.setText(mWikiExtract.substring(lastNum, i - 2));
                                    }
                                    mWikiParCount = mWikiParCount + 1;
                                }
                            }
                        }
//                        Log.i(TAG, "onResponse: last value:  " + lastValue + currentValue);
                        lastValue = currentValue;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    public void flickrImages(String destination, String region, String country) {

        Log.i(TAG, "flickrImages: I'm in");

        String city = "";

        if (DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(destination).size() == 3) {
            // city column if exists
            city = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(destination).get(2);
        } else {
            // airport name is that of the city
            city = DBHelper.getInstance(MainEventPageActivity.this).getCityAndCountry(destination).get(0);
        }

        String encodedCity = city.replaceAll(" ", "_");
        Log.i(TAG, "flickrImages: " + "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                ApiClass.flickrApi + "&tags=" + country + "&text=" + encodedCity + "&format=json");


        RequestQueue requestQueue = Volley.newRequestQueue(MainEventPageActivity.this);
        JsonObjectRequest job = new JsonObjectRequest(Request.Method.GET,
                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                        ApiClass.flickrApi + "&tags=" + country + "&text=" + encodedCity + "&format=json&nojsoncallback=1",
//                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=9cb129c9deed6c950fbd3e3e54bb462b&text=Trondheim&format=json&nojsoncallback=1",
//                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=89a4669c0553d3d254b50803bff8f692&tags=landmark&text=London&format=json&nojsoncallback=1"

                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onFlickrResponse response: " + response);
                        try {
                            JSONObject container = response.getJSONObject("photos");
                            JSONArray photos = container.getJSONArray("photo");
                            Log.i(TAG, "onFlickrResponse size: " + photos.length());


                            for (int i = 1; i < 4; i++) {
//                               needed for photo formatting: id; secret; server; farm
//                               https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
                                JSONObject items = photos.getJSONObject(i);
                                String id = items.getString("id");
                                String secret = items.getString("secret");
                                int farm = items.getInt("farm");
                                String server = items.getString("server");

//                                Log.i(TAG, "onResponse: id " + i + ": " + id);
//                                Log.i(TAG, "onResponse: secret " + i + ": " + secret);
//                                Log.i(TAG, "onResponse: farm " + i + ": " + farm);
//                                Log.i(TAG, "onResponse: server " + i + ": " + server);

                                ImageView city_image_two = (ImageView) findViewById(R.id.city_img_2);
                                ImageView city_image_three = (ImageView) findViewById(R.id.city_img_3);

                                switch (i) {
                                    case 1:
                                        Picasso.with(MainEventPageActivity.this)
                                                .load("http://farm" +
                                                        farm +
                                                        ".staticflickr.com/" +
                                                        server + "/" +
                                                        id + "_" +
                                                        secret + ".jpg")
                                                .resize(mCity_image_one.getWidth(), mCity_image_one.getHeight())
                                                .centerCrop()
                                                .into(mCity_image_one, new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        Log.i(TAG, "onSuccess: success 1");
                                                    }

                                                    @Override
                                                    public void onError() {

                                                    }
                                                });
                                        Log.i(TAG, "onResponse: " + "http://farm" +
                                                farm +
                                                ".staticflickr.com/" +
                                                server + "/" +
                                                id + "_" +
                                                secret + ".jpg");
                                        break;
                                    case 2:
                                        Picasso.with(MainEventPageActivity.this)
                                                .load("https://farm" +
                                                        farm +
                                                        ".staticflickr.com/" +
                                                        server + "/" +
                                                        id + "_" +
                                                        secret + ".jpg")
                                                .resize(city_image_two.getWidth(), city_image_two.getMeasuredHeight())
                                                .centerCrop()
                                                .into(city_image_two, new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        Log.i(TAG, "onSuccess: success 2");
                                                    }

                                                    @Override
                                                    public void onError() {

                                                    }
                                                });

                                        Log.i(TAG, "onResponse: " + "http://farm" +
                                                farm +
                                                ".staticflickr.com/" +
                                                server + "/" +
                                                id + "_" +
                                                secret + ".jpg");
                                        break;
                                    case 3:
                                        Picasso.with(MainEventPageActivity.this)
                                                .load("https://farm" +
                                                        farm +
                                                        ".staticflickr.com/" +
                                                        server + "/" +
                                                        id + "_" +
                                                        secret + ".jpg")
                                                .resize(city_image_three.getWidth(), city_image_three.getMeasuredHeight())
                                                .centerCrop()
                                                .into(city_image_three, new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        Log.i(TAG, "onSuccess: success 3");
                                                    }

                                                    @Override
                                                    public void onError() {

                                                    }
                                                });
                                        Log.i(TAG, "onResponse: " + "https://farm" +
                                                farm +
                                                ".staticflickr.com/" +
                                                server + "/" +
                                                id + "_" +
                                                secret + ".jpg");
                                        break;
                                }
                            }

                            // populating the Image Gallery Activity
                            for (int j = 0; j < photos.length() && j < 100; j++) {
                                JSONObject items = photos.getJSONObject(j);
                                String id = items.getString("id");
                                String secret = items.getString("secret");
                                int farm = items.getInt("farm");
                                String server = items.getString("server");
                                String title = items.getString("title");
                                MasterListSingleton.getInstance().clearGalleryPhotos();
                                ImageGalleryObj iGO = new ImageGalleryObj(id, secret, server, title, farm);
                                MasterListSingleton.getInstance().addGalleryObjs(iGO);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: ");
                error.printStackTrace();
            }
        });
        requestQueue.add(job);

        JsonObjectRequest job2 = new JsonObjectRequest(Request.Method.GET,
                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=" +
                        ApiClass.flickrApi + "&text=" + region +  "&tags=landmark&format=json&nojsoncallback=1",
//                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=9cb129c9deed6c950fbd3e3e54bb462b&text=Trondheim&format=json&nojsoncallback=1",
//                "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=89a4669c0553d3d254b50803bff8f692&tags=landmark&text=London&format=json&nojsoncallback=1"

                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onFlickrResponse response: " + response);
                        try {
                            JSONObject container = response.getJSONObject("photos");
                            JSONArray photos = container.getJSONArray("photo");
                            Log.i(TAG, "onFlickrResponse size: " + photos.length());

//                               needed for photo formatting: id; secret; server; farm
//                               https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg
                            JSONObject items = photos.getJSONObject(0);
                            String id = items.getString("id");
                            String secret = items.getString("secret");
                            int farm = items.getInt("farm");
                            String server = items.getString("server");

                            ImageView city_photo = (ImageView) findViewById(R.id.region_photo);

                            Picasso.with(MainEventPageActivity.this)
                                    .load("http://farm" +
                                            farm +
                                            ".staticflickr.com/" +
                                            server + "/" +
                                            id + "_" +
                                            secret + ".jpg")
                                    .into(city_photo, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Log.i(TAG, "onSuccess: success 1");
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
//                            Log.i(TAG, "onResponse: " + "https://farm" +
//                                    farm +
//                                    ".staticflickr.com/" +
//                                    server + "/" +
//                                    id + "_" +
//                                    secret + ".jpg");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.i(TAG, "onErrorResponse: ");
                error.printStackTrace();
            }
        });
        requestQueue.add(job2);
    }


    public void flickrWoeId(){
//
//        make this call to get the woe id
//        https://api.flickr.com/services/rest/?method=flickr.places.find
        // &api_key=ba22e63bdc81558e733c75a1e30e168a
        // &query=London&format=json&nojsoncallback=1
        // &auth_token=72157672686455202-b7f9031c67576e73&api_sig=33528d25b45a2e6b2800924915201bed
//                then
//                        redo other flicker api to add woe id parameter and bug will be fixed
//        flickrImages();


    }

    @Override
    protected void onPause() {
        mAgentSeekBar.setProgress(0);
        if (conn != null) {
            conn.disconnect();
        }
        if (connection2 != null) {
            connection2.disconnect();
        }

        super.onPause();
    }


    // *********************************************************************

    // Below: Setting Bottom Bar

    // *********************************************************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.information_page:
                return true;
            case R.id.booking_page:
                Intent intent = new Intent(mContext, AgentsObjRVActivity.class);
                mContext.startActivity(intent);
                return true;
            case R.id.more_photos_option:
                Intent intent3 = new Intent(mContext, PhotoGalleryRVActivity.class);
                mContext.startActivity(intent3);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}