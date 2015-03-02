package com.yellobeansoft.happymovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {

    //Explicit
    private static final String DATABASE_NAME = "ShowTime.db";
    private static final int DATABASE_VERSION = 11;
    private static final String CREATE_MOVIE_TABLE = "create table movieTABLE (_id integer primary key, "+" movieTitle text, movieTitle_TH text, Image text, Length text, Url_Info text, Url_Youtube text, Date text, imdb_rating text, imdb_url text, Release_Date text, ShowTimeCount numeric);";
    private static final String CREATE_CINEMA_TABLE = "create table cinemaTABLE (_id integer primary key, "+" CinemaName text, CinemaName_TH text, Brand text, SubBrand text, Zone text, Province text, Phone text, Latitude text, Longitude text, Distance numeric);";
//    private static final String CREATE_SHOWTIME_TABLE = "create table showtimeTABLE (_id integer primary key, "+" CinemaName text, movieTitle text, Screen text, Date numeric, Time_id numeric, Type text, Time String);";

    public MyOpenHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MOVIE_TABLE);
//        db.execSQL(CREATE_SHOWTIME_TABLE);
        db.execSQL(CREATE_CINEMA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "movieTABLE");
        db.execSQL("DROP TABLE IF EXISTS " + "cinemaTABLE");
        db.execSQL("DROP TABLE IF EXISTS " + "showtimeTABLE");
        onCreate(db);
    }

}
