package com.yellobeansoft.happymovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class CinemaAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<Cinema> mCinemaList;
    private ViewHolder mViewHolder;

    public CinemaAdapter(Context context, ArrayList<Cinema> lists) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCinemaList = lists;
    }

    @Override
    public int getCount() {
        return mCinemaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCinemaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.layout_cinema_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.favImg = (ImageButton) convertView.findViewById(R.id.btnFav);
            mViewHolder.cinemaName = (TextView) convertView.findViewById(R.id.txtCinema);

            if (mCinemaList.get(position).getName() != null) {
                mViewHolder.cinemaName.setText(mCinemaList.get(position).getName());
                //mViewHolder.favImg.setImageDrawable();
                mViewHolder.cinemaName.setFocusable(false);
                mViewHolder.cinemaName.setFocusableInTouchMode(false);
            }
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }



        return convertView;
    }

    private static class ViewHolder {
        public ImageButton favImg;
        public TextView cinemaName;
    }// class ViewHolder

}
