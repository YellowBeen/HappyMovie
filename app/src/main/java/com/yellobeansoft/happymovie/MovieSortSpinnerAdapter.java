package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 2/4/15 AD.
 */
public class MovieSortSpinnerAdapter extends BaseAdapter{

    private TextView txtSort;
    private ArrayList<SpinnerMoviesSort> spinnerSort;
    private Context context;

    public MovieSortSpinnerAdapter(ArrayList<SpinnerMoviesSort> spinnerSort, Context context) {
        this.spinnerSort = spinnerSort;
        this.context = context;
    }

    @Override
    public int getCount() {
        return spinnerSort.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerSort.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_movie_spinner, null);
        }

        txtSort = (TextView) convertView.findViewById(R.id.txtSort);
        txtSort.setText(spinnerSort.get(position).getSort());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_movie_spinner, null);
        }

        txtSort = (TextView) convertView.findViewById(R.id.txtSort);
        txtSort.setText(spinnerSort.get(position).getSort());
        return convertView;
    }
}
