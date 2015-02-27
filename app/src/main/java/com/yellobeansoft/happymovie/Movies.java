package com.yellobeansoft.happymovie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Beboyz on 1/10/15 AD.
 */
public class Movies {

    private String movieImg;
    private String movieTitle;
    private String movieTitleTH;
    private String movieLength;
    private String showtime;
    private String rating;
    private String URLInfo;
    private String URLTrailer;
    private String URLIMDB;
    private String Date;
    private String ReleaseDate;
    private Boolean isNew;
    private Integer showtimeCount;

    public Integer getShowtimeCount() {
        return showtimeCount;
    }

    public void setShowtimeCount(Integer showtimeCount) {
        this.showtimeCount = showtimeCount;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strToday = dateFormat.format(new java.util.Date());
        long lDays = this.DATEDIFF(this.getDate(), strToday );

        if (lDays > 7) {
            this.isNew = false;
        } else {
            this.isNew = true;
        }
    }

    public String getReleaseDate() {
        return ReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }

    public String getURLIMDB() {
        return URLIMDB;
    }

    public void setURLIMDB(String URLIMDB) {
        this.URLIMDB = URLIMDB;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getMovieImg() {
        return movieImg;
    }

    public void setMovieImg(String movieImg) {
        this.movieImg = movieImg;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieTitleTH() {
        return movieTitleTH;
    }

    public void setMovieTitleTH(String movieTitleTH) {
        this.movieTitleTH = movieTitleTH;
    }

    public String getMovieLength() {
        return movieLength;
    }

    public void setMovieLength(String movieLength) {
        this.movieLength = movieLength;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getURLInfo() {
        return URLInfo;
    }

    public void setURLInfo(String URLInfo) {
        this.URLInfo = URLInfo;
    }

    public String getURLTrailer() {
        return URLTrailer;
    }

    public void setURLTrailer(String URLTrailer) {
        this.URLTrailer = URLTrailer;
    }

    private long DATEDIFF(String date1, String date2) {
        long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
        long days = 0l;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // "dd/MM/yyyy HH:mm:ss");

        Date dateIni = null;
        Date dateFin = null;
        try {
            dateIni = (Date) format.parse(date1);
            dateFin = (Date) format.parse(date2);
            days = (dateFin.getTime() - dateIni.getTime())/MILLISECS_PER_DAY;
        } catch (Exception e) {  e.printStackTrace();  }

        return days;
    }

}
