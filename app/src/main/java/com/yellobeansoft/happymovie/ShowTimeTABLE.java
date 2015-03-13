package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//import com.google.common.collect.*;
//import com.google.common.base.Predicate;
import com.google.gson.Gson;

/**
 * Created by Jirawut-Jack on 23/01/2015.
 */
public class ShowTimeTABLE {

    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;
    private Context sContext;

    //Constructor
    public ShowTimeTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        sContext = context;
    }//Constructor


    public void deleteAllShowTime() {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        writeSQLite.isDbLockedByCurrentThread();
        writeSQLite.delete("showtimeTABLE", null, null);
        writeSQLite.close();
    }


    //getShowTimeByMovieCinema
    public ArrayList<ShowTime> getShowTimeByMovieCinema(final String strMovie, final String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();

        readSQLite = objMyOpenHelper.getReadableDatabase();

        String strQuery = "SELECT * FROM showtimeTABLE WHERE CinemaName = '"
                + strCinema + "' AND movieTitle = '" + strMovie + "'";

        Cursor objCursor = readSQLite.rawQuery(strQuery, null);

        if (objCursor.moveToFirst()) {
            do {
                ShowTime objShowTime = new ShowTime();
                objShowTime.setName(objCursor.getString(objCursor.getColumnIndexOrThrow("CinemaName")));
                objShowTime.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow("movieTitle")));
                objShowTime.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow("Date")));
                objShowTime.setScreen(objCursor.getString(objCursor.getColumnIndexOrThrow("Screen")));
                objShowTime.setTime(objCursor.getString(objCursor.getColumnIndexOrThrow("Time")));
                objShowTime.setTimeID(objCursor.getInt(objCursor.getColumnIndexOrThrow("Time_id")));
                objShowTime.setType(objCursor.getString(objCursor.getColumnIndexOrThrow("Type")));
                objShowTime.setTimeList(strTime);
                showTimeList.add(objShowTime);
            } while (objCursor.moveToNext());
        }

        readSQLite.close();

        return showTimeList;

//        for (int i = 0; i < showTimeList.size(); i++) {
//            ShowTime objShowTime = showTimeList.get(i);
//            objShowTime.setTimeList(strTime);
//            if (objShowTime.getTimeList().size() > 0) {
//                newList.add(objShowTime);
//            }
//        }
//
//        return newList;

    }//getShowTimeByMovieCinema


    //getShowTimeByMovieCinema
    public ArrayList<ShowTime> getShowTimeByCinema(final String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();

        readSQLite = objMyOpenHelper.getReadableDatabase();
        String strQuery = "SELECT * FROM showtimeTABLE WHERE CinemaName = '" + strCinema + "'" ;
        Cursor objCursor = readSQLite.rawQuery(strQuery, null);

        if (objCursor.moveToFirst()) {
            do {
                ShowTime objShowTime = new ShowTime();
                objShowTime.setName(objCursor.getString(objCursor.getColumnIndexOrThrow("CinemaName")));
                objShowTime.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow("movieTitle")));
                objShowTime.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow("Date")));
                objShowTime.setScreen(objCursor.getString(objCursor.getColumnIndexOrThrow("Screen")));
                objShowTime.setTime(objCursor.getString(objCursor.getColumnIndexOrThrow("Time")));
                objShowTime.setTimeID(objCursor.getInt(objCursor.getColumnIndexOrThrow("Time_id")));
                objShowTime.setType(objCursor.getString(objCursor.getColumnIndexOrThrow("Type")));
                objShowTime.setTimeList(strTime);
                showTimeList.add(objShowTime);
            } while (objCursor.moveToNext());
        }

        readSQLite.close();

        for (int i = 0; i < showTimeList.size(); i++) {
            ShowTime objShowTime = showTimeList.get(i);
            objShowTime.setTimeList(strTime);
            if (objShowTime.getTimeList().size() > 0) {
                newList.add(objShowTime);
            }
        }

        return newList;
    }//getShowTimeByMovieCinema



//    public void addNewShowTimeJSON(ArrayList<ShowTime> showTimeList, String strCinema ) {
//        SharedPreferences settings;
//        SharedPreferences.Editor editor;
//        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        editor = settings.edit();
//        Gson gson = new Gson();
//        String jsonFavorites = gson.toJson(showTimeList);
////        editor.putString(SHOWTIME, jsonFavorites);
//        editor.putString(strCinema, jsonFavorites);
//        editor.apply();
//    }

//    public void deleteShowTimeJSON() {
//        SharedPreferences settings;
//        SharedPreferences.Editor editor;
//        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
//        showTimeList = null;
//        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        editor = settings.edit();
//
//        editor.clear();
//        editor.commit();
//
////        Gson gson = new Gson();
////        String jsonFavorites = gson.toJson(showTimeList);
////        editor.putString(SHOWTIME, jsonFavorites);
////        editor.commit();
//    }
//
//    //getShowTimeByCinema
//    public ArrayList<ShowTime> getShowTimeByCinemaJASON(final String strCinema, String strTime) throws ParseException {
//
//        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
//        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
//        List<ShowTime> ShowTimes;
//        Collection<ShowTime> MyShowTimes;
//        SharedPreferences settings;
//
//        settings = sContext.getSharedPreferences(PREFS_NAME,
//                Context.MODE_PRIVATE);
//
////        if (settings.contains(SHOWTIME)) {
//        if (settings.contains(strCinema)) {
////            String jsonFavorites = settings.getString(SHOWTIME, null);
//            String jsonFavorites = settings.getString(strCinema, null);
//            Gson gson = new Gson();
//            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
//                    ShowTime[].class);
//
//            ShowTimes = Arrays.asList(ShowTimeItems);
//
//            Predicate<ShowTime> predicate = new Predicate<ShowTime>() {
//                @Override
//                public boolean apply(ShowTime input) {
//                    return input.getName().equals(strCinema);
//                }
//            };
//
//            MyShowTimes = Collections2.filter(ShowTimes, predicate);
//            showTimeList = new ArrayList<ShowTime>(MyShowTimes);
//        }
//
//        for (int i = 0; i < showTimeList.size(); i++) {
//            ShowTime objShowTime = showTimeList.get(i);
//            objShowTime.setTimeList(strTime);
//            if (objShowTime.getTimeList().size() > 0) {
//                newList.add(objShowTime);
//            }
//        }
//
//        return newList;
//
//    }//getShowTimeByCinema
//
//
//    //getShowTimeByMovieCinema
//    public ArrayList<ShowTime> getShowTimeByMovieCinemaJSON(final String strMovie, final String strCinema, String strTime) throws ParseException {
//
//        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
//        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
//        List<ShowTime> ShowTimes;
//        Collection<ShowTime> MyShowTimes;
//
//        SharedPreferences settings;
//
//        settings = sContext.getSharedPreferences(PREFS_NAME,
//                Context.MODE_PRIVATE);
//
////        if (settings.contains(SHOWTIME)) {
//        if (settings.contains(strCinema)) {
////            String jsonFavorites = settings.getString(SHOWTIME, null);
//            String jsonFavorites = settings.getString(strCinema, null);
//            Gson gson = new Gson();
//            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
//                    ShowTime[].class);
//
//            ShowTimes = Arrays.asList(ShowTimeItems);
//
//            Predicate<ShowTime> predicate = new Predicate<ShowTime>() {
//                @Override
//                public boolean apply(ShowTime input) {
////                    return ( input.getName().equals(strCinema) && input.getMovieTitle().equals(strMovie));
//                    return ( input.getMovieTitle().equals(strMovie));
//                }
//            };
//
//            MyShowTimes = Collections2.filter(ShowTimes, predicate);
//            showTimeList = new ArrayList<ShowTime>(MyShowTimes);
//        };
//
//        for (int i = 0; i < showTimeList.size(); i++) {
//            ShowTime objShowTime = showTimeList.get(i);
//            objShowTime.setTimeList(strTime);
//            if (objShowTime.getTimeList().size() > 0) {
//                newList.add(objShowTime);
//            }
//        }
//
//        return newList;
//
//    }//getShowTimeByMovieCinema
//
//
//    //getShowTimeByCinema
//    public boolean checkCinemaShowTime(final String strCinema,final String strMovie) throws ParseException {
//
//        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
//        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
//        List<ShowTime> ShowTimes;
//        Collection<ShowTime> MyShowTimes;
//
//        SharedPreferences settings;
//
//        settings = sContext.getSharedPreferences(PREFS_NAME,
//                Context.MODE_PRIVATE);
//
////        if (settings.contains(SHOWTIME)) {
//        if (settings.contains(strCinema)) {
////            String jsonFavorites = settings.getString(SHOWTIME, null);
//            String jsonFavorites = settings.getString(strCinema, null);
//            Gson gson = new Gson();
//            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
//                    ShowTime[].class);
//
//            ShowTimes = Arrays.asList(ShowTimeItems);
//
//            Predicate<ShowTime> predicate = new Predicate<ShowTime>() {
//                @Override
//                public boolean apply(ShowTime input) {
////                    return (input.getName().equals(strCinema) && input.getMovieTitle().equals(strMovie));
//                    return (input.getMovieTitle().equals(strMovie));
//                }
//            };
//
//            MyShowTimes = Collections2.filter(ShowTimes, predicate);
//            showTimeList = new ArrayList<ShowTime>(MyShowTimes);
//
//            if (MyShowTimes.size() > 0) {
//                return true;
//            } else {
//                return false;
//            }
//
//        } ;
//
//        return false;
//
//    }//getShowTimeByCinema

}
