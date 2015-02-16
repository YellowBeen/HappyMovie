package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.text.ParseException;
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
    public static final String COLUMN_SCREEN = "Screen";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_TIMEID = "Time_id";
    public static final String COLUMN_TYPE = "Type";

    public static final String TABLE_TIME = "timeTABLE";
    public static final String COLUMN_ITEM = "Item";
    public static final String COLUMN_TIME = "Time";

    //Constructor
    public ShowTimeTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        readSQLite = objMyOpenHelper.getReadableDatabase();
        writeSQLite = objMyOpenHelper.getWritableDatabase();
    }//Constructor

    public void deleteAllShowTime() {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        writeSQLite.delete(TABLE_SHOWTIME, null, null);
    }

    public Integer getRowCount(){
        String strQuery = "SELECT CinemaName FROM showtimeTABLE";
        Cursor objCursor = readSQLite.rawQuery(strQuery, null);
        return objCursor.getCount();
    }


    //addNewShowTime
    public void addNewShowTime(String strName, String strTitle, String strScreen, String strDate, Integer intTimeID, String strType, String strTime) throws IOException {
        try {
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(COLUMN_NAME, strName);
            objContentValues.put(COLUMN_TITLE, strTitle);
            objContentValues.put(COLUMN_SCREEN, strScreen);
            objContentValues.put(COLUMN_DATE, strDate);
            objContentValues.put(COLUMN_TIMEID, intTimeID);
            objContentValues.put(COLUMN_TYPE, strType);
            objContentValues.put(COLUMN_TIME, strTime);
            writeSQLite.insertOrThrow(TABLE_SHOWTIME, null, objContentValues);
        } catch (Exception e) {

        }
    }//addNewShowTime


    //getShowTimeByCinema
    public ArrayList<ShowTime> getShowTimeByCinema(String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();

//        String strQuery = "SELECT DISTINCT showtimeTABLE.* FROM showtimeTABLE INNER JOIN timeTABLE " +
//                          "ON showtimeTABLE.Time_id = timeTABLE.Time_id " +
//                          "WHERE showtimeTABLE.CinemaName = '" + strCinema + "'";

        String strQuery = "SELECT * FROM showtimeTABLE " +
                          "WHERE CinemaName = '" + strCinema + "'";

        Cursor objCursor = readSQLite.rawQuery(strQuery, null);

        if (objCursor.moveToFirst()) {

            do {
                ShowTime objShowTime = new ShowTime();
                objShowTime.setName(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_NAME)));
                objShowTime.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                objShowTime.setScreen(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_SCREEN)));
                objShowTime.setScreen(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_SCREEN)));
                objShowTime.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_DATE)));
                objShowTime.setType(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                objShowTime.setTimeID(objCursor.getInt(objCursor.getColumnIndexOrThrow(COLUMN_TIMEID)));
                objShowTime.setTime(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TIME)));
                objShowTime.setTimeList(strTime);

//                Cursor objCursorT = readSQLite.rawQuery("SELECT * FROM timeTABLE WHERE Time_id  = "
//                        + objShowTime.getTimeID() , null);
//
//                if (objCursorT.moveToFirst()) {
//                    ArrayList<String> timeList = new ArrayList<String>();
//                    do {
//                        timeList.add(objCursorT.getString(objCursorT.getColumnIndexOrThrow(COLUMN_TIME)));
//                    } while (objCursorT.moveToNext());
//
//                    if (objCursorT != null && !objCursorT.isClosed()) {
//                        objCursorT.close();
//                    }
//                    objShowTime.setTimeList(timeList);
//                } ;

                if (objShowTime.getTimeList().size() > 0){
                    showTimeList.add(objShowTime);
                }

            } while (objCursor.moveToNext());

            if (objCursor != null && !objCursor.isClosed()) {
                objCursor.close();
            }
            this.closeDB();
            return showTimeList;

        } else {
            showTimeList = null;
            this.closeDB();
            return showTimeList;
        }
    }//getShowTimeByCinema


    //getShowTimeByMovie
    public ArrayList<ShowTime> getShowTimeByMovie(String strMovie, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();

//        String strQuery = "SELECT DISTINCT showtimeTABLE.* FROM showtimeTABLE INNER JOIN timeTABLE " +
//                "ON showtimeTABLE.Time_id = timeTABLE.Time_id " +
//                "WHERE showtimeTABLE.movieTitle = '" + strMovie + "'";

        String strQuery = "SELECT * FROM showtimeTABLE " +
                          "WHERE movieTitle = '" + strMovie + "'";

        Cursor objCursor = readSQLite.rawQuery(strQuery, null);

        if (objCursor.moveToFirst()) {

            do {
                ShowTime objShowTime = new ShowTime();
                objShowTime.setName(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_NAME)));
                objShowTime.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                objShowTime.setScreen(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_SCREEN)));
                objShowTime.setScreen(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_SCREEN)));
                objShowTime.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_DATE)));
                objShowTime.setType(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                objShowTime.setTimeID(objCursor.getInt(objCursor.getColumnIndexOrThrow(COLUMN_TIMEID)));
                objShowTime.setTimeList(strTime);

//                Cursor objCursorT = readSQLite.rawQuery("SELECT * FROM timeTABLE WHERE Time_id  = "
//                        + objShowTime.getTimeID() , null);
//
//                if (objCursorT.moveToFirst()) {
//                    ArrayList<String> timeList = new ArrayList<String>();
//                    do {
//                        timeList.add(objCursorT.getString(objCursorT.getColumnIndexOrThrow(COLUMN_TIME)));
//                    } while (objCursorT.moveToNext());
//
//                    if (objCursorT != null && !objCursorT.isClosed()) {
//                        objCursorT.close();
//                    }
//                    objShowTime.setTimeList(timeList);
//                } ;

                if (objShowTime.getTimeList().size() > 0){
                    showTimeList.add(objShowTime);
                }

            } while (objCursor.moveToNext());

            if (objCursor != null && !objCursor.isClosed()) {
                objCursor.close();
            }
            this.closeDB();
            return showTimeList;
        } else {
            showTimeList = null;
            this.closeDB();
            return showTimeList;
        }
    }//getShowTimeByMovie


    public void closeDB() {
        writeSQLite.close();
        readSQLite.close();
    }



}
