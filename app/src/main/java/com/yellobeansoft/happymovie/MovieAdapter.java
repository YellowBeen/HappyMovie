package com.yellobeansoft.happymovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class MovieAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<MovieHolder> mMovieList;
    private ViewHolder mViewHolder;

    public MovieAdapter(Context context, ArrayList<MovieHolder> lists) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mMovieList = lists;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }// method getCount

    @Override
    public Object getItem(int position) {
        return mMovieList.get(position);
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
           // mViewHolder.title = (TextView) convertView.findViewById(R.id.txtMovieName);
            mViewHolder.image = (ImageView) convertView.findViewById(R.id.imgMovie);
            mViewHolder.rating = (TextView) convertView.findViewById(R.id.txtRating);
            mViewHolder.length = (TextView) convertView.findViewById(R.id.txtDuration);

            mViewHolder.title.setText(mMovieList.get(position).getMovieTitle());
            mViewHolder.image.setImageDrawable(mMovieList.get(position).getMovieImg());
            mViewHolder.rating.setText("Rating :" + mMovieList.get(position).getRating());
            mViewHolder.length.setText("Length :" + mMovieList.get(position).getMovieLength());

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }// method getView

    private static class ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView rating;
        public TextView length;
    }// class ViewHolder
}
