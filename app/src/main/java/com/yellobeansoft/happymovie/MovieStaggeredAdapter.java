package com.yellobeansoft.happymovie;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.*;

/**
 * Created by Beboyz on 1/18/15 AD.
 */
public class MovieStaggeredAdapter extends ArrayAdapter<Movies> {

    private LayoutInflater mInflater;
    private int mResource;
    private ArrayList<Movies> mMovies;
    private Movies movie;
    private Activity mActivity;
    private Context mContext;

    private static final SparseArray<Double> sPositionHeightRatios =
            new SparseArray<Double>();
    private final Random mRandom;

    static class ViewHolder {
        DynamicHeightImageView imageView;
        TextView movieTitle;
        TextView movieRating;
        TextView movieLength;
        ImageView imgIsNew;
    }

    public MovieStaggeredAdapter(Activity activity, int resource, ArrayList<Movies> movies) {
        super(activity, resource, movies);
        this.mActivity = activity;
        this.mResource = resource;
        this.mRandom = new Random();
        this.mMovies = movies;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            mInflater = mActivity.getLayoutInflater();
            convertView = mInflater.inflate(mResource, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView =
                    (DynamicHeightImageView) convertView.findViewById(R.id.image);
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.txtMovieTitle);
            viewHolder.movieRating = (TextView) convertView.findViewById(R.id.txtRating);
        //    viewHolder.movieLength = (TextView) convertView.findViewById(R.id.txtDuration);
            viewHolder.imgIsNew = (ImageView) convertView.findViewById(R.id.imgReleased);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);

        movie = mMovies.get(position);
        String path = movie.getMovieImg();

        // Loading image with placeholder and error image ##Volley##
//        ImageLoader imageLoader = new ImageLoader(Volley.newRequestQueue(getContext()), new BitmapLruCache(
//                100));

//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        ImageLoader imageLoader = MyVolleySingleton.getInstance(getContext()).getImageLoader();
        imageLoader.get(path, ImageLoader.getImageListener(
        viewHolder.imageView, R.drawable.ic_loadmovie, R.drawable.ic_loadmovie));
        if (movie.getIsNew()) {
            viewHolder.imgIsNew.setBackgroundResource(R.drawable.ic_new_released);
        } else {
            viewHolder.imgIsNew.setBackgroundResource(0);
        }
        viewHolder.movieTitle.setText(movie.getMovieTitle());
        viewHolder.movieRating.setText(movie.getRating());
        //viewHolder.movieLength.setText(movie.getMovieLength());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mContext = v.getContext();
                GPSTracker objGPS = new GPSTracker(mContext);

                if (objGPS.isGPSEnabled) {
                    new WaitDialog().execute();
                }

                Intent intent = new Intent(v.getContext(), ShowtimeMovieActivity.class);
                movie = mMovies.get(position);
                Log.d("click", movie.getMovieTitle());
                Bundle bundle = new Bundle();
                bundle.putParcelable("chooseMovie", movie);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }

    class WaitDialog extends AsyncTask<String, Integer, String> {

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
                sleep(1000);
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