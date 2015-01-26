package com.yellobeansoft.happymovie;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/24/15 AD.
 */
public class CinemaGroup {

    private String cinemaGroup;
    private ArrayList<Cinema> cinema = new ArrayList<Cinema>();

    public CinemaGroup(String cinemaGroup, ArrayList<Cinema> cinema) {
        this.cinemaGroup = cinemaGroup;
        this.cinema = cinema;
    }

    public String getCinemaGroup() {
        return cinemaGroup;
    }

    public void setCinemaGroup(String cinemaGroup) {
        this.cinemaGroup = cinemaGroup;
    }

    public ArrayList<Cinema> getCinema() {
        return cinema;
    }

    public void setCinema(ArrayList<Cinema> cinema) {
        this.cinema = cinema;
    }
}
