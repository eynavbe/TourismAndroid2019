package com.example.myprojectandroid;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public class TourDatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "toursManager.db";
    private static final String TABLE_TOURS = "tours";
    private static final String KEY_ID = "id";
    private static final String KEY_X = "X";
    private static final String KEY_Y = "Y";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_DIFFICULTY_LEVEL = "DifficultyLevel";
    private static final String KEY_TRACK_TYPE = "TrackType";
    private static final String KEY_TRACK_DURATION = "TrackDuration";
    private static final String KEY_TRACK_LENGTH = "TrackLength";
    private static final String KEY_ADMISSION_CHARGE = "AdmissionCharge";
    private static final String KEY_BEST_SEASON = "BestSeason";
    private static final String KEY_PRECAUTION = "Precautions";
    private static final String KEY_BY_APPOINTMENT = "ByAppointment";
    private static final String KEY_NAME = "name";
    private static final String KEY_TYPE_RECREATION = "TypeRecreation";
    private static final String KEY_CITY = "City";
    private static final String KEY_NEAR_TO = "NearTo";
    private static final String KEY_OPENING_HOURS = "OpeningHours";
    private static final String KEY_PARKING = "Parking";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_REGION = "Region";
    private static final String KEY_SUITABLE_FOR_CHILDREN = "SuitableForChildren";
    private static final String KEY_URL = "URL";
    private static final String KEY_FULL_DESCRIPTION = "FullDescription";
    private static final String KEY_PRODUCT_URL = "ProductUrl";
    private static final String KEY_PIC_URL = "PicUrl";
    private static final String KEY_ACCESSIBILITY = "Accessibility";
    private static final String KEY_ADDRESS = "Address";
    static  Context context1;
    static  Activity activity;
    SQLiteDatabase database;

    private boolean mLocationPermissionGranted = false;
    public TourDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VER);
        context1 = context;
        activity = (Activity)context1;
//        try {
//            database = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
//                    SQLiteDatabase.OPEN_READONLY);
//            database.close();
//        } catch (SQLiteException e) {
//            // database doesn't exist yet.
//        }
        database = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TOURS_TABLE = "CREATE TABLE " + TABLE_TOURS + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + KEY_X + " REAL, "
                + KEY_Y + " REAL, "
                + KEY_TYPE + " TEXT, "
                + KEY_DIFFICULTY_LEVEL + " TEXT, "
                + KEY_TRACK_TYPE + " TEXT, "
                + KEY_TRACK_DURATION + " TEXT, "
                + KEY_TRACK_LENGTH + " TEXT, "
                + KEY_ADMISSION_CHARGE + " TEXT, "
                + KEY_BEST_SEASON + " TEXT, "
                + KEY_PRECAUTION + " TEXT, "
                + KEY_BY_APPOINTMENT + " TEXT, "
                + KEY_NAME + " TEXT, "
                + KEY_TYPE_RECREATION + " TEXT, "
                + KEY_CITY + " TEXT, "
                + KEY_NEAR_TO + " TEXT, "
                + KEY_OPENING_HOURS + " TEXT, "
                + KEY_PARKING + " TEXT, "
                + KEY_PHONE + " TEXT, "
                + KEY_REGION + " TEXT, "
                + KEY_SUITABLE_FOR_CHILDREN + " TEXT, "
                + KEY_URL + " TEXT, "
                + KEY_FULL_DESCRIPTION + " TEXT, "
                + KEY_PRODUCT_URL + " TEXT, "
                + KEY_PIC_URL + " TEXT, "
                + KEY_ACCESSIBILITY + " TEXT, "
                + KEY_ADDRESS + " TEXT" + ")";

        db.execSQL(CREATE_TOURS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_TOURS);
        onCreate(db);
    }
    void addTour(Tour tour){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_X, tour.getX());
        values.put(KEY_Y, tour.getY());
        values.put(KEY_TYPE, tour.getType());
        values.put(KEY_DIFFICULTY_LEVEL, tour.getDifficultyLevel());
        values.put(KEY_TRACK_TYPE, tour.getTrackType());
        values.put(KEY_TRACK_DURATION, tour.getTrackDuration());
        values.put(KEY_TRACK_LENGTH, tour.getTrackLength());
        values.put(KEY_ADMISSION_CHARGE, tour.getAdmissionCharge());
        values.put(KEY_BEST_SEASON, tour.getBestSeason());
        values.put(KEY_PRECAUTION, tour.getPrecautions());
        values.put(KEY_BY_APPOINTMENT, tour.getByAppointment());
        values.put(KEY_NAME, tour.getName());
        values.put(KEY_TYPE_RECREATION, tour.getTypeRecreation());
        values.put(KEY_CITY, tour.getCity());
        values.put(KEY_NEAR_TO, tour.getNearTo());
        values.put(KEY_OPENING_HOURS, tour.getOpeningHours());
        values.put(KEY_PARKING, tour.getParking());
        values.put(KEY_PHONE, tour.getPhone());
        values.put(KEY_REGION, tour.getRegion());
        values.put(KEY_SUITABLE_FOR_CHILDREN, tour.getSuitableForChildren());
        values.put(KEY_URL, tour.getURL());
        values.put(KEY_FULL_DESCRIPTION, tour.getFullDescription());
        values.put(KEY_PRODUCT_URL, tour.getProductUrl());
        values.put(KEY_PIC_URL, tour.getPicUrl());
        values.put(KEY_ACCESSIBILITY, tour.getAccessibility());
        values.put(KEY_ADDRESS, tour.getAddress());

        db.insert(TABLE_TOURS,null,values);
        db.close();
    }
    Tour getTour(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TOURS,new String[]{KEY_ID,KEY_X ,KEY_Y,KEY_TYPE,KEY_DIFFICULTY_LEVEL,KEY_TRACK_TYPE ,KEY_TRACK_DURATION,KEY_TRACK_LENGTH,KEY_ADMISSION_CHARGE,KEY_BEST_SEASON,KEY_PRECAUTION,KEY_BY_APPOINTMENT,KEY_NAME,KEY_TYPE_RECREATION,KEY_CITY,KEY_NEAR_TO,KEY_OPENING_HOURS, KEY_PARKING,KEY_PHONE ,KEY_REGION,KEY_SUITABLE_FOR_CHILDREN,KEY_URL, KEY_FULL_DESCRIPTION ,KEY_PRODUCT_URL,KEY_PIC_URL,KEY_ACCESSIBILITY,KEY_ADDRESS},KEY_ID + " = ?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
        }
        Tour tour = new Tour(Integer.parseInt(cursor.getString(0)),Double.parseDouble(cursor.getString(1)),Double.parseDouble(cursor.getString(2)),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17),cursor.getString(18),cursor.getString(19),cursor.getString(20),cursor.getString(21),cursor.getString(22),cursor.getString(23),cursor.getString(24),cursor.getString(25),cursor.getString(26));
        cursor.close();
        db.close();
        return tour;
    }
    public List<Tour> getAllTours(){
        List<Tour> tourList = new ArrayList<>();
        String selectQuery = "SELECT *FROM " + TABLE_TOURS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                Tour tour = new Tour();
                if (cursor.getString(0) != null){
                    tour.setId(Integer.parseInt(cursor.getString(0)));
                }
                tour.setX(cursor.getDouble(1));
                tour.setY(cursor.getDouble(2));
                tour.setType(cursor.getString(3));
                tour.setDifficultyLevel(cursor.getString(4));
                tour.setTrackType(cursor.getString(5));
                tour.setTrackDuration(cursor.getString(6));
                tour.setTrackLength(cursor.getString(7));
                tour.setAdmissionCharge(cursor.getString(8));
                tour.setBestSeason(cursor.getString(9));
                tour.setPrecautions(cursor.getString(10));
                tour.setByAppointment(cursor.getString(11));
                tour.setName(cursor.getString(12));
                tour.setTypeRecreation(cursor.getString(13));
                tour.setCity(cursor.getString(14));
                tour.setNearTo(cursor.getString(15));
                tour.setOpeningHours(cursor.getString(16));
                tour.setParking(cursor.getString(17));
                tour.setPhone(cursor.getString(18));
                tour.setRegion(cursor.getString(19));
                tour.setSuitableForChildren(cursor.getString(20));
                tour.setURL(cursor.getString(21));
                tour.setFullDescription(cursor.getString(22));
                tour.setProductUrl(cursor.getString(23));
                tour.setPicUrl(cursor.getString(24));
                tour.setAccessibility(cursor.getString(25));
                tour.setAddress(cursor.getString(26));
                tourList.add(tour);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tourList;
    }
    public int updateTour(Tour tour){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_X, tour.getX());
        values.put(KEY_Y, tour.getY());
        values.put(KEY_TYPE, tour.getType());
        values.put(KEY_DIFFICULTY_LEVEL, tour.getDifficultyLevel());
        values.put(KEY_TRACK_TYPE, tour.getTrackType());
        values.put(KEY_TRACK_DURATION, tour.getTrackDuration());
        values.put(KEY_TRACK_LENGTH, tour.getTrackLength());
        values.put(KEY_ADMISSION_CHARGE, tour.getAdmissionCharge());
        values.put(KEY_BEST_SEASON, tour.getBestSeason());
        values.put(KEY_PRECAUTION, tour.getPrecautions());
        values.put(KEY_BY_APPOINTMENT, tour.getByAppointment());
        values.put(KEY_NAME, tour.getName());
        values.put(KEY_TYPE_RECREATION, tour.getTypeRecreation());
        values.put(KEY_CITY, tour.getCity());
        values.put(KEY_NEAR_TO, tour.getNearTo());
        values.put(KEY_OPENING_HOURS, tour.getOpeningHours());
        values.put(KEY_PARKING, tour.getParking());
        values.put(KEY_PHONE, tour.getPhone());
        values.put(KEY_REGION, tour.getRegion());
        values.put(KEY_SUITABLE_FOR_CHILDREN, tour.getSuitableForChildren());
        values.put(KEY_URL, tour.getURL());
        values.put(KEY_FULL_DESCRIPTION, tour.getFullDescription());
        values.put(KEY_PRODUCT_URL, tour.getProductUrl());
        values.put(KEY_PIC_URL, tour.getPicUrl());
        values.put(KEY_ACCESSIBILITY, tour.getAccessibility());
        values.put(KEY_ADDRESS, tour.getAddress());
        return db.update(TABLE_TOURS,values,KEY_ID+" = ?",
                new String[]{String.valueOf(tour.getId())});
    }
    public void deleteTour(Tour tour){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TOURS, KEY_ID + " = ?",
                new String[]{String.valueOf(tour.getId())});
        db.close();

    }
    public int getToursCount(){
        String countQuery = "SELECT *FROM " +TABLE_TOURS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    public List<Tour> getToursByCity(String city){
        String countQuery = "SELECT *FROM " +TABLE_TOURS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TOURS,new String[]{KEY_ID,KEY_X ,KEY_Y,KEY_TYPE,KEY_DIFFICULTY_LEVEL,KEY_TRACK_TYPE ,KEY_TRACK_DURATION,KEY_TRACK_LENGTH,KEY_ADMISSION_CHARGE,KEY_BEST_SEASON,KEY_PRECAUTION,KEY_BY_APPOINTMENT,KEY_NAME,KEY_TYPE_RECREATION,KEY_CITY,KEY_NEAR_TO,KEY_OPENING_HOURS, KEY_PARKING,KEY_PHONE ,KEY_REGION,KEY_SUITABLE_FOR_CHILDREN,KEY_URL, KEY_FULL_DESCRIPTION ,KEY_PRODUCT_URL,KEY_PIC_URL,KEY_ACCESSIBILITY,KEY_ADDRESS},KEY_CITY + " LIKE ?" ,new String[]{"%"+city+"%"},null,null,"name"+" ASC",null);

       List<Tour> result = new ArrayList<>();


        if (cursor.moveToFirst()){
            do{
                Tour tour = new Tour();
                if (cursor.getString(0) != null){
                    tour.setId(Integer.parseInt(cursor.getString(0)));
                }
                tour.setX(cursor.getDouble(1));
                tour.setY(cursor.getDouble(2));
                tour.setType(cursor.getString(3));
                tour.setDifficultyLevel(cursor.getString(4));
                tour.setTrackType(cursor.getString(5));
                tour.setTrackDuration(cursor.getString(6));
                tour.setTrackLength(cursor.getString(7));
                tour.setAdmissionCharge(cursor.getString(8));
                tour.setBestSeason(cursor.getString(9));
                tour.setPrecautions(cursor.getString(10));
                tour.setByAppointment(cursor.getString(11));
                tour.setName(cursor.getString(12));
                tour.setTypeRecreation(cursor.getString(13));
                tour.setCity(cursor.getString(14));
                tour.setNearTo(cursor.getString(15));
                tour.setOpeningHours(cursor.getString(16));
                tour.setParking(cursor.getString(17));
                tour.setPhone(cursor.getString(18));
                tour.setRegion(cursor.getString(19));
                tour.setSuitableForChildren(cursor.getString(20));
                tour.setURL(cursor.getString(21));
                tour.setFullDescription(cursor.getString(22));
                tour.setProductUrl(cursor.getString(23));
                tour.setPicUrl(cursor.getString(24));
                tour.setAccessibility(cursor.getString(25));
                tour.setAddress(cursor.getString(26));

                result.add(tour);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return result;
    }
    public List<Tour> getToursByType(String nameCity, List<String> types , String trackType, String parking, String  accessibility, String  suitableForChildren){

        String countQuery = "SELECT *FROM " +TABLE_TOURS;
        if (trackType == null){trackType = "";}
        if (parking == null){ parking = ""; }
        if (accessibility == null){accessibility = "";}
        if (suitableForChildren == null){suitableForChildren = "";}

        SQLiteDatabase db = this.getReadableDatabase();
        List<Tour> result = new ArrayList<>();
        String type = "";
        int i = 0;
        do {
            if ((types.size() > 0) && (i == 0)){
                type = types.get(0);
            }
            else{ if (i>0){type = types.get(i);}}

            String[] columnNames = {"id","X","Y","Type","DifficultyLevel","TrackType","TrackDuration","TrackLength","AdmissionCharge","BestSeason","Precautions","ByAppointment","name","TypeRecreation","City","NearTo","OpeningHours","Parking","phone","Region","SuitableForChildren","URL","FullDescription","ProductUrl","PicUrl","Accessibility","Address"};

            Cursor cursor = db.query(TABLE_TOURS,new String[]{KEY_ID,KEY_X ,KEY_Y,KEY_TYPE,KEY_DIFFICULTY_LEVEL,KEY_TRACK_TYPE ,KEY_TRACK_DURATION,KEY_TRACK_LENGTH,KEY_ADMISSION_CHARGE,KEY_BEST_SEASON,KEY_PRECAUTION,KEY_BY_APPOINTMENT,KEY_NAME,KEY_TYPE_RECREATION,KEY_CITY,KEY_NEAR_TO,KEY_OPENING_HOURS, KEY_PARKING,KEY_PHONE ,KEY_REGION,KEY_SUITABLE_FOR_CHILDREN,KEY_URL, KEY_FULL_DESCRIPTION ,KEY_PRODUCT_URL,KEY_PIC_URL,KEY_ACCESSIBILITY,KEY_ADDRESS}, KEY_CITY + " LIKE ? AND " + KEY_TYPE + " LIKE ? AND " + KEY_ACCESSIBILITY + " LIKE ? AND " + KEY_SUITABLE_FOR_CHILDREN+ " LIKE ? AND " + KEY_TRACK_TYPE + " LIKE ? AND " + KEY_PARKING + " LIKE ? ",new String[]{"%"+nameCity+"%","%"+type+"%","%"+accessibility+"%","%"+suitableForChildren+"%","%"+trackType+"%","%"+parking+"%"},null,null,"name"+" ASC",null);
//            String[] columnNames = cursor.getColumnNames();

            if (cursor.moveToFirst()){
                do{
                    Tour tour = new Tour();
                    if (cursor.getString(0) != null){
                        tour.setId(Integer.parseInt(cursor.getString(0)));
                    }
                    tour.setX(cursor.getDouble(1));
                    tour.setY(cursor.getDouble(2));
                    tour.setType(cursor.getString(3));
                    tour.setDifficultyLevel(cursor.getString(4));
                    tour.setTrackType(cursor.getString(5));
                    tour.setTrackDuration(cursor.getString(6));
                    tour.setTrackLength(cursor.getString(7));
                    tour.setAdmissionCharge(cursor.getString(8));
                    tour.setBestSeason(cursor.getString(9));
                    tour.setPrecautions(cursor.getString(10));
                    tour.setByAppointment(cursor.getString(11));
                    tour.setName(cursor.getString(12));
                    tour.setTypeRecreation(cursor.getString(13));
                    tour.setCity(cursor.getString(14));
                    tour.setNearTo(cursor.getString(15));
                    tour.setOpeningHours(cursor.getString(16));
                    tour.setParking(cursor.getString(17));
                    tour.setPhone(cursor.getString(18));
                    tour.setRegion(cursor.getString(19));
                    tour.setSuitableForChildren(cursor.getString(20));
                    tour.setURL(cursor.getString(21));
                    tour.setFullDescription(cursor.getString(22));
                    tour.setProductUrl(cursor.getString(23));
                    tour.setPicUrl(cursor.getString(24));
                    tour.setAccessibility(cursor.getString(25));
                    tour.setAddress(cursor.getString(26));

                    result.add(tour);
                }while (cursor.moveToNext());
            }
            i++;
            cursor.close();
        }while (i< types.size());


        db.close();
        return result;
    }
    public ArrayList<Tour> getToursByTypeHome(Location myLocation,List<String> regions, List<String> types , String trackType, String  accessibility, String  suitableForChildren, int time, boolean weather){
        String countQuery = "SELECT *FROM " +TABLE_TOURS;
        if (trackType == null){trackType = "";}
        if (accessibility == null){accessibility = "";}
        if (suitableForChildren == null){suitableForChildren = "";}

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Tour> result = new ArrayList<>();
        String type = "";
        String region = "";
        int i = 0;
        int y = 0;

        do {
            if ((types.size() > 0) && (i == 0)){
                type = types.get(0);
            }
            else{ if (i>0){type = types.get(i);}}

            if ((regions.size() > 0) && (y == 0)){
                region = regions.get(0);
            }
            else{ if (y>0){region = regions.get(y);}}

            String[] columnNames = {"id","X","Y","Type","DifficultyLevel","TrackType","TrackDuration","TrackLength","AdmissionCharge","BestSeason","Precautions","ByAppointment","name","TypeRecreation","City","NearTo","OpeningHours","Parking","phone","Region","SuitableForChildren","URL","FullDescription","ProductUrl","PicUrl","Accessibility","Address"};

            Cursor cursor = db.query(TABLE_TOURS,new String[]{KEY_ID,KEY_X ,KEY_Y,KEY_TYPE,KEY_DIFFICULTY_LEVEL,KEY_TRACK_TYPE ,KEY_TRACK_DURATION,KEY_TRACK_LENGTH,KEY_ADMISSION_CHARGE,KEY_BEST_SEASON,KEY_PRECAUTION,KEY_BY_APPOINTMENT,KEY_NAME,KEY_TYPE_RECREATION,KEY_CITY,KEY_NEAR_TO,KEY_OPENING_HOURS, KEY_PARKING,KEY_PHONE ,KEY_REGION,KEY_SUITABLE_FOR_CHILDREN,KEY_URL, KEY_FULL_DESCRIPTION ,KEY_PRODUCT_URL,KEY_PIC_URL,KEY_ACCESSIBILITY,KEY_ADDRESS}, KEY_REGION + " LIKE ? AND " + KEY_TYPE + " LIKE ? AND " + KEY_ACCESSIBILITY + " LIKE ? AND " + KEY_SUITABLE_FOR_CHILDREN+ " LIKE ? AND " + KEY_TRACK_TYPE + " LIKE ? ",new String[]{"%"+region+"%","%"+type+"%","%"+accessibility+"%","%"+suitableForChildren+"%","%"+trackType+"%"},null,null,null,null);
//            String[] columnNames = cursor.getColumnNames();

            if (cursor.moveToFirst()){
                do{
                    Tour tour = new Tour();
                    if (cursor.getString(0) != null){
                        tour.setId(Integer.parseInt(cursor.getString(0)));
                    }
                    tour.setX(cursor.getDouble(1));
                    tour.setY(cursor.getDouble(2));
                    tour.setType(cursor.getString(3));
                    tour.setDifficultyLevel(cursor.getString(4));
                    tour.setTrackType(cursor.getString(5));
                    tour.setTrackDuration(cursor.getString(6));
                    tour.setTrackLength(cursor.getString(7));
                    tour.setAdmissionCharge(cursor.getString(8));
                    tour.setBestSeason(cursor.getString(9));
                    tour.setPrecautions(cursor.getString(10));
                    tour.setByAppointment(cursor.getString(11));
                    tour.setName(cursor.getString(12));
                    tour.setTypeRecreation(cursor.getString(13));
                    tour.setCity(cursor.getString(14));
                    tour.setNearTo(cursor.getString(15));
                    tour.setOpeningHours(cursor.getString(16));
                    tour.setParking(cursor.getString(17));
                    tour.setPhone(cursor.getString(18));
                    tour.setRegion(cursor.getString(19));
                    tour.setSuitableForChildren(cursor.getString(20));
                    tour.setURL(cursor.getString(21));
                    tour.setFullDescription(cursor.getString(22));
                    tour.setProductUrl(cursor.getString(23));
                    tour.setPicUrl(cursor.getString(24));
                    tour.setAccessibility(cursor.getString(25));
                    tour.setAddress(cursor.getString(26));
                    if (tour.getY() != 0) {
//                        String origin = "origin=" + myLocation.getLatitude() + "," + myLocation.getLongitude();
//                        String dest = "destination=" + tour.getY() + "," + tour.getX();
//                        String mode = "mode=driving";
//                        String parameters = origin + "&" + dest + "&" + mode;
//                        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters + "&key=AIzaSyCQDu85xwg8XUUUzVKd9ZJ6iYnHa_amObA";
//                        System.out.println("url "+url);
//                        readJson2(url);
                        Location loc1 = new Location("");
                        loc1.setLatitude(tour.getY());
                        loc1.setLongitude(tour.getX());
                        float distanceInMeters = loc1.distanceTo(myLocation);
                        float distanceInKM = distanceInMeters/ 1000;
                        float timeDrive = distanceInKM * 4 / 60;
                        if (time == 0){
                            time = 6;
                        }
                        if (time >= timeDrive){
                            if (!weather) {
                                result.add(tour);


                            } else {
                                Boolean weatherCity = findWeather(tour.getCity());
                                if ((weatherCity) || (weatherCity == null)) {

                                    result.add(tour);



                                }

                            }
                        }
//                        origin = myLocation.getLatitude() + "," + myLocation.getLongitude();
//                         dest = tour.getY() + "," + tour.getX();
//                        TourRating b = new TourRating();
//                        b.doInBackground();
//                        gettttt();

//                        try {
//                            System.out.println(getDriveDist(origin, dest));
//                        } catch (ApiException e) {
//                            System.out.println("ApiException");
//                        } catch (InterruptedException e) {
//                            System.out.println("InterruptedException");
//                        } catch (IOException e) {
//                            System.out.println("IOException");
//                        }
//                        int hours = readJson2(url);
//                                    int length = 0;
//                                    if (tour.getTrackLength() == null){
//                                        length = 0;
//                                    }
//                                    else {
//                                        length = tour.getTrackLength();
//                                    }
//                                    if ((hours + length)>= time){


                    }

                }while (cursor.moveToNext());
            }

            i++;
            if ((i >= types.size()) && (y < regions.size())){
                y++;
                i = 0;
            }

            cursor.close();
        }while ((i< types.size()) && (y< regions.size()));

        db.close();
        return result;
    }
//    private void read11111(String  origin , String dest){
////        GeoApiContext geoApiContext = new GeoApiContext.Builder()
////                .apiKey("AIzaSyCQDu85xwg8XUUUzVKd9ZJ6iYnHa_amObA")
////                .build();
////
////        // - Perform the actual request
////            System.out.println("#####_____");
////        DirectionsResult directionsResult = null;
////        try {
////            directionsResult = DirectionsApi.newRequest(geoApiContext)
////                    .mode(TravelMode.DRIVING)
////                    .origin(origin)
////                    .destination(dest)
////                    .await();
////            // - Parse the result
////            DirectionsRoute route = directionsResult.routes[0];
////            DirectionsLeg leg = route.legs[0];
////            Duration duration = leg.duration;
////            System.out.println("++++++++++++ "+duration.humanReadable);
////
////        } catch (ApiException e) {
////            e.printStackTrace();
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//        GeoApiContext DirectionsApi = new GeoApiContext.Builder()
//                .apiKey("AIzaSyCQDu85xwg8XUUUzVKd9ZJ6iYnHa_amObA")
//                .build();
//        DirectionsResult directionsResult = DirectionsApi.newRequest(DirectionsApi);
////        DirectionsApiRequest apiRequest = DirectionsApi.newRequest(context);
//        apiRequest.origin(new com.google.maps.model.LatLng(latitude, longitude));
//        apiRequest.destination(new com.google.maps.model.LatLng(latitude, longitude));
//        apiRequest.mode(TravelMode.DRIVING); //set travelling mode
//
//        apiRequest.setCallback(new com.google.maps.PendingResult.Callback<DirectionsResult>() {
//            @Override
//            public void onResult(DirectionsResult result) {
//                DirectionsRoute[] routes = result.routes;
//
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//
//            }
//        });

//    private  void  ggggggg(){
//        RequestParams rp = new RequestParams();
//        rp.add("Host", "api.traveltimeapp.com");
//        rp.add("Accept", "application/json");
//        rp.add("X-Application-Id", "9e687cfe");
//        rp.add("X-Api-Key", "6cb702dd06643a706595048be03f5e50");
//
//
//
//        HttpUtils.get( "/v4/geocoding/search?query=Parliament square&focus.lat=51.512539&focus.lng=-0.097541", rp, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String response) {
//                super.onSuccess(statusCode, headers, response);
//
//
//
//                // If the response is JSONObject instead of expected JSONArray
//                System.out.println("---------------- this is response : " + response);
////                Log.d("asd", "---------------- this is response : " + response);
//                try {
//                    JSONObject serverResp = new JSONObject(response.toString());
//                    System.out.println("serverResp "+serverResp);
//                } catch (JSONException e) {
//                    System.out.println("e.printStackTrace(); ");
//
//                }
//            }
//
//
//        });
//    }
//    private void gettttt(){
//
//
//        RequestParams rp = new RequestParams();
//        rp.add("Host", "api.traveltimeapp.com");
//        rp.add("Content-Type", "application/json");
//        rp.add("Accept", "application/json");
//        rp.add("X-Application-Id", "9e687cfe");
//        rp.add("X-Api-Key", "6cb702dd06643a706595048be03f5e50");
//        String  json = "{\n" +
//                "  \"locations\": [\n" +
//                "    {\n" +
//                "      \"id\": \"London center\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.508930,\n" +
//                "        \"lng\": -0.131387\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"Manchester\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 53.459790,\n" +
//                "        \"lng\": -2.216907\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"ZSL London Zoo\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.536067,\n" +
//                "        \"lng\": -0.153596\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"departure_searches\": [\n" +
//                "    {\n" +
//                "      \"id\": \"forward search example\",\n" +
//                "      \"departure_location_id\": \"London center\",\n" +
//                "      \"arrival_location_ids\": [\n" +
//                "        \"Manchester\",\n" +
//                "        \"ZSL London Zoo\"\n" +
//                "      ],\n" +
//                "      \"transportation\": {\n" +
//                "        \"type\": \"cycling\"\n" +
//                "      },\n" +
//                "      \"departure_time\": \"2019-06-20T08:00:00Z\",\n" +
//                "      \"properties\": [\"travel_time\", \"distance\", \"route\"]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//
//        rp.put("", json);
//
//        HttpUtils.post( "/v4/routes", rp, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                // If the response is JSONObject instead of expected JSONArray
//                System.out.println("response " + response);
//            }
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
//                // Pull out the first event on the public timeline
//
//                try {
//                    JSONObject firstEvent = (JSONObject) timeline.get(0);
////                    String tweetText = firstEvent.getString("text");
//                    // Do something with the response
//                    System.out.println( "firstEvent "+firstEvent.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                System.out.println("onFailure : "+statusCode);
//            }
//
//        });
//    }
//    private void gettttt2() {
//
//
//        RequestParams rp = new RequestParams();
//        rp.add("Host", "api.traveltimeapp.com");
//        rp.add("Content-Type", "application/json");
//        rp.add("Accept", "application/json");
//        rp.add("X-Application-Id", "9e687cfe");
//        rp.add("X-Api-Key", "6cb702dd06643a706595048be03f5e50");
//        String json = "{\n" +
//                "  \"locations\": [\n" +
//                "    {\n" +
//                "      \"id\": \"London center\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.508930,\n" +
//                "        \"lng\": -0.131387\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"Manchester\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 53.459790,\n" +
//                "        \"lng\": -2.216907\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"ZSL London Zoo\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.536067,\n" +
//                "        \"lng\": -0.153596\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"departure_searches\": [\n" +
//                "    {\n" +
//                "      \"id\": \"forward search example\",\n" +
//                "      \"departure_location_id\": \"London center\",\n" +
//                "      \"arrival_location_ids\": [\n" +
//                "        \"Manchester\",\n" +
//                "        \"ZSL London Zoo\"\n" +
//                "      ],\n" +
//                "      \"transportation\": {\n" +
//                "        \"type\": \"cycling\"\n" +
//                "      },\n" +
//                "      \"departure_time\": \"2019-06-20T08:00:00Z\",\n" +
//                "      \"properties\": [\"travel_time\", \"distance\", \"route\"]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//
//        rp.put("", json);
//        StringEntity mHttpEntity;
//        HashMap headers = new HashMap();
//        headers.put("Host", "api.traveltimeapp.com");
//        headers.put("Content-Type", "application/json");
//        headers.put("Accept", "application/json");
//        headers.put("X-Application-Id", "9e687cfe");
//        headers.put("X-Api-Key", "6cb702dd06643a706595048be03f5e50");
//
////        Header [] headers2 = new Header[String];
////        headers2["Host"] = ("Host", "api.traveltimeapp.com");
//        try {
//            mHttpEntity = new StringEntity(json);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        HttpUtils.post2(context1, "/v4/routes" , mHttpEntity, "application/json", new AsyncHttpResponseHandler() {
////            @Override
////            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
////                System.out.println(responseBody.toString());
////            }
////
////            @Override
////            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
////                System.out.println(responseBody.toString());
////            }
////
////        });
//    }
//    private void getNew(){
//        String json = "{\n" +
//                "  \"locations\": [\n" +
//                "    {\n" +
//                "      \"id\": \"London center\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.508930,\n" +
//                "        \"lng\": -0.131387\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"Manchester\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 53.459790,\n" +
//                "        \"lng\": -2.216907\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"ZSL London Zoo\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.536067,\n" +
//                "        \"lng\": -0.153596\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"departure_searches\": [\n" +
//                "    {\n" +
//                "      \"id\": \"forward search example\",\n" +
//                "      \"departure_location_id\": \"London center\",\n" +
//                "      \"arrival_location_ids\": [\n" +
//                "        \"Manchester\",\n" +
//                "        \"ZSL London Zoo\"\n" +
//                "      ],\n" +
//                "      \"transportation\": {\n" +
//                "        \"type\": \"cycling\"\n" +
//                "      },\n" +
//                "      \"departure_time\": \"2019-06-20T08:00:00Z\",\n" +
//                "      \"properties\": [\"travel_time\", \"distance\", \"route\"]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, json);
//
//        OkHttpClient client = new OkHttpClient();
//
//
//        Request request = new Request.Builder()
//                .url("https://api.traveltimeapp.com/v4/routes")
//                .post(body)
//        .addHeader("Host", "api.traveltimeapp.com");
//        .addHeader("Content-Type", "application/json");
//        .addHeader("Accept", "application/json");
//        .addHeader("X-Application-Id", "9e687cfe");
//        .addHeader("X-Api-Key", "6cb702dd06643a706595048be03f5e50");//Notice this request has header if you don't need to send a header just erase this part
//                .build();
//
//        Call call = client.newCall(request);
//
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("HttpService", "onFailure() Request was: " + request);
//
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response r) throws IOException {
//                String response = r.body().string();
//
//                Log.e("response ", "onResponse(): " + response );
//            }
//
//
//        });
//    }
//    private void sendPost() {
//        String url;
////        JSONObject parameters;
//        url =  "https://api.traveltimeapp.com/v4/routes";
////        boolean requestResult = false;
////        InputStream inputStream = null;
////        String result = "";
////        try {
//
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//
//
////            json = parameters.toString();
//        String json = "{\n" +
//                "  \"locations\": [\n" +
//                "    {\n" +
//                "      \"id\": \"London center\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.508930,\n" +
//                "        \"lng\": -0.131387\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"Manchester\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 53.459790,\n" +
//                "        \"lng\": -2.216907\n" +
//                "      }\n" +
//                "    },\n" +
//                "    {\n" +
//                "      \"id\": \"ZSL London Zoo\",\n" +
//                "      \"coords\": {\n" +
//                "        \"lat\": 51.536067,\n" +
//                "        \"lng\": -0.153596\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"departure_searches\": [\n" +
//                "    {\n" +
//                "      \"id\": \"forward search example\",\n" +
//                "      \"departure_location_id\": \"London center\",\n" +
//                "      \"arrival_location_ids\": [\n" +
//                "        \"Manchester\",\n" +
//                "        \"ZSL London Zoo\"\n" +
//                "      ],\n" +
//                "      \"transportation\": {\n" +
//                "        \"type\": \"cycling\"\n" +
//                "      },\n" +
//                "      \"departure_time\": \"2019-06-20T08:00:00Z\",\n" +
//                "      \"properties\": [\"travel_time\", \"distance\", \"route\"]\n" +
//                "    }\n" +
//                "  ]\n" +
//                "}";
//
//
//        httpPost.setHeader("Host", "https://api.traveltimeapp.com");
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("X-Application-Id", "9e687cfe");
//        httpPost.setHeader("X-Api-Key", "6cb702dd06643a706595048be03f5e50");
//
//        try {
////            StringEntity se = new StringEntity(json);
//            httpPost.setEntity(new StringEntity(json));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
////        httpPost.setEntity(se);
//
//
////        HttpResponse httpResponse = null;
////        try {
////            HttpResponse response = httpclient.execute(httpPost);
////            System.out.println("response "+response);
////        } catch (IOException e) {
////            System.out.println(e);
////        }
//
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost2 = new HttpPost(url);
//        httpPost2.setHeader("Content-type", "application/json; charset=utf-8");
//        httpPost2.setHeader("Accept", "application/json");
//        httpPost2.setHeader("X-Application-Id", "9e687cfe");
//        httpPost2.setHeader("X-Api-Key", "6cb702dd06643a706595048be03f5e50");
////        httpPost.setEntity(httpEntity);
//        try {
//            HttpResponse httpResponse = httpClient.execute(httpPost2);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        System.out.println("httpResponse "+httpResponse);
////            inputStream = httpResponse.getEntity().getContent();
////            System.out.println("inputStream   inputStream "+inputStream);
////            if (inputStream != null) {
////                System.out.println("inputStreaminputStream "+inputStream.toString() );
////                result = inputStream.toString();
////                requestResult = true;
////            } else {
////                result = "Did not work!";
////                requestResult = false;
////            }
////            System.out.println(result);
////        } catch (Exception e) {
////            Log.d("InputStream", e.getLocalizedMessage());
////            requestResult = false;
////        }
//    }
        private long getDriveDist(String  origin , String dest) throws ApiException, InterruptedException, IOException{


            //set up key
            GeoApiContext distCalcer = new GeoApiContext.Builder()
                    .apiKey("AIzaSyDNGt0CbB_y1-UIl0DQ_CWPcROrr2EE9QI")
                    .build();

            DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer);
            DistanceMatrix result = req.origins(origin)
                    .destinations(dest)
                    .mode(TravelMode.DRIVING)
                    .avoid(com.google.maps.DirectionsApi.RouteRestriction.TOLLS)
                    .language("en-US")
                    .await();


            long distApart = result.rows[0].elements[0].duration.inSeconds;
            System.out.println(distApart);
            return distApart;
        }
    private long getDriveDist2222(String  origin , String dest) throws ApiException, InterruptedException, IOException{


        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyDNGt0CbB_y1-UIl0DQ_CWPcROrr2EE9QI")
                .build();

        // - Perform the actual request
        DirectionsResult directionsResult = DirectionsApi.newRequest(geoApiContext)
                .mode(TravelMode.DRIVING)
                .origin(origin)
                .destination(dest)
                .await();

        System.out.println("directionsResult.toString() " + directionsResult.toString());

//        if (directionsResult.toString().equals("OVER_QUERY_LIMIT"))
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                    try {
                        sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

        };
        thread.start();

        return directionsResult.routes[0].legs[0].duration.inSeconds;

//        if (directionsResult.s.OVER_QUERY_LIMIT== "OVER_QUERY_LIMIT")
//        {
//        }
        // - Parse the result

    }

    private void read(String link){
        try {
            HttpURLConnection conn = null;
            StringBuilder jsonResults = new StringBuilder();
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            if (conn != null) {
                conn.disconnect();
            }

            JSONObject object = new JSONObject(jsonResults.toString());
            JSONArray routesArray = object.getJSONArray("routes");
            JSONObject routesObject = routesArray.getJSONObject(0);
            JSONArray legsArray = routesObject.getJSONArray("legs");
            JSONObject legsObject = legsArray.getJSONObject(0);
            JSONObject durationObject = legsObject.getJSONObject("duration");
            long timeTo = durationObject.getLong("value");
            int hours = (int) timeTo / 3600;
            // //           int temp = (int) timeTo- hours * 3600;
            // //                              int mins = temp / 60;
            System.out.println("hours " + hours);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private int readJson2(String url){
        try {
            URL url1 = new URL(url);
            URLConnection yc = url1.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
//            String inputLine;
//            InputStream is = url1.openConnection().getInputStream();
            StringBuffer buffer = new StringBuffer();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line+"\n");
            }
            System.out.println("buffer.toString() " +buffer.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private int readJson(String url){

        int hours = 0;
        try {
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

            Call call = client.newCall(request);
            try (okhttp3.Response response = call.execute()) {
                if (response.body() == null) return hours;
                String json = response.body().string();

                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray routesArray = jsonObject.getJSONArray("routes");
                    JSONObject routesObject = routesArray.getJSONObject(0);
                    JSONArray legsArray = routesObject.getJSONArray("legs");
                    JSONObject legsObject = legsArray.getJSONObject(0);
                    JSONObject durationObject = legsObject.getJSONObject("duration");
                    long timeTo = durationObject.getLong("value");
                    hours = (int) timeTo / 3600;
                    // //           int temp = (int) timeTo- hours * 3600;
                    // //                              int mins = temp / 60;
                    System.out.println("hours " + hours);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return hours;





    }
//<!--בשביל המפה לא בטוח שצריך-->
    private boolean isServiceGoogleMapOK(Context context){
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context);
        if (available == ConnectionResult.SUCCESS){
            System.out.println("true");
            return true;
        }else {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
                System.out.println("אפשר לתקן את הטעות הזאת");
            }else {
                System.out.println("אי אפשר לתקן");
            }
        }
        return false;
    }
    //<!--בשביל המפה לא בטוח שצריך-->
    private Boolean findWeather(String nameCity) {
        String api = null;
        final Boolean[] test = {null};
        CityWeatherSourceJson cityWeatherSourceJson;
        cityWeatherSourceJson = new CityWeatherSourceJson();
        List<WeatherJson> weatherJsons = cityWeatherSourceJson.doInBackground();
        for (WeatherJson weatherJson : weatherJsons) {
            if ((weatherJson.getName().equals(nameCity))){
                api = weatherJson.getApi();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, api, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject current = response.getJSONObject("current");
                            int temp = ((int) current.getDouble("temp_c"));
                            if ((temp >= 18) && (temp <= 35)){
                                test[0] = true;
                            }
                            else {
                                test[0] = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

            }
        }
        return test[0];
    }


//
//    private boolean checkMapServices(){
//        if(isServicesOK()){
//            if(isMapsEnabled()){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(context1);
//        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(enableGpsIntent, 9002);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }
//    public boolean isMapsEnabled(){
//        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
//        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
//            buildAlertMessageNoGps();
//            return false;
//        }
//        return true;
//    }
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(context1.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mLocationPermissionGranted = true;
//            getChatrooms();
//        } else {
//            ActivityCompat.requestPermissions(activity,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    9003);
//        }
//    }
//    public boolean isServicesOK(){
//        System.out.println("isServicesOK: checking google services version");
//        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context1);
//        if(available == ConnectionResult.SUCCESS){
////everything is fine and the user can make map requests
//            System.out.println("isServicesOK: Google Play Services is working");
//            return true;
//        }
//        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
////an error occured but we can resolve it
//            System.out.println("isServicesOK: an error occured but we can fix it");
//            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(activity, available, 9001);
//            dialog.show();
//        }else{
//            Toast.makeText(context1, "You can't make map requests", Toast.LENGTH_SHORT).show();
//        }
//        return false;
//    }
//
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        mLocationPermissionGranted = false;
//        switch (requestCode) {
//            case 9003: {
//// If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                }
//            }
//        }
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        System.out.println("onActivityResult: called.");
//        switch (requestCode) {
//            case 9002: {
//                if(mLocationPermissionGranted){
//                    getChatrooms();
//                }
//                else{
//                    getLocationPermission();
//                }
//            }
//        }
//    }
}

