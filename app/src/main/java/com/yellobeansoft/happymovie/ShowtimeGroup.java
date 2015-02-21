package com.yellobeansoft.happymovie;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/24/15 AD.
 */
public class ShowtimeGroup {

    private Cinema mCinema;
    private ArrayList<ShowTime> showtime = new ArrayList<ShowTime>();

    public ShowtimeGroup(Cinema cinema, ArrayList<ShowTime> showtime) {
        this.mCinema = cinema;
        this.showtime = showtime;
    }

    public Cinema getShowtimeGroup() {
        return mCinema;
    }

    public void setShowtimeGroup(String showtimeGroup) {
        this.mCinema = mCinema;
    }

    public ArrayList<ShowTime> getShowtime() {
        return showtime;
    }

    public void setShowtime(ArrayList<ShowTime> showtime) {
        this.showtime = showtime;
    }}


