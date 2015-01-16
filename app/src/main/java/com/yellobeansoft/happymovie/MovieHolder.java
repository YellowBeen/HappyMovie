package com.yellobeansoft.happymovie;

import android.graphics.drawable.Drawable;

/**
 * Created by Beboyz on 1/10/15 AD.
 */
public class MovieHolder {

    private Drawable movieImg;
    private String movieTitle;
    private String rating;
    private String movieLength;

    public Drawable getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(Drawable movieImg) {
        this.movieImg = movieImg;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(String movieLength) {
        this.movieLength = movieLength;
    }
}
