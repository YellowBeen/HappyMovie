package com.yellobeansoft.happymovie;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
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

    //Constructor
    public MovieTable(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
    }//Constructor


    // getAllMovies
    public ArrayList<Movies> getAllMovies() {

        ArrayList<Movies> movieList = new ArrayList<Movies>();
        readSQLite = objMyOpenHelper.getReadableDatabase();

        Cursor objCursor = readSQLite.query(TABLE_MOVIE,
                new String[]{COLUMN_TITLE, COLUMN_TITLE_TH, COLUMN_IMAGE, COLUMN_LENGTH, COLUMN_INFO, COLUMN_YOUTUBE},
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
                movieList.add(movies);
            } while (objCursor.moveToNext());
        }

        if (objCursor != null && !objCursor.isClosed()) {
            objCursor.close();
        }

        readSQLite.close();
        return movieList;
    } // getAllMovies


    public void deleteAllMovie() {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        writeSQLite.delete(TABLE_MOVIE, null, null);
        writeSQLite.close();
    }


    public void addNewMovie(String strTitle, String strTitleTH, String strImage, String strLength, String strYoutube) throws IOException {
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        try {
            ContentValues objContentValues = new ContentValues();
            objContentValues.put(COLUMN_TITLE, strTitle);
            objContentValues.put(COLUMN_TITLE_TH, strTitleTH);
            objContentValues.put(COLUMN_IMAGE, strImage);
            objContentValues.put(COLUMN_LENGTH, strLength);
            objContentValues.put(COLUMN_YOUTUBE, strYoutube);
            writeSQLite.insertOrThrow(TABLE_MOVIE, null, objContentValues);
        } catch (Exception e) {
            writeSQLite.close();
        }
        writeSQLite.close();

    }

}

