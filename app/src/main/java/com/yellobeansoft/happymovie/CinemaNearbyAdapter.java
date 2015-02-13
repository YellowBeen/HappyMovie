package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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

            convertView = mInflater.inflate(R.layout.layout_cinema_allitem, null);

            mViewHolder = new ViewHolder();
            mViewHolder.cinemaName = (TextView) convertView.findViewById(R.id.txtCinema);
            mViewHolder.cinemaNameTH = (TextView) convertView.findViewById(R.id.txtCinemaTH);
            mViewHolder.favImg = (Button) convertView.findViewById(R.id.btnFavourite);
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

        int sdk = Build.VERSION.SDK_INT;
        if (objCinemaFav.checkExist(mContext, objCinema)){
            if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
                mViewHolder.favImg.setBackgroundDrawable(mViewHolder.favImg.getContext().getResources().getDrawable(R.drawable.ic_favon));
            } else {
                mViewHolder.favImg.setBackground(mViewHolder.favImg.getContext().getResources().getDrawable(R.drawable.ic_favon));
            }
        } else {
            if(sdk < Build.VERSION_CODES.JELLY_BEAN) {
                mViewHolder.favImg.setBackgroundDrawable(mViewHolder.favImg.getContext().getResources().getDrawable(R.drawable.ic_favoff));
            } else {
                mViewHolder.favImg.setBackground(mViewHolder.favImg.getContext().getResources().getDrawable(R.drawable.ic_favoff));
            }

        }

        mViewHolder.favImg.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                objCinema = mCinemaList.get(position);
                if (objCinemaFav.checkExist(mContext, objCinema)) {
                    objCinemaFav.removeFavorite(mContext, objCinema);
                } else {
                    objCinemaFav.addFavorite(mContext, objCinema);
                }

                mCinemaAdapter.notifyDataSetChanged();
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objCinema = mCinemaList.get(position);
                Toast.makeText(mContext, objCinema.getName(),
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ShowtimeCinemaActivity.class);
                intent.putExtra("Cinema", objCinema.getName());
                intent.putExtra("CinemaTH", objCinema.getNameTH());
                v.getContext().startActivity(intent);

            }
        });
        //Jack
    /*    convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextProvider objContext = new ContextProvider();
                CinemaFavorite objCinemaFav = new CinemaFavorite();
                ArrayList<Cinema> cinemaFavList = objCinemaFav.getFavorites(objContext.getContext());
                Cinema objCinema = mCinemaList.get(position);
                if (objCinemaFav.checkExist(objContext.getContext(), objCinema)) {
                    objCinemaFav.removeFavorite(objContext.getContext(), objCinema);
                } else {
                    objCinemaFav.addFavorite(objContext.getContext(), objCinema);
                }
                Toast.makeText(mContext, "Fav Button" + mCinemaList.get(position).getName(),
                        Toast.LENGTH_SHORT).show();

            }
        });//Jack        */

        return convertView;
    }

    private static class ViewHolder {
        public Button favImg;
        public TextView cinemaName;
        public TextView cinemaNameTH;
        public TextView distance;
    }// class ViewHolder

}
