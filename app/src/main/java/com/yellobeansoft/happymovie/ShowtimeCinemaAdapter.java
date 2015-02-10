package com.yellobeansoft.happymovie;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

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

            convertView = mInflater.inflate(R.layout.layout_movie_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.movieName = (TextView) convertView.findViewById(R.id.txtMovieName);
            mViewHolder.movieImg = (ImageView) convertView.findViewById(R.id.imgMovie);
            mViewHolder.movieRating = (TextView) convertView.findViewById(R.id.txtRating);
            mViewHolder.movieShowtime = (TextView) convertView.findViewById(R.id.txtShowtime);

            Drawable drawable = LoadImageFromWebOperations(objMovie.getMovieImg());

            objMovie = movieTable.getMovies(mShowtimeList.get(position).getMovieTitle());
            mViewHolder.movieName.setText(objMovie.getMovieTitle());
            mViewHolder.movieImg.setImageDrawable(drawable);
            mViewHolder.movieRating.setText(objMovie.getRating());
            //mViewHolder.movieShowtime.setText(mShowtimeList.get(position).getTimeList());

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }


        return convertView;
    }// method getView

    private static class ViewHolder {
        public ImageView movieImg;
        public TextView movieName;
        public TextView movieRating;
        public TextView movieShowtime;
    }// class ViewHolder

    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            System.out.println("Exc="+e);
            return null;
        }
    }


}
