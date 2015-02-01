package com.yellobeansoft.happymovie;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.etsy.android.grid.util.DynamicHeightImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Beboyz on 1/18/15 AD.
 */


public class MovieStaggeredAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<Movies> mMovies;
    private Movies movie;

    private static final SparseArray<Double> sPositionHeightRatios =
            new SparseArray<Double>();
    private final Random mRandom;

    static class ViewHolder {
        DynamicHeightImageView imageView;
        TextView movieTitle;
        TextView movieRating;
        TextView movieLength;
    }

    public MovieStaggeredAdapter(final Context context, final int staggeredId) {
        super(context, staggeredId);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mRandom = new Random();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.layout_staggered_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView =
                    (DynamicHeightImageView) convertView.findViewById(R.id.image);
            viewHolder.movieTitle = (TextView) convertView.findViewById(R.id.txtMovieTitle);
            viewHolder.movieRating = (TextView) convertView.findViewById(R.id.txtRating);
            viewHolder.movieLength = (TextView) convertView.findViewById(R.id.txtMovieLength);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);

        //movie = mMovies.get(position);
        //String path = movie.getMovieImg();
        String path = getItem(position);
        // Loading image with placeholder and error image ##Volley##
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imageLoader.get(path, ImageLoader.getImageListener(
                viewHolder.imageView,R.drawable.ic_loadmovie,R.drawable.ic_loadmovie));

//        Picasso.with(mContext)
//                .load(path)
//                .error(R.drawable.ic_loadmovie)
//                .placeholder(R.drawable.ic_loadmovie)
//                .into(viewHolder.imageView);
//        final Movies movie = mMovies.get(position);
        viewHolder.movieTitle.setText("Movie Title");
        viewHolder.movieRating.setText("Rating");
        viewHolder.movieLength.setText("Duration");
//        viewHolder.movieTitle.setText(movie.getMovieTitle());
//        viewHolder.movieRating.setText(movie.getRating());
//        viewHolder.movieLength.setText(movie.getMovieLength());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Toast.makeText(getContext(), "Pic" + Integer.toString(position), Toast.LENGTH_SHORT).show();
                intent.putExtra("id", Integer.toString(position));
                intent.putExtra("flagmovie", "1");
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
}