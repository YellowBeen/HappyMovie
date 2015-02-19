package com.yellobeansoft.happymovie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Jirawut-Jack on 23/01/2015
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

    public String getNextTime() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String strNow = dateFormat.format(new Date());
        java.util.Date dNow =(java.util.Date)dateFormat.parse(strNow);
        ArrayList<String> time = this.getTimeList();

        for (int i = 0; i < time.size(); i++){
            java.util.Date dShowTime =(java.util.Date)dateFormat.parse(time.get(i));
            if (dShowTime.after(dNow)) {
                return time.get(i);
            }
        }

        return "XXX";
//        return nextTime;
    }

    public void setNextTime(String time) {
        nextTime = time;
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
    public void setTimeList(String strTime) throws ParseException {
        ArrayList<String> tList= new ArrayList(Arrays.asList(this.getTime().split(",")));
        ArrayList<String> yList= new ArrayList();

        if (strTime.equals("")){
            this.timeList = tList;
        }else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            java.util.Date dTime =(java.util.Date)dateFormat.parse(strTime);

            for (int i = 0; i < tList.size(); i++){
                java.util.Date dShowTime =(java.util.Date)dateFormat.parse(tList.get(i));
                if (dShowTime.after(dTime)) {
                    yList.add(tList.get(i));
                }
            }

            this.timeList = yList;
        }

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
