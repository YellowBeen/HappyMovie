package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.content.Context;
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
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.layout_cinema_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.cinemaName = (TextView) convertView.findViewById(R.id.txtCinema);
            mViewHolder.favImg = (Button) convertView.findViewById(R.id.btnFavourite);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();

        }

        mViewHolder.cinemaName.setText(mCinemaList.get(position).getName());
        mViewHolder.favImg.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    v.getResources().getDrawable(R.drawable.ic_favon);
                    mViewHolder.favImg.setBackgroundDrawable(mViewHolder.favImg.getContext().getResources().getDrawable(R.drawable.ic_favon));
                    mViewHolder.favImg.refreshDrawableState();

                } else {
                    mViewHolder.favImg.setBackground(mViewHolder.favImg.getContext().getResources().getDrawable(R.drawable.ic_favon));
                    mViewHolder.favImg.refreshDrawableState();

                }

                Toast.makeText(mContext, "Fav Button" + mCinemaList.get(position).getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });


        //Jack
        convertView.setOnClickListener(new View.OnClickListener() {
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
            }
        });//Jack



        return convertView;
    }

    private static class ViewHolder {
        public Button favImg;
        public TextView cinemaName;
    }// class ViewHolder

}
