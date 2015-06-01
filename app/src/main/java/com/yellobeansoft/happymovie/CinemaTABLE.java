package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by Jirawut-Jack on 14/01/2015.
 */
public class CinemaTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;
    private Context sContext;

    public static final String TABLE_CINEMA = "cinemaTABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "CinemaName";
    public static final String COLUMN_NAME_TH = "CinemaName_TH";
    public static final String COLUMN_BRAND = "Brand";
    public static final String COLUMN_SUB_BRAND = "SubBrand";
    public static final String COLUMN_ZONE = "Zone";
    public static final String COLUMN_PROVINCE = "Province";
    public static final String COLUMN_PHONE = "Phone";
    public static final String COLUMN_LAT = "Latitude";
    public static final String COLUMN_LONG = "Longitude";
    public static final String COLUMN_DIST = "Distance";

    //Constructor
    public CinemaTABLE(Context context) {
        sContext = context;
        objMyOpenHelper = new MyOpenHelper(context);
        readSQLite = objMyOpenHelper.getReadableDatabase();
        writeSQLite = objMyOpenHelper.getWritableDatabase();
    }//Constructor


    //getAllCinemas
    public ArrayList<Cinema> getAllCinemas() {
        return this.getAll("X");
    }//getAllCinemas

    //getAllCinemasByMovie
    public ArrayList<Cinema> getAllCinemasByMovie(String strMovie) throws ParseException {
        return this.getAllByMovie(strMovie, "X");
    }//getAllCinemas

    //getNearByCinemas
    public ArrayList<Cinema> getNearByCinemas() {

        ArrayList<Cinema> allCinemaList = new ArrayList<Cinema>();
        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();

        GPSTracker gps;

        try {

            gps = new GPSTracker(sContext);
            if (gps.canGetLocation()) {

                this.updateDistance(gps.getLocation());

                allCinemaList = this.getAll("NEAR");

                for (int i = 0; i < allCinemaList.size(); i++) {
                    Cinema objCinema = (Cinema) allCinemaList.get(i);
                    cinemaList.add(objCinema);

                    if (i == 4) {
                        i = allCinemaList.size() + 1;
                    }
                }

                return cinemaList;

            } else {
//            gps.showSettingsAlert();
                cinemaList = null;
                return cinemaList;
            }

        } catch (Exception e) {
            Log.d("CINEMA", "Error from getNearByCinemas => " + e.toString());
            cinemaList = null;
            return cinemaList;
        }

    }//getNearByCinemas


    //getNearByCinemasByMovie
    public ArrayList<Cinema> getNearByCinemasByMovie(String strMovie) {

        ArrayList<Cinema> allCinemaList = new ArrayList<Cinema>();
        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();

        GPSTracker gps;

        try {

            gps = new GPSTracker(sContext);
            if (gps.canGetLocation()) {
                this.updateDistance(gps.getLocation());
                allCinemaList = this.getAllByMovie(strMovie, "NEAR");

                for (int i = 0; i < allCinemaList.size(); i++) {
                    Cinema objCinema = (Cinema) allCinemaList.get(i);
                    cinemaList.add(objCinema);

                    if (i == 4) {
                        i = allCinemaList.size() + 1;
                    }
                }

                return cinemaList;

            } else {
//            gps.showSettingsAlert();
                cinemaList = null;
                return cinemaList;
            }

        } catch (Exception e) {
            Log.d("CINEMA", "Error from getNearByCinemas => " + e.toString());
            cinemaList = null;
            return cinemaList;
        }

    }//getNearByCinemasByMovie

    //getCinemaByMovie
    private ArrayList<Cinema> getAllByMovie (String strMovie,String strSortType) throws ParseException {
        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
        String strSort;

        if (strSortType.equals("NEAR")) {
            strSort = COLUMN_DIST + " ASC";
        } else {
            strSort = COLUMN_NAME + " ASC";
        }

        readSQLite = objMyOpenHelper.getReadableDatabase();

        String strQuery = "SELECT DISTINCT cinemaTABLE.* FROM cinemaTABLE LEFT OUTER JOIN showtimeTABLE on cinemaTABLE.CinemaName = showtimeTABLE.CinemaName "
                + "WHERE showtimeTABLE.movieTitle = '" + strMovie + "' ORDER BY " + strSort;
        Cursor objCursor = readSQLite.rawQuery(strQuery, null);

        if (objCursor.moveToFirst()) {

            do {
                ShowTimeTABLE objShowTab = new ShowTimeTABLE(sContext);
                    Cinema objCinema = new Cinema();
                    objCinema.setName(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_NAME)));
                    objCinema.setNameTH(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_NAME_TH)));
                    objCinema.setBrand(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_BRAND)));
                    objCinema.setGroup(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_SUB_BRAND)));
                    objCinema.setPhone(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                    objCinema.setLatitude(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LAT)));
                    objCinema.setLongtitude(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LONG)));
                    objCinema.setDistance(objCursor.getDouble(objCursor.getColumnIndexOrThrow(COLUMN_DIST)));
                    cinemaList.add(objCinema);

            } while (objCursor.moveToNext());

            if (objCursor != null && !objCursor.isClosed()) {
                objCursor.close();
            }
            readSQLite.close();
            return cinemaList;
        } else {
            cinemaList = null;
            return cinemaList;
        }

    }//getCinemaByMovie

    //getAll
    private ArrayList<Cinema> getAll(String strSortType) {

        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
        String strSort;

        if (strSortType.equals("NEAR")) {
            strSort = COLUMN_DIST + " ASC";
        } else {
            strSort = COLUMN_NAME + " ASC";
        }

        readSQLite = objMyOpenHelper.getReadableDatabase();

        Cursor objCursor = readSQLite.query(TABLE_CINEMA, new String[]{COLUMN_NAME, COLUMN_NAME_TH, COLUMN_BRAND, COLUMN_SUB_BRAND,
                COLUMN_PHONE, COLUMN_LAT, COLUMN_LONG, COLUMN_DIST}, null, null, null, null, strSort);

        if (objCursor.moveToFirst()) {

            do {
                Cinema objCinema = new Cinema();
                objCinema.setName(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_NAME)));
                objCinema.setNameTH(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_NAME_TH)));
                objCinema.setBrand(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_BRAND)));
                objCinema.setGroup(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_SUB_BRAND)));
                objCinema.setPhone(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_PHONE)));
                objCinema.setLatitude(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LAT)));
                objCinema.setLongtitude(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LONG)));
                objCinema.setDistance(objCursor.getDouble(objCursor.getColumnIndexOrThrow(COLUMN_DIST)));
                cinemaList.add(objCinema);
            } while (objCursor.moveToNext());

            if (objCursor != null && !objCursor.isClosed()) {
                objCursor.close();
            }
            readSQLite.close();
            return cinemaList;
        } else {
            cinemaList = null;
            readSQLite.close();
            return cinemaList;
        }

    }//getAll


    //updateDistance
    private void updateDistance(Location locMe) {
        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
        cinemaList = this.getAll("");
        LocationCheck objLocCheck = new LocationCheck(sContext);

        if (objLocCheck.shouldUpdateDistance(locMe)) {
            writeSQLite = objMyOpenHelper.getWritableDatabase();
            writeSQLite.beginTransaction();
            try {
                for (int i = 0; i < cinemaList.size(); i++) {
                    Cinema objCinema = (Cinema) cinemaList.get(i);

                    if (!objCinema.getLatitude().equals("null") && !objCinema.getLongtitude().equals("null")){
                        Location locCinema = new Location("Cinema");
                        locCinema.setLatitude(Double.parseDouble(objCinema.getLatitude()));
                        locCinema.setLongitude(Double.parseDouble(objCinema.getLongtitude()));

                        float distance = locMe.distanceTo(locCinema);
                        distance = distance / 1000;

                        writeSQLite.execSQL("UPDATE " + TABLE_CINEMA + " SET " + COLUMN_DIST + " = " + distance + " WHERE " +
                                COLUMN_NAME + " = '" + objCinema.getName() + "'");

                    }else {
                        writeSQLite.execSQL("UPDATE " + TABLE_CINEMA + " SET " + COLUMN_DIST + " = " + 99999999 + " WHERE " +
                                COLUMN_NAME + " = '" + objCinema.getName() + "'");
                    }
                }
                writeSQLite.setTransactionSuccessful();

            } catch (Exception e) {
            } finally {
                writeSQLite.endTransaction();
            }
        }

    }//updateDistance

    public void deleteAllCinema() {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        writeSQLite.delete(TABLE_CINEMA, null, null);
}

    public void addNewCinema(String strName, String strNameTH, String strPhone, String strBrand, String strGroup, String strLat, String strLong){
        try {
            writeSQLite = objMyOpenHelper.getWritableDatabase();
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(COLUMN_NAME, strName);
            objContentValues.put(COLUMN_NAME_TH, strNameTH);
            objContentValues.put(COLUMN_PHONE, strPhone);
            objContentValues.put(COLUMN_BRAND, strBrand);
            objContentValues.put(COLUMN_SUB_BRAND, strGroup);
            objContentValues.put(COLUMN_LAT, strLat);
            objContentValues.put(COLUMN_LONG, strLong);
            writeSQLite.insertOrThrow(TABLE_CINEMA, null, objContentValues);
            writeSQLite.close();
        } catch (Exception e){

        }
    }

    public void closeDB() {
        readSQLite.close();
        writeSQLite.close();
    }


    public boolean checkTableNotEmpty() {
        ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
        cinemaList = this.getAll("X");
        if (cinemaList != null) {
            return true;
        }
        return false;
    }

}
