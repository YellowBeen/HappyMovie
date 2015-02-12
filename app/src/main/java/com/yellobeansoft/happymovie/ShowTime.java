package com.yellobeansoft.happymovie;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jirawut-Jack on 23/01/2015.
 */
public class ShowTime {

    private String name;
    private String movieTitle;
    private String screen;
    private String date;
    private String Type;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    private String Time;
    private String nextTime;

    ArrayList<String> timeList;

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }

    private Integer timeID;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getTimeID() {
        return timeID;
    }

    public void setTimeID(Integer timeID) {
        this.timeID = timeID;
    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    //    public void setTimeList(ArrayList<String> timeList) {
    public void setTimeList() {
        ArrayList<String> aList= new ArrayList(Arrays.asList(this.getTime().split(",")));
        this.timeList = aList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
