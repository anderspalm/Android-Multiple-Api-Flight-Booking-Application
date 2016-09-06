package com.example.ander.travelapp;

/**
 * Created by ander on 9/2/2016.
 */
public class InitialValuesObj {

    double Price;
    String outboundDate;
    String inboundDate;
    String outboundAirport;
    String region;

    public InitialValuesObj(double price, String outboundDate, String inboundDate, String outboundAirport, String region) {
        Price = price;
        this.outboundDate = outboundDate;
        this.inboundDate = inboundDate;
        this.outboundAirport = outboundAirport;
        this.region = region;
    }

    public InitialValuesObj(){}

    public double getPrice() {
        return Price;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getOutboundDate() {
        return outboundDate;
    }

    public void setOutboundDate(String outboundDate) {
        this.outboundDate = outboundDate;
    }

    public String getInboundDate() {
        return inboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.inboundDate = inboundDate;
    }

    public String getOutboundAirport() {
        return outboundAirport;
    }

    public void setOutboundAirport(String outboundAirport) {
        this.outboundAirport = outboundAirport;
    }
}
