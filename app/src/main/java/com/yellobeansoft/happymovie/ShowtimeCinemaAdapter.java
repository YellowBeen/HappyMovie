package com.yellobeansoft.happymovie;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class ShowtimeCinemaAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<ShowTime> mShowtimeList;
    private ViewHolder mViewHolder;
    private MovieTable movieTable;
    private Movies objMovie;
    private String showtimeConcat;
    private String nextTime;
    private ArrayList<String> timeList = new ArrayList<String>();

    protected static int timeDigit = 5;

    public ShowtimeCinemaAdapter(Context context, ArrayList<ShowTime> lists) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mShowtimeList = lists;
    }

    @Override
    public int getCount() {
        return mShowtimeList.size();
    }// method getCount

    @Override
    public Object getItem(int position) {
        return mShowtimeList.get(position);
    }// method getItem

    @Override
    public long getItemId(int position) {
        return position;
    }// method getItemId

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.layout_showtime_cinema_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.movieName = (TextView) convertView.findViewById(R.id.txtDuration);
            mViewHolder.movieNameTH = (TextView) convertView.findViewById(R.id.txtMovieNameTH);
            mViewHolder.movieImg = (ImageView) convertView.findViewById(R.id.imgMovie);
            mViewHolder.movieRating = (TextView) convertView.findViewById(R.id.txtRating);
            mViewHolder.movieShowtime = (TextView) convertView.findViewById(R.id.txtShowtime);
            //mViewHolder.movieDate = (TextView) convertView.findViewById(R.id.txtDate);
            mViewHolder.screen = (TextView) convertView.findViewById(R.id.txtScreen);
            mViewHolder.showtimeType = (TextView) convertView.findViewById(R.id.txtType);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        // Get data from movie class
        movieTable = new MovieTable(mContext);
        try {
            objMovie = movieTable.getMovies(mShowtimeList.get(position).getMovieTitle());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Set image
        String path = objMovie.getMovieImg();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
        setShowtimeColor(mViewHolder.movieShowtime, showtimeConcat, nextTime, Color.GRAY, Color.RED, Color.BLACK);
        // Set Textview (Movie object)
        mViewHolder.movieName.setText(objMovie.getMovieTitle());
        mViewHolder.movieNameTH.setText(objMovie.getMovieTitleTH());
        mViewHolder.movieRating.setText(objMovie.getRating()+"/10");
        //mViewHolder.movieDate.setText(objMovie.getDate());
        // Set Textview (Showtime object)
        mViewHolder.showtimeType.setText(mShowtimeList.get(position).getType());
        mViewHolder.screen.setText(mShowtimeList.get(position).getScreen());
        imageLoader.get(path, ImageLoader.getImageListener(
                mViewHolder.movieImg, R.drawable.ic_loadmovie, R.drawable.ic_loadmovie));

        return convertView;
    }// method getView

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
        //str.setSpan(new ForegroundColorSpan(cColor), cTimePosition, cTimePosition+currentTime.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        public ImageView movieImg;
        public TextView movieName;
        public TextView movieNameTH;
        public TextView movieRating;
        public TextView movieShowtime;
        public TextView movieDate;
        public TextView showtimeType;
        public TextView screen;

    }// class ViewHolder

}
