package com.example.ander.travelapp;

/**
 * Created by ander on 9/1/2016.
 */
public class TravelInfoObj {

    String mDestination;
    String mOrigin;

    public TravelInfoObj(String mDestination, String mOrigin) {
        this.mDestination = mDestination;
        this.mOrigin = mOrigin;
    }


    public String getmDestination() {
        return mDestination;
    }

    public void setmDestination(String mDestination) {
        this.mDestination = mDestination;
    }

    public String getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(String mOrigin) {
        this.mOrigin = mOrigin;
    }
}
