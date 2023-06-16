package com.example.myprojectandroid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class VideoDatabase extends SQLiteAssetHelper {
    private static final String DB_NAME ="VideoSQLite.db";
    private static final int DB_VER = 1;
    public VideoDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }
    public List<Video> getVideos(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id","Name","City","Length","Pic"};
        String tableName = "Videos";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,"name"+" ASC");
        List<Video> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Video video = new Video();
                video.setCity(cursor.getString(cursor.getColumnIndex("City")));
                video.setId(cursor.getString(cursor.getColumnIndex("id")));
                video.setLength(cursor.getString(cursor.getColumnIndex("Length")));
                video.setTitle(cursor.getString(cursor.getColumnIndex("Name")));
                video.setPic(cursor.getString(cursor.getColumnIndex("Pic")));
                result.add(video);
            }while (cursor.moveToNext());
        }
        return result;
    }
    public List<String> getNames(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"Name"};
        String tableName = "Videos";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,null,null,null,null,"name"+" ASC");
        List<String> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                result.add(cursor.getString(cursor.getColumnIndex("Name")));
            }while (cursor.moveToNext());
        }
        return result;
    }
    public List<Video> getVideosByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id","Name","City","Length","Pic"};
        String tableName = "Videos";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"Name LIKE ?",new String[]{"%"+name+"%"},null,null,null);
        List<Video> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Video video = new Video();
                video.setCity(cursor.getString(cursor.getColumnIndex("City")));
                video.setId(cursor.getString(cursor.getColumnIndex("id")));
                video.setLength(cursor.getString(cursor.getColumnIndex("Length")));
                video.setTitle(cursor.getString(cursor.getColumnIndex("Name")));
                video.setPic(cursor.getString(cursor.getColumnIndex("Pic")));
                result.add(video);
            }while (cursor.moveToNext());
        }
        return result;
    }
    public List<Video> getVideosByCity(String city){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect = {"id","Name","City","Length","Pic"};
        String tableName = "Videos";
        qb.setTables(tableName);
        Cursor cursor = qb.query(db,sqlSelect,"City LIKE ?",new String[]{"%"+city+"%"},null,null,null);
        List<Video> result = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                Video video = new Video();
                video.setCity(cursor.getString(cursor.getColumnIndex("City")));
                video.setId(cursor.getString(cursor.getColumnIndex("id")));
                video.setLength(cursor.getString(cursor.getColumnIndex("Length")));
                video.setTitle(cursor.getString(cursor.getColumnIndex("Name")));
                video.setPic(cursor.getString(cursor.getColumnIndex("Pic")));
                result.add(video);
            }while (cursor.moveToNext());
        }
        return result;
    }
}





























