package com.andersgpalm.travelapp;

/**
 * Created by ander on 9/2/2016.
 */
public class InitialValuesObj {

    double Price;
    String outboundDate;
    String inboundDate;
    String outboundAirport;

    public InitialValuesObj(double price, String outboundDate, String inboundDate, String outboundAirport) {
        Price = price;
        this.outboundDate = outboundDate;
        this.inboundDate = inboundDate;
        this.outboundAirport = outboundAirport;
    }

    public InitialValuesObj(){}

    public double getPrice() {
        return Price;
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
