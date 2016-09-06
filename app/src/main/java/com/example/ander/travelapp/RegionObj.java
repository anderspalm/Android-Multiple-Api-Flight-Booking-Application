package com.example.ander.travelapp;

/**
 * Created by ander on 9/1/2016.
 */
public class RegionObj {

    String mName;
    String mURL;

    public RegionObj(String mName, String mURL) {
        this.mName = mName;
        this.mURL = mURL;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
