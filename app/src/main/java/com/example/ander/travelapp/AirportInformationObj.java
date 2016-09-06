package com.example.ander.travelapp;

/**
 * Created by ander on 8/31/2016.
 */
public class AirportInformationObj {

    public String mAirportName;
    public String mAirportIata;
    public String mAirportRegion;
    public String mAirportCountry;
    public String mAirport_ID;
    public String mCity;

    public AirportInformationObj(String airport_ID, String airportName, String airportIata, String airportRegion, String airportCountry) {
        mAirportName = airportName;
        mAirportIata = airportIata;
        mAirportRegion = airportRegion;
        mAirportCountry = airportCountry;
        mAirport_ID = airport_ID;
    }

    public AirportInformationObj(String airport_ID, String airportName, String airportIata, String airportRegion, String airportCountry, String airport_City) {
        mAirportName = airportName;
        mAirportIata = airportIata;
        mAirportRegion = airportRegion;
        mAirportCountry = airportCountry;
        mAirport_ID = airport_ID;
        mCity = airport_City;
    }

    public String getmAirportName() {
        return mAirportName;
    }

    public void setmAirportName(String mAirportName) {
        this.mAirportName = mAirportName;
    }

    public String getmAirportIata() {
        return mAirportIata;
    }

    public void setmAirportIata(String mAirportIata) {
        this.mAirportIata = mAirportIata;
    }

    public String getmAirportRegion() {
        return mAirportRegion;
    }

    public void setmAirportRegion(String mAirportRegion) {
        this.mAirportRegion = mAirportRegion;
    }

    public String getmAirportCountry() {
        return mAirportCountry;
    }

    public void setmAirportCountry(String mAirportCountry) {
        this.mAirportCountry = mAirportCountry;
    }

    public String getmAirport_ID() {
        return mAirport_ID;
    }

    public void setmAirport_ID(String mAirport_ID) {
        this.mAirport_ID = mAirport_ID;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }
}
