package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class CinemaNearbyAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    ArrayList<Cinema> mCinemaList;
    private ViewHolder mViewHolder;
    private CinemaNearbyAdapter mCinemaAdapter;
    private Cinema objCinema;
    private CinemaFavorite objCinemaFav;
    public CinemaNearbyAdapter(Context context, ArrayList<Cinema> lists) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCinemaList = lists;
        mCinemaAdapter = this;
    }

    @Override
    public int getCount() {
        return mCinemaList.size();
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

            convertView = mInflater.inflate(R.layout.layout_cinema_nearby_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.cinemaName = (TextView) convertView.findViewById(R.id.txtCinema);
            mViewHolder.cinemaNameTH = (TextView) convertView.findViewById(R.id.txtCinemaTH);
            mViewHolder.distance = (TextView) convertView.findViewById(R.id.txtDistance);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();

        }
        double dist = mCinemaList.get(position).getDistance();
        String txtDistance = String.format("%.2f", dist);
        mViewHolder.distance.setText(txtDistance+"km");
        mViewHolder.cinemaName.setText(mCinemaList.get(position).getName());
        mViewHolder.cinemaNameTH.setText(mCinemaList.get(position).getNameTH());
        objCinemaFav = new CinemaFavorite();
        objCinema = mCinemaList.get(position);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objCinema = mCinemaList.get(position);
                Toast.makeText(mContext, objCinema.getName(),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ShowtimeCinemaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("chooseCinema",objCinema);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    private static class ViewHolder {
        public TextView cinemaName;
        public TextView cinemaNameTH;
        public TextView distance;
    }// class ViewHolder

}
