package com.example.myprojectandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class CitiesDatabase extends SQLiteAssetHelper {
    private static final String DB_NAME = "CommunityAndCitiesSQLiteDB.db";
    private static final int DB_VER = 1;

    public CitiesDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    public List<City> getCities(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        String []sqlSelect = {"Id","Name","FullDescription","VendorName","ProductUrl","PicUrl","FAQ","Region","X","Y","Youtube"};
        String tableName = "CommunityAndCitiesSQLiteDB";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<City> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setName(cursor.getString(cursor.getColumnIndex("Name")));
                city.setX(cursor.getDouble(cursor.getColumnIndex("X")));
                city.setFullDescription(cursor.getString(cursor.getColumnIndex("FullDescription")));
                city.setY(cursor.getDouble(cursor.getColumnIndex("Y")));
                city.setVendorName(cursor.getString(cursor.getColumnIndex("VendorName")));
                city.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                city.setProductURL(cursor.getString(cursor.getColumnIndex("ProductUrl")));
                city.setPicURL(cursor.getString(cursor.getColumnIndex("PicUrl")));
                city.setFaq(cursor.getString(cursor.getColumnIndex("FAQ")));
                city.setRegion(cursor.getString(cursor.getColumnIndex("Region")));
                city.setYoutube(cursor.getString(cursor.getColumnIndex("Youtube")));
                result.add(city);
            }while (cursor.moveToNext());
        }
        cursor.close();
db.close();
        return result;
    }
    public List<String> getNames(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        String []sqlSelect = {"Name"};
        String tableName = "CommunityAndCitiesSQLiteDB";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,null);
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Name")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return result;
    }
    public List<City> getCitiesByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        String []sqlSelect = {"Id","Name","FullDescription","VendorName","ProductUrl","PicUrl","FAQ","Region","X","Y","Youtube"};
        String tableName = "CommunityAndCitiesSQLiteDB";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"Name LIKE ?",new String[]{"%"+name+"%"},null,null,null,null);
        List<City> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                City city = new City();
                city.setName(cursor.getString(cursor.getColumnIndex("Name")));
                city.setX(cursor.getDouble(cursor.getColumnIndex("X")));
                city.setFullDescription(cursor.getString(cursor.getColumnIndex("FullDescription")));
                city.setY(cursor.getDouble(cursor.getColumnIndex("Y")));
                city.setVendorName(cursor.getString(cursor.getColumnIndex("VendorName")));
                city.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                city.setProductURL(cursor.getString(cursor.getColumnIndex("ProductUrl")));
                city.setPicURL(cursor.getString(cursor.getColumnIndex("PicUrl")));
                city.setFaq(cursor.getString(cursor.getColumnIndex("FAQ")));
                city.setRegion(cursor.getString(cursor.getColumnIndex("Region")));
                city.setYoutube(cursor.getString(cursor.getColumnIndex("Youtube")));
                result.add(city);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return result;
    }
}
