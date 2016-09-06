package com.example.ander.travelapp;

import java.util.ArrayList;

/**
 * Created by ander on 8/30/2016.
 */
public class MasterListSingleton {

    ArrayList<ItineraryClassGson> itinerariesList;

    private static MasterListSingleton INSTANCE;

    private MasterListSingleton() {
    }

    public static MasterListSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MasterListSingleton();
        }
        return INSTANCE;
    }

    public ArrayList<ItineraryClassGson> getItinerariesList() {
        return itinerariesList;
    }

    public void setItinerariesList(ArrayList<ItineraryClassGson> itinerariesList) {
        this.itinerariesList = itinerariesList;
    }

    public static MasterListSingleton getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(MasterListSingleton INSTANCE) {
        MasterListSingleton.INSTANCE = INSTANCE;
    }
}
