package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jirawut-Jack on 23/01/2015.
 */
public class ShowTimeTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String TABLE_SHOWTIME = "showtimeTABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "CinemaName";
    public static final String COLUMN_TITLE = "movieTitle";
    public static final String COLUMN_SCREEN = "movieTitle";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_TIME = "Time";

    //Constructor
    public ShowTimeTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();
    }//Constructor

    public void deleteAllShowTime() {
        writeSQLite.delete(TABLE_SHOWTIME, null, null);
    }


    public void addNewShowTime(String strName, String strTitle, String strScreen, String strDate, String strTime) throws IOException {

        try {
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(COLUMN_NAME, strName);
            objContentValues.put(COLUMN_TITLE, strTitle);
            objContentValues.put(COLUMN_SCREEN, strScreen);
            objContentValues.put(COLUMN_DATE, strDate);
            objContentValues.put(COLUMN_TIME, strTime);
            writeSQLite.insertOrThrow(TABLE_SHOWTIME, null, objContentValues);
        } catch (Exception e) {

        }

    }



}
