package com.yellobeansoft.happymovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

/**
 * Created by jirawuts on 6/1/15 AD.
 */
public class LocationCheck {

    //Explicit
    public static final String PREFS_NAME = "CINEMA_APP";
    private Context sContext;


    //Constructor
    public LocationCheck(Context context) {
        sContext = context;
    }//Constructor


    // setLoc
    public void setLoc(Double dblLat, Double dblLong) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = sContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString("LAST_LAT", String.valueOf(dblLat));
        editor.commit();
        editor.putString("LAST_LONG", String.valueOf(dblLong));
        editor.commit();
    }// setLoc


    public Boolean shouldUpdateDistance(Location locMe) {

        Location locLast = getLastLoc();
        float distance = locMe.distanceTo(locLast);
        distance = distance / 1000;

        setLoc(locMe.getLatitude(),locMe.getLongitude());

        if (distance > 2) {
            return true;
        } else {
            return false;
        }

    }

    //getLastLoc
    private Location getLastLoc() {

        double dblLat = 0;
        double dblLong = 0;
        String strLat = "0";
        String strLong = "0";

        SharedPreferences settings;
        settings = sContext.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        strLat = settings.getString("LAST_LAT", "0");
        strLong = settings.getString("LAST_LONG", "0");

        dblLat = Double.valueOf(strLat);
        dblLong = Double.valueOf(strLong);

        Location locLast = new Location("LastLocation");
        locLast.setLatitude(dblLat);
        locLast.setLongitude(dblLong);

        return locLast;

    }//getLastLoc


}
