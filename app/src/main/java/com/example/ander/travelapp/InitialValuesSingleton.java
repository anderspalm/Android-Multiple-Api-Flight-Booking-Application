package com.example.ander.travelapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ander on 9/2/2016.
 */
public class InitialValuesSingleton {

    public InitialValuesObj mInitialValuesObj;
    public List<BookingObj> mBookingObject;

    public static InitialValuesSingleton INSTANCE;

    public static InitialValuesSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new InitialValuesSingleton();
        }
        return INSTANCE;
    }

    public List<BookingObj> getAllBookingObjs() {
        return mBookingObject;
    }

    public void addBookingObjToList(BookingObj bookingObj) {
        if(mBookingObject == null){
            mBookingObject = new ArrayList<>();
        }
        mBookingObject.add(bookingObj);
    }

    public void clearBookingObjects(){
        if(mBookingObject == null){
            mBookingObject = new ArrayList<>();
        }
        if(mBookingObject != null){
            mBookingObject.clear();
        }
    }

    public InitialValuesObj getmInitialValuesObj() {
        return mInitialValuesObj;
    }

    public void setmInitialValuesObj(InitialValuesObj initialValuesObjs) {
        if(mInitialValuesObj == null){
            mInitialValuesObj = new InitialValuesObj();
        }
        mInitialValuesObj = initialValuesObjs;
    }

}
