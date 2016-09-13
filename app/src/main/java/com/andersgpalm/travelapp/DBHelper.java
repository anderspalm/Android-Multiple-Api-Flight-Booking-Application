package com.andersgpalm.travelapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ander on 8/30/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public String mAirportName;
    public int mAirportLength;
    private static final String TAG = "DBHelper";

    public static final Integer DATABASE_VERSION = 22;

    public static final String IATA_TABLE = "iata_table";
    public static final String AIRLINE_NAME = "airline_name";
    public static final String AIRLINE_IATA_CODE = "iata_code";

    public static final String AGENT_TABLE = "agent_table";
    public static final String AGENT_NAME = "agent_name";
    public static final String AGENT_CODE = "agent_code";

    public static final String AIRPORT_TABLE = "airport_table";
    public static final String AIRPORT_ID = "_id";
    public static final String AIRPORT_NAME = "airport_name";
    public static final String AIRPORT_CODE = "iata_code";
    public static final String AIRPORT_REGION = "airport_region";
    public static final String AIRPORT_COUNTRY = "airport_country";
    public static final String AIRPORT_CITY = "airport_city";

    public static final String REGION_TABLE = "region_table";
    public static final String REGION_ID = "region_id";
    public static final String REGION_IMAGE = "region_image";
    public static final String REGION_NAME = "region_name";

    public static final String FLIGHT_CARD_TABLE = "flight_card_table";
    public static final String FLIGHT_CARD_COUNTRY = "flight_card_country";
    public static final String FLIGHT_CARD_COST = "flight_card_cost";
    public static final String FLIGHT_CARD_INB = "flight_card_inbound";
    public static final String FLIGHT_CARD_OUT = "flight_card_outbound";


    private DBHelper(Context context) {
        super(context, "db", null, DATABASE_VERSION);
    }

    private static DBHelper INSTANCE;

    public static DBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DBHelper(context);
        }
        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + IATA_TABLE + "(" +
                AIRLINE_NAME + " TEXT, " +
                AIRLINE_IATA_CODE + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + AGENT_TABLE + "("
                + AGENT_NAME + " TEXT, "
                + AGENT_CODE + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + AIRPORT_TABLE + "("
                + AIRPORT_ID + " TEXT PRIMARY KEY, "
                + AIRPORT_NAME + " TEXT, "
                + AIRPORT_CODE + " TEXT,"
                + AIRPORT_REGION + " TEXT, "
                + AIRPORT_COUNTRY + " TEXT, "
                + AIRPORT_CITY + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + REGION_TABLE + "("
                + REGION_ID + " INTEGER PRIMARY KEY, "
                + REGION_NAME + " TEXT, "
                + REGION_IMAGE + " TEXT )");

        sqLiteDatabase.execSQL("CREATE TABLE " + FLIGHT_CARD_TABLE + "("
                + FLIGHT_CARD_COUNTRY + " TEXT, "
                + FLIGHT_CARD_COST + " TEXT, "
                + FLIGHT_CARD_OUT + " TEXT, "
                + FLIGHT_CARD_INB + " TEXT )");

        insertAgentItems(sqLiteDatabase);
        insertAirlines(sqLiteDatabase);
        insertLocationData(sqLiteDatabase);
//        insertRegionItems(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + IATA_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AGENT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + AIRPORT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FLIGHT_CARD_TABLE);
        onCreate(sqLiteDatabase);
    }

    // *********************************************************************

    // Below: random airport code queries

    // *********************************************************************

    public String getRandomAirportCode() {
        SQLiteDatabase db = getReadableDatabase();
        int random = (int) (Math.random() * 100);
        String randToString = String.valueOf(random);
        Cursor cursor = db.rawQuery("SELECT * FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_ID + " = " + randToString, null);

        if (cursor.moveToFirst()) {
            mAirportName = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
            Log.i(TAG, "getRandomAirportCode: original inside" + cursor.getString(cursor.getColumnIndex(AIRPORT_CODE)));
            Log.i(TAG, "getRandomAirportCode: mAirportName: " + mAirportName);
        }
        db.close();
        return mAirportName;
    }

    public String getRandEUAirportCode() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_REGION + " like '%urop%'", null);

        int random = (int) (Math.random() * cursor.getCount());
        int randToString = random;
        cursor.moveToPosition(randToString);
        mAirportName = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
        Log.i(TAG, "getRandomAirportCode: original inside" + cursor.getString(cursor.getColumnIndex(AIRPORT_CODE)));
        Log.i(TAG, "getRandomAirportCode: mAirportName: " + mAirportName);
        db.close();
        return mAirportName;
    }

    public String getRandSAmericaAirportCode() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_REGION + " like '%outhEastAsi%'", null);

        int random = (int) (Math.random() * cursor.getCount());
        int randToString = random;

        cursor.moveToPosition(randToString);
        mAirportName = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
        Log.i(TAG, "getRandomAirportCode: original inside" + cursor.getString(cursor.getColumnIndex(AIRPORT_CODE)));
        Log.i(TAG, "getRandomAirportCode: mAirportName: " + mAirportName);

        db.close();
        return mAirportName;
    }

    public String getRandSAsiaAirportCode() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_REGION + " like '%outhAmeric%'", null);

        int random = (int) (Math.random() * cursor.getCount());
        int randToString = random;
        cursor.moveToPosition(randToString);
        mAirportName = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
        Log.i(TAG, "getRandomAirportCode: original inside" + cursor.getString(cursor.getColumnIndex(AIRPORT_CODE)));
        Log.i(TAG, "getRandomAirportCode: mAirportName: " + mAirportName);
        db.close();
        return mAirportName;
    }

    public String getRandNAmericaAirportCode() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_REGION + " like '%orthAmeric%'", null);

        int random = (int) (Math.random() * cursor.getCount());
        int randToString = random;
        cursor.moveToPosition(randToString);
        mAirportName = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
        Log.i(TAG, "getRandomAirportCode: original inside" + cursor.getString(cursor.getColumnIndex(AIRPORT_CODE)));
        Log.i(TAG, "getRandomAirportCode: mAirportName: " + mAirportName);
        db.close();
        return mAirportName;
    }

    // *********************************************************************

    // Below: general airport info queries

    // *********************************************************************

    public ArrayList<String> getAllAirportCodes() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + AIRPORT_CODE + "," + AIRPORT_NAME + " FROM " + AIRPORT_TABLE + " ORDER BY " + AIRPORT_NAME + " ASC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String code = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
            String name = cursor.getString(cursor.getColumnIndex(AIRPORT_NAME));
            arrayList.add(name + " (" + code + ")");
            cursor.moveToNext();
        }
        db.close();
        return arrayList;
    }

    public ArrayList<String> getAllEuropeCodes() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT " + AIRPORT_CODE + "," + AIRPORT_NAME + " FROM " +
                AIRPORT_TABLE + " WHERE " + AIRPORT_REGION + " Like '%urop%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String code = cursor.getString(cursor.getColumnIndex(AIRPORT_CODE));
            String name = cursor.getString(cursor.getColumnIndex(AIRPORT_NAME));
            arrayList.add(name + " (" + code + ")");
            cursor.moveToNext();
        }
        db.close();
        return arrayList;
    }

    public ArrayList<String> getAirportInfo(String code) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT "
                + AIRPORT_NAME + "," + AIRPORT_REGION + "," + AIRPORT_COUNTRY +
                " FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_CODE + " = " + code, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_NAME)));
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_REGION)));
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_COUNTRY)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<String> getAirportRegion(String code) {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT "+ AIRPORT_REGION +
                " FROM " + AIRPORT_TABLE + " WHERE " + AIRPORT_CODE + " = " + code, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_NAME)));
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_REGION)));
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_COUNTRY)));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return arrayList;
    }

//    public ArrayList<RegionObj> getRegionItems() {
//        SQLiteDatabase db = getReadableDatabase();
//        ArrayList<RegionObj> arrayList = new ArrayList<>();
//
//        Cursor cursor = db.rawQuery("SELECT "+ REGION_NAME + "," + REGION_IMAGE + " FROM " + REGION_TABLE, null);
//        if (cursor.moveToFirst()) {
//            while (!cursor.isAfterLast()) {
//                String name = (cursor.getString(cursor.getColumnIndex(REGION_NAME)));
//                String image = (cursor.getString(cursor.getColumnIndex(REGION_IMAGE)));
//                arrayList.add(new RegionObj(name,image));
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        return arrayList;
//    }

    public ArrayList<String> getAllCardVItems(){
        ArrayList<String> arrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " +
                FLIGHT_CARD_COST + "," +
                FLIGHT_CARD_COUNTRY + "," +
                FLIGHT_CARD_INB + "," +
                FLIGHT_CARD_OUT +
                " FROM " + FLIGHT_CARD_TABLE,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex(FLIGHT_CARD_COST)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(FLIGHT_CARD_COUNTRY)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(FLIGHT_CARD_INB)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(FLIGHT_CARD_OUT)));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public void insertCardVItems(String cost, String out, String inb, String country){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FLIGHT_CARD_COST,cost);
        contentValues.put(FLIGHT_CARD_COUNTRY,country);
        contentValues.put(FLIGHT_CARD_INB,inb);
        contentValues.put(FLIGHT_CARD_OUT,out);
        db.insert(FLIGHT_CARD_TABLE,null,contentValues);
    }

    public void removeCardVItems(){
        SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + FLIGHT_CARD_TABLE);
            db.execSQL("CREATE TABLE " + FLIGHT_CARD_TABLE + "("
                + FLIGHT_CARD_COUNTRY + " TEXT, "
                + FLIGHT_CARD_COST + " TEXT, "
                + FLIGHT_CARD_OUT + " TEXT, "
                + FLIGHT_CARD_INB + " TEXT )");
    }

    public ArrayList<String> getCityAndCountry(String code){
        String minusSkyCode;
        Log.i(TAG, "getCityAndCountry: " + code.substring(code.length()-3, code.length()));
        Log.i(TAG, "getCityAndCountry: " + code.substring(0, code.length() - 4));
//        if(code.substring(code.length()-3, code.length()) == "sky") {
            minusSkyCode = code.substring(0, code.length() - 4);
//        }
//        else{
//            minusSkyCode = code;
//        }
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT *" +
                " FROM " + AIRPORT_TABLE +
                " WHERE " + AIRPORT_CODE + " like " + "'%" +minusSkyCode + "%'"
                ,null);
        if(cursor.moveToFirst()) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_NAME)));
            arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_COUNTRY)));
            if (cursor.getString(cursor.getColumnIndex(AIRPORT_CITY)) != null) {
                arrayList.add(cursor.getString(cursor.getColumnIndex(AIRPORT_CITY)));
            }
        }
        db.close();
        return arrayList;
    }

//    public ArrayList<String> arrayList()

    // *********************************************************************

    // Below: filling the database on the tables' creation

    // *********************************************************************

//    private void insertRegionItems(SQLiteDatabase sqLiteDatabase) {
//        ArrayList<RegionObj> images = new ArrayList<>();
//        images.add(new RegionObj("South East Asia", "https://upload.wikimedia.org/wikipedia/commons/5/59/Sultan_Omar_Ali_Saifuddin_Mosque_02.jpg"));
//        images.add(new RegionObj("Europe", "http://media1.markusharju.se/2016/01/OluffaGrekland4.jpg"));
//        images.add(new RegionObj("South America","http://image.sciencenet.cn/album/201510/08/0714100xz3dq92a10yxyi4.jpg"));
//        images.add(new RegionObj("North America","https://www.lonelyplanet.com/travel-blog/tip-article/wordpress_uploads/2016/01/Bison-crossing-river-in-Yellowstone.-Image-courtesy-of-Wyoming-Office-of-Tourism.jpg"));
//
//        for (int i = 0; i < images.size(); i ++){
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(REGION_NAME,images.get(i).getmName());
//            contentValues.put(REGION_IMAGE,images.get(i).getmURL());
//            sqLiteDatabase.insert(REGION_TABLE,null,contentValues);
//        }
//
//    }

    private void insertAgentItems(SQLiteDatabase sqLiteDatabase) {
        ArrayList<AgentNNObj> arrayInner = new ArrayList();

        arrayInner.add(new AgentNNObj("Opodo", "3503883"));
        arrayInner.add(new AgentNNObj("GetAFlight", "2627398"));
        arrayInner.add(new AgentNNObj("lastminute.com", "3165195"));
        arrayInner.add(new AgentNNObj("Bravofly", "2043147"));
        arrayInner.add(new AgentNNObj("Fly-Sharp", "2499240"));
        arrayInner.add(new AgentNNObj("TravelTrolley", "3943896"));
        arrayInner.add(new AgentNNObj("omegaflightstore.com", "3496199"));
        arrayInner.add(new AgentNNObj("EmailFlights", "3247564"));
        arrayInner.add(new AgentNNObj("travelup", "4061456"));
        arrayInner.add(new AgentNNObj("GotoGate", "2628363"));
        arrayInner.add(new AgentNNObj("eDreams", "2370315"));
        arrayInner.add(new AgentNNObj("STATravel", "3920907"));
        arrayInner.add(new AgentNNObj("Lees-Travel", "3146004"));
        arrayInner.add(new AgentNNObj("Mytrip", "1963108"));
        arrayInner.add(new AgentNNObj("Tripsta", "4056843"));
        arrayInner.add(new AgentNNObj("BudgetAir", "3874827"));
        arrayInner.add(new AgentNNObj("QatarAirways", "3690449"));
        arrayInner.add(new AgentNNObj("CheapOair", "2158117"));
        arrayInner.add(new AgentNNObj("wanguk", "2172259"));
        arrayInner.add(new AgentNNObj("BritishAirways", "2032127"));
        arrayInner.add(new AgentNNObj("EmailFlights", "3247564"));
        arrayInner.add(new AgentNNObj("ebookers", "2365707"));
        arrayInner.add(new AgentNNObj("UnitedAirlines", "4132306"));
        arrayInner.add(new AgentNNObj("Expedia", "4499211"));
        arrayInner.add(new AgentNNObj("Despegar", "2261936"));
        arrayInner.add(new AgentNNObj("Carlton Leisure", "2142076"));

        for (int i = 0; i < arrayInner.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AGENT_NAME, arrayInner.get(i).getAgentName());
            contentValues.put(AGENT_CODE, arrayInner.get(i).getAgentNum());
            sqLiteDatabase.insert(AGENT_TABLE, null, contentValues);
        }
    }

    private void insertAirlines(SQLiteDatabase sqLiteDatabase) {
        ArrayList<AirlineNameNumObj> arrayList = new ArrayList<>();

        arrayList.add(new AirlineNameNumObj("Aegean Airlines", "A3"));
        arrayList.add(new AirlineNameNumObj("Air Lingus", "EI"));
        arrayList.add(new AirlineNameNumObj("Aero Airlines", "EE"));
        arrayList.add(new AirlineNameNumObj("Alpi Eagles", "E8"));
        arrayList.add(new AirlineNameNumObj("Atlantic Airways", "RC"));
        arrayList.add(new AirlineNameNumObj("Air Finland", "FIF"));
        arrayList.add(new AirlineNameNumObj("Air Iceland", "NY"));
        arrayList.add(new AirlineNameNumObj("Air Philippines", "2P"));
        arrayList.add(new AirlineNameNumObj("Air Georgian", "ZX"));
        arrayList.add(new AirlineNameNumObj("Aegean Airlines", "A3"));

        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AIRLINE_NAME, arrayList.get(i).getAirlineName());
            contentValues.put(AIRLINE_IATA_CODE, arrayList.get(i).getAirlineNum());
            sqLiteDatabase.insert(IATA_TABLE, null, contentValues);
        }

    }

    private void insertLocationData(SQLiteDatabase sqLiteDatabase) {

        ArrayList<AirportInformationObj> arrayList = new ArrayList<>();
        arrayList.add(new AirportInformationObj("1", "Jakarta", "JKT", "SouthEastAsia", "Indonesia", "Jakarta"));
        arrayList.add(new AirportInformationObj("2", "Bangkok", "BKK", "SouthEastAsia", "Thailand", "Bangkok"));
        arrayList.add(new AirportInformationObj("3", "Manila Aquino Intl", "MNL", "SouthEastAsia", "Philipines", "Manila"));
        arrayList.add(new AirportInformationObj("4", "Kuala Lumpur", "KUL", "SouthEastAsia", "Malaysia", "Kuala Lumpur"));
        arrayList.add(new AirportInformationObj("5", "Surabaya", "SUB", "SouthEastAsia", "Indonesia", "Surabaya"));
        arrayList.add(new AirportInformationObj("6", "Ho Chi Minh City", "SGN", "SouthEastAsia", "Vietnam", "Ho Chi Minh City"));
        arrayList.add(new AirportInformationObj("7", "Hong Kong Intl", "HKG", "SouthEastAsia", "China", "Hong Kong"));
        arrayList.add(new AirportInformationObj("8", "Bali(Denspasar)", "DPS", "SouthEastAsia", "Indonesia", "Denspasar"));
        arrayList.add(new AirportInformationObj("9", "Hanoi", "HAN", "SouthEastAsia", "Vietnam"));
        arrayList.add(new AirportInformationObj("10", "Singapore Changi", "SIN", "SouthEastAsia", "Singapore"));
        arrayList.add(new AirportInformationObj("11", "Taipei", "TPE", "SouthEastAsia", "Taipei"));
        arrayList.add(new AirportInformationObj("12", "Mactan Cebu Intl", "CEB", "SouthEastAsia", "Philippines", "Mactan"));
        arrayList.add(new AirportInformationObj("13", "Macau", "MFM", "SouthEastAsia", "China"));
        arrayList.add(new AirportInformationObj("14", "Mexico City Juarez", "MEX", "NorthAmerica", "Mexico", "Mexico City"));
        arrayList.add(new AirportInformationObj("15", "Toronto", "YTO", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("16", "Montreal", "YMQ", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("17", "Tijuana", "TIJ", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("18", "Kelowna", "YLW", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("19", "Merida", "MID", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("20", "Vancouver", "YVR", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("21", "Saskatoon", "YXE", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("22", "Villahermosa", "VSA", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("23", "Hermosillo", "HMO", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("24", "Regina", "CUU", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("25", "Vancouver", "YVR", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("26", "Veracruz", "VER", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("27", "London", "YXU", "NorthAmerica", "USA"));
        arrayList.add(new AirportInformationObj("28", "Torreon", "TRC", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("29", "Aguascientes", "AGU", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("30", "Mazatlan", "MZT", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("31", "Queretaro", "QRO", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("32", "Nanaimo", "YCD", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("33", "Winnipeg", "YWG", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("34", "Halifax Intl", "YHZ", "NorthAmerica", "Canada", "Halifax"));
        arrayList.add(new AirportInformationObj("35", "Ottawa Intl", "YOW", "NorthAmerica", "Canada", "Ottawa"));
        arrayList.add(new AirportInformationObj("36", "Edmonton Intl", "YEG", "NorthAmerica", "Canada", "Edmonton"));
        arrayList.add(new AirportInformationObj("37", "Victoria", "YYJ", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("38", "Oaxaca", "OAX", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("39", "Deer Lake", "YDF", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("40", "Cancun", "CUN", "NorthAmerica", "Mexico"));
        arrayList.add(new AirportInformationObj("41", "Calgary", "YYC", "NorthAmerica", "Canada"));
        arrayList.add(new AirportInformationObj("42", "Francisco Bangoy Intl", "DVO", "SouthEastAsia", "Philippines","Davao City"));
        arrayList.add(new AirportInformationObj("43", "London", "LON", "Europe", "England"));
        arrayList.add(new AirportInformationObj("44", "Moscow", "MOW", "Europe", "Russia"));
        arrayList.add(new AirportInformationObj("45", "Paris", "PAR", "Europe", "France"));
        arrayList.add(new AirportInformationObj("46", "Amsterdam", "AMS", "Europe", "Netherlands"));
        arrayList.add(new AirportInformationObj("47", "Frankfurt", "FRA", "Europe", "Germany"));
        arrayList.add(new AirportInformationObj("48", "Madrid", "MAD", "Europe", "Spain"));
        arrayList.add(new AirportInformationObj("49", "Olso", "OSL", "Europe", "Norway"));
        arrayList.add(new AirportInformationObj("50", "Rome", "ROM", "Europe", "Italy"));
        arrayList.add(new AirportInformationObj("51", "Dublin", "DUB", "Europe", "Republic of Ireland"));
        arrayList.add(new AirportInformationObj("52", "Milan", "MIL", "Europe", "Italy"));
        arrayList.add(new AirportInformationObj("53", "Munich", "MUC", "Europe", "Germany"));
        arrayList.add(new AirportInformationObj("54", "Berlin", "BER", "Europe", "Germany"));
        arrayList.add(new AirportInformationObj("55", "Stockholm", "STO", "Europe", "Sweden"));
        arrayList.add(new AirportInformationObj("56", "St Petersburg Pulkovo", "LED", "Europe", "Russia", "Saint Petersburg"));
        arrayList.add(new AirportInformationObj("57", "Athens Intl", "ATH", "Europe", "Greece", "Athens"));
        arrayList.add(new AirportInformationObj("58", "Zurich", "ZRH", "Europe", "Switzerland"));
        arrayList.add(new AirportInformationObj("59", "Vienna", "VIE", "Europe", "Italy"));
        arrayList.add(new AirportInformationObj("60", "Manchester", "MAN", "Europe", "England"));
        arrayList.add(new AirportInformationObj("61", "Birmingham", "BHX", "Europe", "England"));
        arrayList.add(new AirportInformationObj("62", "Malaga", "AGP", "Europe", "Spain"));
        arrayList.add(new AirportInformationObj("63", "Palma Majorca", "PMI", "Europe", "Spain", "Majorca"));
        arrayList.add(new AirportInformationObj("64", "Catania Fontanarossa", "CTA", "Europe", "Italy", "Catania"));
        arrayList.add(new AirportInformationObj("65", "Tenerife", "TCI", "Europe", "Spain"));
        arrayList.add(new AirportInformationObj("66", "Gran Canaria Las Palmas", "LPA", "Europe", "Spain", "Las Palmas"));
        arrayList.add(new AirportInformationObj("67", "Trondheim", "TRD", "Europe", "Norway"));
        arrayList.add(new AirportInformationObj("68", "Faro", "FAO", "Europe", "Portugal"));
        arrayList.add(new AirportInformationObj("69", "Ercan", "ECN", "Europe", "Cyprus"));
        arrayList.add(new AirportInformationObj("70", "Dusseldorf", "DUS", "Europe", "Germany"));
        arrayList.add(new AirportInformationObj("71", "Bergen", "BGO", "Europe", "Norway"));
        arrayList.add(new AirportInformationObj("72", "Edinburgh", "EDI", "Europe", "Scotland"));
        arrayList.add(new AirportInformationObj("73", "Toulouse", "TLS", "Europe", "France"));
        arrayList.add(new AirportInformationObj("74", "Alicante", "ALC", "Europe", "Spain"));
        arrayList.add(new AirportInformationObj("75", "Nice", "NCE", "Europe", "France"));
        arrayList.add(new AirportInformationObj("76", "Belfast", "BFS", "Europe", "Northern-Ireland"));
        arrayList.add(new AirportInformationObj("77", "Helsinki Vantaa", "HEL", "Europe", "Finland", "Helsinki"));
        arrayList.add(new AirportInformationObj("78", "Fortaleza", "FOR", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("79", "Caracas", "CCS", "SouthAmerica", "Colombia"));
        arrayList.add(new AirportInformationObj("80", "Manaus", "MAO", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("81", "Cali", "CLO", "SouthAmerica", "Colombia"));
        arrayList.add(new AirportInformationObj("82", "Rio De Janeiro", "RIO", "SouthAmerica", "Brazil", "Rio de Janeiro"));
        arrayList.add(new AirportInformationObj("83", "Lima", "LIM", "SouthAmerica", "Peru"));
        arrayList.add(new AirportInformationObj("84", "Buenos Aires", "BUE", "SouthAmerica", "Argentina", "Buenos Aires"));
        arrayList.add(new AirportInformationObj("85", "Brasilia", "BSB", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("86", "Quito", "UIO", "SouthAmerica", "Ecuador"));
        arrayList.add(new AirportInformationObj("87", "Manaus", "MAO", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("88", "Caracas", "CCS", "SouthAmerica", "Venezuela"));
        arrayList.add(new AirportInformationObj("89", "Barranquilla", "BAQ", "SouthAmerica", "Colombia"));
        arrayList.add(new AirportInformationObj("90", "Guayaquil", "GYE", "SouthAmerica", "Ecuador"));
        arrayList.add(new AirportInformationObj("91", "Recife", "REC", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("92", "Medellin", "MDE", "SouthAmerica", "Colombia"));
        arrayList.add(new AirportInformationObj("93", "Cartagena", "CTG", "SouthAmerica", "Colombia"));
        arrayList.add(new AirportInformationObj("94", "Cuzco", "CUZ", "SouthAmerica", "Peru"));
        arrayList.add(new AirportInformationObj("95", "Salvador", "SSA", "SouthAmerica", "Argentina"));
        arrayList.add(new AirportInformationObj("96", "Puerto Montt", "PMC", "SouthAmerica", "Chile", "Puerto Montt"));
        arrayList.add(new AirportInformationObj("97", "Goiania", "GYN", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("98", "Santa Marta", "SMR", "SouthAmerica", "Colombia", "Santa Marta"));
        arrayList.add(new AirportInformationObj("99", "Belem", "BEL", "SouthAmerica", "Brazil"));
        arrayList.add(new AirportInformationObj("100", "Luzon Clark Intl","CRK","SouthEastAsia","Philippines", "Angeles Philippines"));

        mAirportLength = arrayList.size();
        Log.i(TAG, "insertLocationData: " + mAirportLength);

        for (int i = 0; i < arrayList.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(AIRPORT_ID, arrayList.get(i).getmAirport_ID());
            contentValues.put(AIRPORT_NAME, arrayList.get(i).getmAirportName());
            contentValues.put(AIRPORT_CODE, arrayList.get(i).getmAirportIata());
            contentValues.put(AIRPORT_REGION, arrayList.get(i).getmAirportRegion());
            contentValues.put(AIRPORT_COUNTRY, arrayList.get(i).getmAirportCountry());
            if(arrayList.get(i).getmCity() != null){
                contentValues.put(AIRPORT_CITY, arrayList.get(i).getmCity());
            }
            sqLiteDatabase.insert(AIRPORT_TABLE, null, contentValues);
        }

    }
}
