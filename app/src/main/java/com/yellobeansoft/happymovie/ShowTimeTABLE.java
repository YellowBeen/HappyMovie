package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    public static final String PREFS_NAME = "CINEMA_APP";
    public static final String SHOWTIME = "SHOWTIME";

    private Context sContext;

    //Constructor
    public ShowTimeTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        sContext = context;
        readSQLite = objMyOpenHelper.getReadableDatabase();
        writeSQLite = objMyOpenHelper.getWritableDatabase();
    }//Constructor

    public void deleteAllShowTime() {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        writeSQLite.delete(TABLE_SHOWTIME, null, null);
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


    public void addNewShowTimeJSON(ArrayList<ShowTime> showTimeList) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(showTimeList);
        editor.putString(SHOWTIME, jsonFavorites);
        editor.commit();
    }

    public void deleteShowTimeJSON() {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        showTimeList = null;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(showTimeList);
        editor.putString(SHOWTIME, jsonFavorites);
        editor.commit();
    }


    //getShowTimeByCinema
    public ArrayList<ShowTime> getShowTimeByCinemaX(String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();

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



    //getShowTimeByCinema
    public ArrayList<ShowTime> getShowTimeByCinema(String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
        List<ShowTime> ShowTimes;

        SharedPreferences settings;

        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SHOWTIME)) {
            String jsonFavorites = settings.getString(SHOWTIME, null);
            Gson gson = new Gson();
            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
                    ShowTime[].class);

            ShowTimes = Arrays.asList(ShowTimeItems);
            showTimeList = new ArrayList<ShowTime>(ShowTimes);
        } ;

        for (int i = 0; i < showTimeList.size(); i++) {
            ShowTime objShowTime = showTimeList.get(i);

            if (objShowTime.getName().equals(strCinema)) {
                objShowTime.setTimeList(strTime);
                if (objShowTime.getTimeList().size() > 0){
                    newList.add(objShowTime);
                }
            }

        }

        return newList;

    }//getShowTimeByCinema


    //getShowTimeByMovie
    public ArrayList<ShowTime> getShowTimeByMovieX(String strMovie, String strTime) throws ParseException {

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


    //getShowTimeByCinema
    public ArrayList<ShowTime> getShowTimeByMovie(String strMovie, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
        List<ShowTime> ShowTimes;

        SharedPreferences settings;

        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SHOWTIME)) {
            String jsonFavorites = settings.getString(SHOWTIME, null);
            Gson gson = new Gson();
            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
                    ShowTime[].class);

            ShowTimes = Arrays.asList(ShowTimeItems);
            showTimeList = new ArrayList<ShowTime>(ShowTimes);
        } ;

        for (int i = 0; i < showTimeList.size(); i++) {
            ShowTime objShowTime = showTimeList.get(i);

            if (objShowTime.getMovieTitle().equals(strMovie)) {
                objShowTime.setTimeList(strTime);
                if (objShowTime.getTimeList().size() > 0){
                    newList.add(objShowTime);
                }
            }

        }

        return newList;

    }//getShowTimeByCinema


    public void closeDB() {
        writeSQLite.close();
        readSQLite.close();
    }



}
