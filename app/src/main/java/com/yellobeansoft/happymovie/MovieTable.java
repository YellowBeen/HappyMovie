package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


/**
 * Created by Jirawut-Jack on 14/01/2015.
 */
public class MovieTable {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public static final String TABLE_MOVIE = "movieTABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "movieTitle";
    public static final String COLUMN_TITLE_TH = "movieTitle_TH";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_LENGTH = "Length";
    public static final String COLUMN_INFO = "Url_Info";
    public static final String COLUMN_YOUTUBE = "Url_Youtube";
    public static final String COLUMN_RATING = "imdb_rating";
    public static final String COLUMN_IMDB = "imdb_url";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_RDATE = "Release_Date";
    public static final String COLUMN_SHOWC = "ShowTimeCount";

    public static final String PREFS_NAME = "CINEMA_APP";
    public static final String SHOWTIME = "MOVIE";
    //Constructor
    public MovieTable(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
    }//Constructor


    // getAllMovies
    public ArrayList<Movies> getAllMovies() throws ParseException {

        ArrayList<Movies> movieList = new ArrayList<Movies>();
        readSQLite = objMyOpenHelper.getReadableDatabase();

        Cursor objCursor = readSQLite.query(TABLE_MOVIE,
                new String[]{COLUMN_TITLE, COLUMN_TITLE_TH, COLUMN_IMAGE, COLUMN_LENGTH, COLUMN_INFO, COLUMN_YOUTUBE, COLUMN_RATING, COLUMN_IMDB, COLUMN_DATE, COLUMN_RDATE},
                null, null, null, null, null);

        if (objCursor.moveToFirst()) {
            do {
                Movies movies = new Movies();
                movies.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movies.setMovieTitleTH(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE_TH)));
                movies.setMovieImg(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                movies.setURLInfo(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_INFO)));
                movies.setURLTrailer(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_YOUTUBE)));
                movies.setMovieLength(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LENGTH)));
                movies.setRating(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_RATING)));
                movies.setURLIMDB(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMDB)));
                movies.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_DATE)));
                movies.setReleaseDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_RDATE)));
                movies.setIsNew();
                movieList.add(movies);
            } while (objCursor.moveToNext());
        }
        if (objCursor != null && !objCursor.isClosed()) {
            objCursor.close();
        }

        readSQLite.close();
        return movieList;
    } // getAllMovies


    // getMovies
    public Movies getMovies(String strName) throws ParseException {

        Movies movies = new Movies();
        readSQLite = objMyOpenHelper.getReadableDatabase();
        Cursor objCursor = readSQLite.rawQuery("SELECT * FROM movieTABLE WHERE movieTitle  = '"
                + strName + "'", null);

        if (objCursor.moveToFirst()) {
            do {
                movies.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movies.setMovieTitleTH(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE_TH)));
                movies.setMovieImg(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                movies.setURLInfo(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_INFO)));
                movies.setURLTrailer(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_YOUTUBE)));
                movies.setMovieLength(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LENGTH)));
                movies.setRating(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_RATING)));
                movies.setURLIMDB(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMDB)));
                movies.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_DATE)));
                movies.setReleaseDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_RDATE)));
                movies.setIsNew();
            } while (objCursor.moveToNext());
        }

        if (objCursor != null && !objCursor.isClosed()) {
            objCursor.close();
        }

        readSQLite.close();
        return movies;
    } // getMovies


    // getAllMoviesSortBy
    public ArrayList<Movies> getAllMoviesSortBy(String strSort) throws ParseException {

        ArrayList<Movies> movieList = new ArrayList<Movies>();
        readSQLite = objMyOpenHelper.getReadableDatabase();
        String strQuery = new String();

        if (strSort.equals("Date")) {
            strQuery = "SELECT * FROM movieTABLE ORDER BY DATETIME(Date) DESC, movieTitle ASC";
        } else if (strSort.equals("Rate")) {
            strQuery = "SELECT * FROM movieTABLE ORDER BY imdb_rating DESC, movieTitle ASC";
        } else if (strSort.equals("Name")) {
            strQuery = "SELECT * FROM movieTABLE ORDER BY movieTitle ASC";
        } else if (strSort.equals("Popular")) {
            strQuery = "SELECT * FROM movieTABLE ORDER BY ShowTimeCount DESC";
        } else {
            strQuery = "SELECT * FROM movieTABLE ORDER BY DATETIME(Date) DESC, movieTitle ASC";
        }

        Cursor objCursor = readSQLite.rawQuery(strQuery, null);

        if (objCursor.moveToFirst()) {
            do {
                Movies movies = new Movies();
                movies.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movies.setMovieTitleTH(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE_TH)));
                movies.setMovieImg(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                movies.setURLInfo(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_INFO)));
                movies.setURLTrailer(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_YOUTUBE)));
                movies.setMovieLength(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LENGTH)));
                movies.setRating(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_RATING)));
                movies.setURLIMDB(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMDB)));
                movies.setDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_DATE)));
                movies.setReleaseDate(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_RDATE)));
                movies.setIsNew();
                movieList.add(movies);
            } while (objCursor.moveToNext());
        }

        if (objCursor != null && !objCursor.isClosed()) {
            objCursor.close();
        }

        readSQLite.close();
        return movieList;
    } // getMovies


    public void deleteAllMovie() {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        writeSQLite.isDbLockedByCurrentThread();
        writeSQLite.delete(TABLE_MOVIE, null, null);
        writeSQLite.close();
    }


    public void addNewMovie(String strTitle, String strTitleTH, String strImage, String strLength, String strYoutube, String strRating, String strDate, String strIMDB, String strRDate, Integer intShowC) throws IOException {
        writeSQLite = objMyOpenHelper.getWritableDatabase();

        if (strRating.equals("n/A")) {
            strRating = " - ";
        }



        try {
            writeSQLite = objMyOpenHelper.getWritableDatabase();
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(COLUMN_TITLE, strTitle);
            objContentValues.put(COLUMN_TITLE_TH, strTitleTH);
            objContentValues.put(COLUMN_IMAGE, strImage);
            objContentValues.put(COLUMN_LENGTH, strLength);
            objContentValues.put(COLUMN_YOUTUBE, strYoutube);
            objContentValues.put(COLUMN_RATING, strRating);
            objContentValues.put(COLUMN_IMDB, strIMDB);
            objContentValues.put(COLUMN_DATE, strDate);
            objContentValues.put(COLUMN_RDATE, strRDate);
            objContentValues.put(COLUMN_SHOWC, intShowC);
            writeSQLite.insertOrThrow(TABLE_MOVIE, null, objContentValues);

            writeSQLite.close();
        } catch (Exception e) {
            if (writeSQLite.isOpen()){
                writeSQLite.close();
            }
        }


    }

}
