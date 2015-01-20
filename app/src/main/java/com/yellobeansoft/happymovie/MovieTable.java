package com.yellobeansoft.happymovie;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String COLUMN_TITLE = "MovieTitle";
    public static final String COLUMN_TITLE_TH = "MovieTitle_TH";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_LENGTH = "Length";
    public static final String COLUMN_INFO = "Url_Info";
    public static final String COLUMN_YOUTUBE = "Url_Youtube";

    //Constructor
    public MovieTable(Context context) {
        objMyOpenHelper = new MyOpenHelper(context);
        writeSQLite = objMyOpenHelper.getWritableDatabase();
        readSQLite = objMyOpenHelper.getReadableDatabase();
    }//Constructor


    // getAllMovies
    public ArrayList<Movies> getAllMovies() {

        ArrayList<Movies> movieList = new ArrayList<Movies>();

        Cursor objCursor = readSQLite.query(TABLE_MOVIE,
                new String[]{COLUMN_TITLE, COLUMN_IMAGE, COLUMN_LENGTH, COLUMN_INFO, COLUMN_YOUTUBE},
                null, null, null, null, null);

        if (objCursor.moveToFirst()) {
            do {
                Movies movies = new Movies();
                movies.setMovieTitle(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                movies.setMovieTitleTH(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_TITLE_TH)));
                movies.setMovieImg(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_IMAGE)));
                movies.setURLInfo(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_INFO)));
                movies.setURLTrailer(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_YOUTUBE)));
                movies.setMovieLength(Integer.parseInt(objCursor.getString(objCursor.getColumnIndexOrThrow(COLUMN_LENGTH))));
                movieList.add(movies);
            } while (objCursor.moveToNext());
        }

        if (objCursor != null && !objCursor.isClosed()) {
            objCursor.close();
        }

        return movieList;
    } // getAllMovies

}
