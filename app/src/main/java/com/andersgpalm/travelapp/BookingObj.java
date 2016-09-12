package com.andersgpalm.travelapp;

/**
 * Created by anders on 9/2/2016.
 */
public class BookingObj {

    String mDestinedCode, mOriginCode, mPrice, mOutboundDate, mInboundDate;
    String mCountry, mDeepLinkUrl, mAgentName, mAgentPic, mOriginCity, mDestinedCity;
    String oAirName, dAirName, mOCountry, mDcountry;

    public BookingObj(String mDestinedCode, String mOriginCode, String mPrice, String mOutboundDate,
                      String mInboundDate, String mCountry, String mDeepLinkUrl, String mAgentName,
                      String mAgentPic, String mOriginCity, String mDestinedCity, String oAirName,
                      String dAirName, String mOCountry, String mDcountry)
    {
        this.mDestinedCode = mDestinedCode;
        this.mOriginCode = mOriginCode;
        this.mPrice = mPrice;
        this.mOutboundDate = mOutboundDate;
        this.mInboundDate = mInboundDate;
        this.mCountry = mCountry;
        this.mDeepLinkUrl = mDeepLinkUrl;
        this.mAgentName = mAgentName;
        this.mAgentPic = mAgentPic;
        this.mOriginCity = mOriginCity;
        this.mDestinedCity = mDestinedCity;
        this.oAirName = oAirName;
        this.dAirName = dAirName;
        this.mOCountry = mOCountry;
        this.mDcountry = mDcountry;
    }

    public String getmDestinedCode() {
        return mDestinedCode;
    }

    public String getmOriginCode() {
        return mOriginCode;
    }

    public String getmPrice() {
        return mPrice;
    }

    public String getmOutboundDate() {
        return mOutboundDate;
    }

    public String getmInboundDate() {
        return mInboundDate;
    }

    public String getmCountry() {
        return mCountry;
    }

    public String getmDeepLinkUrl() {
        return mDeepLinkUrl;
    }

    public String getmAgentName() {
        return mAgentName;
    }

    public String getmAgentPic() {
        return mAgentPic;
    }

    public String getmOriginCity() {
        return mOriginCity;
    }

    public String getmDestinedCity() {
        return mDestinedCity;
    }

    public String getoAirName() {
        return oAirName;
    }

    public String getdAirName() {
        return dAirName;
    }

    public String getmOCountry() {
        return mOCountry;
    }

    public String getmDcountry() {
        return mDcountry;
    }
}
