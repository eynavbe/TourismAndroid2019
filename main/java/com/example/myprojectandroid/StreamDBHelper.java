package com.example.myprojectandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class StreamDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TourMyLocationDistanceManager.db";
    public  static final String TABLE_TOURS = "StreamDBTourMyLocationDistance";
    public  static final int DB_VERSION = 1;
    private static final String KEY_Distance = "Distance";

    SQLiteDatabase database;

    public StreamDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        database = getWritableDatabase();

    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_TOURS + "("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "X REAL, "+
                "Y REAL, "+
                "Type TEXT, "+
                "DifficultyLevel TEXT, "+
                "TrackType TEXT, "+
                "TrackDuration TEXT, "+
                "TrackLength TEXT, "+
                "AdmissionCharge TEXT, "+
                "BestSeason TEXT, "+
                "Precautions TEXT, "+
                "ByAppointment TEXT, "+
                "Name TEXT, "+
                "TypeRecreation TEXT, "+
                "City TEXT, "+
                "NearTo TEXT, "+
                "OpeningHours TEXT, "+
                "Parking TEXT, "+
                "Phone TEXT, "+
                "Region TEXT, "+
                "SuitableForChildren TEXT, "+
                "URL TEXT, "+
                "FullDescription TEXT, "+
                "ProductUrl TEXT, "+
                "PicUrl TEXT, "+
                "Accessibility TEXT, "+
                "Address TEXT, "+
                "distance TEXT);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ TABLE_TOURS);
        onCreate(db);
    }

    public int getTourMyLocationDistanceCount(){
        String countQuery = "SELECT *FROM " +TABLE_TOURS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public void add(TourMyLocationDistance s) {
        Tour tour = s.getTour();
        ContentValues cv = new ContentValues();
        cv.put("X",tour.getX());
        cv.put("Y",tour.getY());
        cv.put("Type",tour.getType());
        cv.put("DifficultyLevel",tour.getDifficultyLevel());
        cv.put("TrackType",tour.getType());
        cv.put("TrackDuration",tour.getTrackDuration());
        cv.put("TrackLength",tour.getTrackLength());
        cv.put("AdmissionCharge",tour.getAdmissionCharge());
        cv.put("BestSeason",tour.getBestSeason());
        cv.put("Precautions",tour.getPrecautions());
        cv.put("ByAppointment",tour.getByAppointment());
        cv.put("Name",tour.getName());
        cv.put("TypeRecreation",tour.getTypeRecreation());
        cv.put("City",tour.getCity());
        cv.put("NearTo",tour.getNearTo());
        cv.put("OpeningHours",tour.getOpeningHours());
        cv.put("Parking",tour.getParking());
        cv.put("Phone",tour.getPhone());
        cv.put("Region",tour.getRegion());
        cv.put("SuitableForChildren",tour.getSuitableForChildren());
        cv.put("URL",tour.getURL());
        cv.put("FullDescription",tour.getFullDescription());
        cv.put("ProductUrl",tour.getProductUrl());
        cv.put("PicUrl",tour.getPicUrl());
        cv.put("Accessibility",tour.getAccessibility());
        cv.put("Address",tour.getAddress());
        cv.put("distance",s.getDistance());
        database.insert(TABLE_TOURS,null,cv);
    }

    public void cleanAllData(){
        database.execSQL("delete from "+ TABLE_TOURS);
    }

    public List<TourMyLocationDistance> getListTourMyLocationDistances(){
        List<TourMyLocationDistance> tourMyLocationDistancesList = new ArrayList<>();
        String selectQuery = "SELECT *FROM " + TABLE_TOURS;
//        SQLiteDatabase database = getWritableDatabase();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do{
                TourMyLocationDistance tourMyLocationDistance = new TourMyLocationDistance();
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
                tourMyLocationDistance.setTour(tour);
                tourMyLocationDistance.setDistance(cursor.getString(27));
                tourMyLocationDistancesList.add(tourMyLocationDistance);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return tourMyLocationDistancesList;
    }


    public List<TourMyLocationDistance> getToursSort(){

        List<TourMyLocationDistance> tourMyLocationDistancesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TOURS;
        SQLiteDatabase database = getWritableDatabase();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_TOURS,  new String[] {"id", "X","Y","Type","DifficultyLevel","TrackType","TrackDuration","TrackLength","AdmissionCharge","BestSeason", "Precautions","ByAppointment","Name","TypeRecreation","City","NearTo","OpeningHours","Parking","Phone","Region","SuitableForChildren", "URL","FullDescription","ProductUrl","PicUrl","Accessibility","Address","distance"},null,null,null,null,"distance"+" ASC");
//
        if (cursor.moveToFirst()){
            do{
                TourMyLocationDistance tourMyLocationDistance = new TourMyLocationDistance();
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
                tourMyLocationDistance.setTour(tour);
                tourMyLocationDistance.setDistance(cursor.getString(27));
                tourMyLocationDistancesList.add(tourMyLocationDistance);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return tourMyLocationDistancesList;

    }
}


































