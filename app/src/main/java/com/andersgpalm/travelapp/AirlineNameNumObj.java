package com.andersgpalm.travelapp;

/**
 * Created by ander on 8/31/2016.
 */
public class AirlineNameNumObj {

    public String airlineName;
    public String airlineNum;

    public AirlineNameNumObj(String airlineName, String airlineNum) {
        this.airlineName = airlineName;
        this.airlineNum = airlineNum;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getAirlineNum() {
        return airlineNum;
    }

    public void setAirlineNum(String airlineNum) {
        this.airlineNum = airlineNum;
    }
}
