package com.example.ander.travelapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ander on 9/1/2016.
 */
public class ThirdPage extends AppCompatActivity {


    ImageButton mApiButton;
    Button mToFlightOptions;
    Context mContext;
    HttpURLConnection conn, connection2;
    String mLocale, mOriginPlace, mOutboundDate, mInboundDate, mDestinationPlace, mAdults, mMarket, mLocationName;
    String mCurrency, mRegion, mUrlBooking, mCabinClass, mSessionID, mBookingSession, mDestinationCountry;
    int mErrorCount, mNoFlightCount;
    double mMaxPrice;

    String mApiKey = ApiClass.api;
    private static final String TAG = "ThirdPage.this";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_screen);
        mContext = ThirdPage.this;
        mMaxPrice = 0;

        InitialValuesObj initialValuesObj = InitialValuesSingleton.getInstance().getmInitialValuesObj();
        mErrorCount = 0;
        mNoFlightCount = 0;
        mMarket = "GB";
        mCurrency = "GBP";
        mLocale = "en-GB";
        mOriginPlace = initialValuesObj.getOutboundAirport();
        mAdults = "1";
        mOutboundDate = initialValuesObj.getOutboundDate();
        mInboundDate = initialValuesObj.getInboundDate();
        mRegion = initialValuesObj.getRegion();
        mMaxPrice = initialValuesObj.getPrice();
        conn = null;

        String newS = mRegion;

        mApiButton = (ImageButton) findViewById(R.id.api_call);

        mApiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // retrieving the destination randomizer
                destinationRandomizer();

                InitialValuesSingleton.getInstance().clearBookingObjects();

                // starting the session connection, which will be called multiple times
                SessionCreator sessionCreator = new SessionCreator();
                sessionCreator.execute();
            }
        });

        mToFlightOptions = (Button) findViewById(R.id.toFlightOptions);
        mToFlightOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdPage.this, FourthScreen.class);
                startActivity(intent);
            }
        });
    }


    public class SessionCreator extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

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
                    "&originplace=" + mOriginPlace +
                    "&destinationplace=" + mDestinationPlace +
                    "&outbounddate=" + mOutboundDate +
                    "&inbounddate=" + mInboundDate +
                    "&locationschema=" + "Iata" +
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
            PollingSession pollingSession = new PollingSession();
            pollingSession.execute();
        }
    }


    public class PollingSession extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(final Void... integers) {
            Log.i(TAG, "doInBackground: inside doInBackground with key: " + mSessionID + "?" + mApiKey);
            Log.i(TAG, "doInBackground: check key above follows this key: http://partners.api.skyscanner.net/apiservices/pricing/v1.0/{sessionKey}?apiKey={apiKey}");
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest objectReq = new JsonObjectRequest(Request.Method.GET, mSessionID + "?" + mApiKey, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    Log.i(TAG, "onResponse: " + response);
                    Gson gson = new Gson();
                    ItineraryClassGson itinerary;
                    itinerary = gson.fromJson(response.toString(), ItineraryClassGson.class);

                    if (itinerary.getItineraries().isEmpty()) {
                        destinationRandomizer();
                        SessionCreator sessionCreator = new SessionCreator();
                        sessionCreator.execute();

                    } else {
                        mUrlBooking = itinerary.getItineraries().get(0).getBookingDetailsLink().getUri();
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "onPostExecute:");
            BookingSessionCreator bookingSession = new BookingSessionCreator();
            bookingSession.execute();
        }
    }


    public class BookingSessionCreator extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {

            if (mUrlBooking != null) {

                String request = "http://partners.api.skyscanner.net" + mUrlBooking + "?" + mApiKey;
//  http://partners.api.skyscanner.net/apiservices/pricing/v1.0/{session key}/booking?apiKey={apiKey}
                URL url = null;
                try {
                    url = new URL(request);
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

                String urlParameters = mApiKey +
                        "&outboundlegid=" + mOutboundDate +
                        "&inboundlegid=" + mInboundDate;

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
            Log.i(TAG, "onPostExecute: The session ID: " + s);
            BookingPollingSession bookingPolling = new BookingPollingSession();
            bookingPolling.execute();
            if (connection2 != null) {
                connection2.disconnect();
            }
        }
    }

    public class BookingPollingSession extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(final Void... integers) {

            RequestQueue queue = Volley.newRequestQueue(mContext);
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


                        mLocationName = DBHelper.getInstance(ThirdPage.this).getCityAndCountry(mDestinationPlace).get(0);
                        mDestinationCountry = DBHelper.getInstance(ThirdPage.this).getCityAndCountry(mDestinationPlace).get(1);

                        wikipediaInformation();
                        for (int j = 0; j < itinerary.getItineraries().get(0).getPricingOptions().size(); j++) {
                            double num = Double.parseDouble(itinerary.getItineraries().get(0).getPricingOptions().get(0).getPrice());
                            if (num > mMaxPrice && mMaxPrice != 0) {
                                if (mNoFlightCount < 10) {
                                    destinationRandomizer();
                                    SessionCreator sessionCreator = new SessionCreator();
                                    sessionCreator.execute();
                                    Log.i(TAG, "onResponse: noFlightCount = " + mNoFlightCount);
                                    mNoFlightCount = mNoFlightCount + 1;
                                } else {
                                    cancel(true);
                                    Toast.makeText(ThirdPage.this, " There are no flights for your price", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                mUrlBooking = itinerary.getItineraries().get(j).getBookingDetailsLink().getUri();
                                String price = itinerary.getItineraries().get(j).getPricingOptions().get(0).getPrice();
                                String url = itinerary.getItineraries().get(j).getPricingOptions().get(0).getDeeplinkUrl();
                                String agentName = agentObjClass.getAgents().get(j).getName();
                                String agentpic = agentObjClass.getAgents().get(j).getImageUrl();

                                Log.i(TAG, "onResponse: flight price = " + price + " " + mCurrency);
                                Log.i(TAG, "onResponse: flight url = " + url);
                                Log.i(TAG, "onResponse: " + mUrlBooking);
                                Log.i(TAG, "onResponse: flight urlBooking = " + mUrlBooking);
                                Log.i(TAG, "onResponse: agentName " + agentName);
                                Log.i(TAG, "onResponse: agentpic " + agentpic);

                                try {
                                    TextView outbound = (TextView) findViewById(R.id.outbound);
                                    TextView inbound = (TextView) findViewById(R.id.inbound);
                                    TextView destinations = (TextView) findViewById(R.id.destinations);
                                    TextView region = (TextView) findViewById(R.id.region_cardview);
                                    TextView priceCV = (TextView) findViewById(R.id.price_cardview);
                                    TextView country = (TextView) findViewById(R.id.country);
                                    if (!itinerary.getItineraries().equals(0)) {
                                        String price2 = itinerary.getItineraries().get(0).getPricingOptions().get(0).getPrice();

                                        JSONObject mQuery = response.getJSONObject("Query");
                                        mOutboundDate = mQuery.optString("OutboundDate");
                                        mInboundDate = mQuery.getString("InboundDate");
                                        mCabinClass = mQuery.getString("CabinClass");

                                        DBHelper.getInstance(ThirdPage.this).removeCardVItems();
                                        DBHelper.getInstance(ThirdPage.this).insertCardVItems(price2, mOutboundDate, mInboundDate, mDestinationCountry);

                                        ArrayList<String> array = DBHelper.getInstance(ThirdPage.this).getAllCardVItems();
                                        outbound.setText(mInboundDate);
                                        inbound.setText(mOutboundDate);
                                        country.setText(mLocationName + " - " + mDestinationCountry);
                                        destinations.setText(mOriginPlace + " --> " + mDestinationPlace);
                                        region.setText(mRegion);
                                        priceCV.setText("From $" + price2);
                                        Log.i(TAG, "onResponse: flight outbound:" + mOutboundDate + ", Return: " + mInboundDate);
                                        Log.i(TAG, "onResponse: flight origin:" + mDestinationCountry + ", Cabin: " + mCabinClass);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                BookingObj bookingObj = new BookingObj(
                                        mDestinationPlace, mOriginPlace, price, mOutboundDate,
                                        mInboundDate, mDestinationCountry, url);
                                InitialValuesSingleton.getInstance().addBookingObjToList(bookingObj);
                            }
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "onErrorResponse: api call stopped working again second poll");
                    if (mErrorCount >= 5) {
                        mErrorCount = 0;
                    } else {
                        BookingPollingSession bookingPollingSession = new BookingPollingSession();
                        bookingPollingSession.execute();
                        mErrorCount = mErrorCount + 1;
                    }
                }
            });
            queue.add(objectReq);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "onPostExecute:");
            mNoFlightCount = 0;
        }
    }

    public void wikipediaInformation() {

        RequestQueue requestQueue = Volley.newRequestQueue(ThirdPage.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=images%7Cpageimages%7Ccoordinates%7Cvideoinfo&meta=&continue=&titles=" + mDestinationCountry
                ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Log.i(TAG, "onResponse: wikipedia response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }


    public void destinationRandomizer() {
        mDestinationPlace = null;
        if (mRegion.contains("South") && mRegion.contains("America")) {
            Log.i(TAG, "onClick: " + mRegion);
            String origin = DBHelper.getInstance(ThirdPage.this).getRandSAmericaAirportCode();
            Log.i(TAG, "onClick: destination " + origin);
            mDestinationPlace = origin;
        } else if (mRegion.contains("South") && mRegion.contains("Asia")) {
            Log.i(TAG, "onClick: " + mRegion);
            String origin = DBHelper.getInstance(ThirdPage.this).getRandSAsiaAirportCode();
            Log.i(TAG, "onClick: destination " + origin);
            mDestinationPlace = origin;
        } else if (mRegion.contains("North") && mRegion.contains("America")) {
            Log.i(TAG, "onClick: " + mRegion);
            String origin = DBHelper.getInstance(ThirdPage.this).getRandNAmericaAirportCode();
            Log.i(TAG, "onClick: destination " + origin);
            mDestinationPlace = origin;
        } else if (mRegion.contains("Europe")) {
            Log.i(TAG, "onClick: " + mRegion);
            String origin = DBHelper.getInstance(ThirdPage.this).getRandEUAirportCode();
            Log.i(TAG, "onClick: destination " + origin);
            mDestinationPlace = origin;
        }
    }
}
