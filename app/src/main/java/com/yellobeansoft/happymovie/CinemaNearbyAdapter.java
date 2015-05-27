package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

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
//                Toast.makeText(mContext, objCinema.getName(),
//                        Toast.LENGTH_SHORT).show();

                new LoadingDialog().execute();

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


    class LoadingDialog extends AsyncTask<String, Integer, String> {

        private ProgressDialog mProgress;

        @Override
        protected void onPreExecute() {
            mProgress = new ProgressDialog(mContext, R.style.Happy_Dialog_Style);
            mProgress.setMessage("Loading...");
            mProgress.setCancelable(true);
            mProgress.setIndeterminate(true);
            mProgress.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Log.d("CINEMA-TEST", "Start Sync");
                DataLoader objLoader = new DataLoader(mContext);
                objLoader.syncAll();
                Log.d("CINEMA-TEST", "Execute Sync");
                sleep(500);
                while (!objLoader.checkShowTimeSyncDone() || !objLoader.checkMovieSyncDone() || !objLoader.checkMovieSyncDone()) {
                }
                Log.d("CINEMA-TEST", "Finish Sync");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);
        }

        @Override
        protected void onPostExecute(String testStr) {
            mProgress.dismiss();

        }

    }

}
