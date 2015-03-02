package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.*;
import com.google.common.base.Predicate;
import com.google.gson.Gson;

/**
 * Created by Jirawut-Jack on 23/01/2015.
 */
public class ShowTimeTABLE {

    public static final String PREFS_NAME = "CINEMA_APP";
    public static final String SHOWTIME = "SHOWTIME";
    private Context sContext;


    //Constructor
    public ShowTimeTABLE(Context context) {
        sContext = context;
    }//Constructor


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
    public ArrayList<ShowTime> getShowTimeByCinema(final String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
        List<ShowTime> ShowTimes;
        Collection<ShowTime> MyShowTimes;
        SharedPreferences settings;

        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SHOWTIME)) {
            String jsonFavorites = settings.getString(SHOWTIME, null);
            Gson gson = new Gson();
            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
                    ShowTime[].class);

            ShowTimes = Arrays.asList(ShowTimeItems);

            Predicate<ShowTime> predicate = new Predicate<ShowTime>() {
                @Override
                public boolean apply(ShowTime input) {
                    return input.getName().equals(strCinema);
                }
            };

            MyShowTimes = Collections2.filter(ShowTimes, predicate);
            showTimeList = new ArrayList<ShowTime>(MyShowTimes);
        }

        for (int i = 0; i < showTimeList.size(); i++) {
            ShowTime objShowTime = showTimeList.get(i);
            objShowTime.setTimeList(strTime);
            if (objShowTime.getTimeList().size() > 0) {
                newList.add(objShowTime);
            }
        }

        return newList;

    }//getShowTimeByCinema


    //getShowTimeByMovieCinema
    public ArrayList<ShowTime> getShowTimeByMovieCinema(final String strMovie, final String strCinema, String strTime) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
        List<ShowTime> ShowTimes;
        Collection<ShowTime> MyShowTimes;

        SharedPreferences settings;

        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SHOWTIME)) {
            String jsonFavorites = settings.getString(SHOWTIME, null);
            Gson gson = new Gson();
            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
                    ShowTime[].class);

            ShowTimes = Arrays.asList(ShowTimeItems);

            Predicate<ShowTime> predicate = new Predicate<ShowTime>() {
                @Override
                public boolean apply(ShowTime input) {
                    return ( input.getName().equals(strCinema) && input.getMovieTitle().equals(strMovie));
                }
            };

            MyShowTimes = Collections2.filter(ShowTimes, predicate);
            showTimeList = new ArrayList<ShowTime>(MyShowTimes);
        };

        for (int i = 0; i < showTimeList.size(); i++) {
            ShowTime objShowTime = showTimeList.get(i);
            objShowTime.setTimeList(strTime);
            if (objShowTime.getTimeList().size() > 0) {
                newList.add(objShowTime);
            }
        }

        return newList;

    }//getShowTimeByMovieCinema


    //getShowTimeByCinema
    public boolean checkCinemaShowTime(final String strCinema,final String strMovie) throws ParseException {

        ArrayList<ShowTime> showTimeList = new ArrayList<ShowTime>();
        ArrayList<ShowTime> newList = new ArrayList<ShowTime>();
        List<ShowTime> ShowTimes;
        Collection<ShowTime> MyShowTimes;

        SharedPreferences settings;

        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(SHOWTIME)) {
            String jsonFavorites = settings.getString(SHOWTIME, null);
            Gson gson = new Gson();
            ShowTime[] ShowTimeItems = gson.fromJson(jsonFavorites,
                    ShowTime[].class);

            ShowTimes = Arrays.asList(ShowTimeItems);

            Predicate<ShowTime> predicate = new Predicate<ShowTime>() {
                @Override
                public boolean apply(ShowTime input) {
                    return (input.getName().equals(strCinema) && input.getMovieTitle().equals(strMovie));
                }
            };

            MyShowTimes = Collections2.filter(ShowTimes, predicate);
            showTimeList = new ArrayList<ShowTime>(MyShowTimes);

            if (MyShowTimes.size() > 0) {
                return true;
            } else {
                return false;
            }

        } ;

        return false;

    }//getShowTimeByCinema

}
