package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class ShowtimeMovieCinemaAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<String> timeList = new ArrayList<String>();
    private String showtimeConcat;
    private String nextTime;
    ArrayList<ShowTime> mShowtimeList;
    private ViewHolder mViewHolder;
    public ShowtimeMovieCinemaAdapter mCinemaAdapter;
    public ShowtimeMovieCinemaAdapter(Context context, ArrayList<ShowTime> showtimeLists) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mShowtimeList = showtimeLists;
        mCinemaAdapter = this;
    }

    public ShowtimeMovieCinemaAdapter getmCinemaAdapter() {
        return mCinemaAdapter;
    }

    @Override
    public int getCount() {
        return mShowtimeList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.layout_showtime_movie_cinema_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.txtScreen = (TextView) convertView.findViewById(R.id.txtScreen);
            mViewHolder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            mViewHolder.txtShowtime = (TextView) convertView.findViewById(R.id.txtShowtime);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();

        }

        mViewHolder.txtScreen.setText("Screen: "+mShowtimeList.get(position).getScreen());
        mViewHolder.txtType.setText(mShowtimeList.get(position).getType());

// Concatenate showtime
        timeList = mShowtimeList.get(position).getTimeList();
        showtimeConcat = JoinArray(timeList, "   ");

        Spannable showtimeSpan = new SpannableString(showtimeConcat);
        showtimeSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        try {
            nextTime = mShowtimeList.get(position).getNextTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //mViewHolder.movieShowtime.setText(showtimeSpan);
        setShowtimeColor(mViewHolder.txtShowtime, showtimeConcat, nextTime, Color.GRAY, Color.RED, Color.BLACK);

        return convertView;
    }

    private void setShowtimeColor(TextView view, String fullShowtime, String currentTime, int eColor, int cColor, int lColor) {

        SpannableStringBuilder builder = new SpannableStringBuilder();
        if (!currentTime.equalsIgnoreCase("XXX")) {
            int cTimePosition = fullShowtime.indexOf(currentTime);
            int lTimePosition = cTimePosition + currentTime.length();
            int showtimeLength = fullShowtime.length();
            String earlyTime = fullShowtime.substring(0, cTimePosition);
            String lateTime = fullShowtime.substring(lTimePosition, showtimeLength);

            if (!earlyTime.equalsIgnoreCase(null)) {
                SpannableString earlyTimeSpannable = new SpannableString(earlyTime);
                earlyTimeSpannable.setSpan(new ForegroundColorSpan(eColor), 0, earlyTime.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(earlyTimeSpannable);
            }

            SpannableString currentTimeSpannable = new SpannableString(currentTime);
            currentTimeSpannable.setSpan(new ForegroundColorSpan(cColor), 0, currentTime.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.append(currentTimeSpannable);

            if (!lateTime.equalsIgnoreCase(null)) {
                SpannableString lateTimeSpannable = new SpannableString(lateTime);
                lateTimeSpannable.setSpan(new ForegroundColorSpan(lColor), 0, lateTime.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(lateTimeSpannable);
            }
            view.setText(builder, TextView.BufferType.SPANNABLE);
        }else{
            view.setText(fullShowtime);
        }

    }

    public static String JoinArray(List<String> list, String delim) {

        StringBuilder sb = new StringBuilder();

        String loopDelim = "";

        for(String s : list) {

            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }

        return sb.toString();
    }// JoinArray

    private static class ViewHolder {
        public TextView txtScreen;
        public TextView txtType;
        public TextView txtShowtime;
    }// class ViewHolder

}
