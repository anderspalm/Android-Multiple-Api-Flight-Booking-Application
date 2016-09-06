package com.example.ander.travelapp;

/**
 * Created by ander on 9/2/2016.
 */
public class BookingObj {

    String mDestinationAirport;
    String mOriginAirport;
    String mPrice;
    String mOutboundDate;
    String mInboundDate;
    String mCountry;
    String mDeepLinkUrl;
    String mAgent;

    public BookingObj(String destination, String origin, String price, String outboundDate, String inboundDate, String country, String deepLinkUrl) {
        mDestinationAirport = destination;
        mOriginAirport = origin;
        mPrice = price;
        mOutboundDate = outboundDate;
        mInboundDate = inboundDate;
        mCountry = country;
        mDeepLinkUrl = deepLinkUrl;
    }

    public BookingObj(){}

    public String getmAgent() {
        return mAgent;
    }

    public void setmAgent(String mAgent) {
        this.mAgent = mAgent;
    }

    public String getmDeepLinkUrl() {
        return mDeepLinkUrl;
    }

    public void setmDeepLinkUrl(String mDeepLinkUrl) {
        this.mDeepLinkUrl = mDeepLinkUrl;
    }

    public String getmDestinationAirport() {
        return mDestinationAirport;
    }

    public void setmDestinationAirport(String mDestinationAirport) {
        this.mDestinationAirport = mDestinationAirport;
    }

    public String getmOriginAirport() {
        return mOriginAirport;
    }

    public void setmOriginAirport(String mOriginAirport) {
        this.mOriginAirport = mOriginAirport;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmOutboundDate() {
        return mOutboundDate;
    }

    public void setmOutboundDate(String mOutboundDate) {
        this.mOutboundDate = mOutboundDate;
    }

    public String getmInboundDate() {
        return mInboundDate;
    }

    public void setmInboundDate(String mInboundDate) {
        this.mInboundDate = mInboundDate;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }
}
