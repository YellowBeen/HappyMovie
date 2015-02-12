package com.yellobeansoft.happymovie;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

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
    private ArrayList<String> timeList = new ArrayList<String>();

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
            mViewHolder.movieDate = (TextView) convertView.findViewById(R.id.txtDate);
            mViewHolder.screen = (TextView) convertView.findViewById(R.id.txtScreen);
            mViewHolder.showtimeType = (TextView) convertView.findViewById(R.id.txtType);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        // Get data from movie class
        movieTable = new MovieTable(mContext);
        objMovie = movieTable.getMovies(mShowtimeList.get(position).getMovieTitle());

        // Set image
        String path = objMovie.getMovieImg();
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // Concatenate showtime
        timeList = mShowtimeList.get(position).getTimeList();
        showtimeConcat = JoinArray(timeList, "   ");
        Spannable showtimeSpan = new SpannableString(showtimeConcat);
        showtimeSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Set Textview (Movie object)
        mViewHolder.movieName.setText(objMovie.getMovieTitle());
        mViewHolder.movieNameTH.setText(objMovie.getMovieTitleTH());
        mViewHolder.movieRating.setText(objMovie.getRating()+"/10");
        mViewHolder.movieDate.setText(objMovie.getDate());
        // Set Textview (Showtime object)
        mViewHolder.movieShowtime.setText(showtimeSpan);
        mViewHolder.showtimeType.setText(mShowtimeList.get(position).getType());
        mViewHolder.screen.setText(mShowtimeList.get(position).getScreen());
        imageLoader.get(path, ImageLoader.getImageListener(
                mViewHolder.movieImg, R.drawable.ic_loadmovie, R.drawable.ic_loadmovie));

        return convertView;
    }// method getView

/*    private void setShowtimeColor(TextView view, String fulltext, String subtext, int color) {
        view.setText(fulltext, TextView.BufferType.SPANNABLE);
        Spannable str = (Spannable) view.getText();
        int i = fulltext.indexOf(subtext);
        str.setSpan(new ForegroundColorSpan(color), i, i+subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }*/

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
