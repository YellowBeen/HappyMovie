package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Jirawut-Jack on 23/01/2015.
 */
public class TimeTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;
    public static final String TABLE_TIME = "timeTABLE";
    public static final String COLUMN_TIMEID = "Time_id";
    public static final String COLUMN_ITEM = "Item";
    public static final String COLUMN_TIME = "Time";

    //Constructor
    public TimeTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        readSQLite = objMyOpenHelper.getReadableDatabase();
        writeSQLite = objMyOpenHelper.getWritableDatabase();
    }//Constructor


    public void deleteAllTime() {

        writeSQLite.delete(TABLE_TIME, null, null);
    }

    //addNewTime
    public void addNewTime(Integer intID, Integer intItem, String strTime) {
        try {
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(COLUMN_TIMEID, intID);
            objContentValues.put(COLUMN_ITEM, intItem);
            objContentValues.put(COLUMN_TIME, strTime);
            writeSQLite.insertOrThrow(TABLE_TIME, null, objContentValues);
        } catch (Exception e) {

        }

    }//addNewTime

    public void closeDB() {
        writeSQLite.close();
        readSQLite.close();
    }





}
