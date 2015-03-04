package com.yellobeansoft.happymovie;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;



public class ShowtimeFragment extends Fragment {
    private ExpandableListView lvExpShowtime;
    private LinearLayout llempty;
    private ArrayList<ShowtimeGroup> showtimeGroups = new ArrayList<ShowtimeGroup>();
    private LinearLayout llmovie;
    private TextView txtShowDate;
    private ShowtimeFragmentAdapter lvShowtimeAdapter;
    private String chooseCinema;
    private String chooseCinemaTH;


    private ShowTimeTABLE objShowTimeTABLE;
    private TimePickerDialog timePickerDialog;
    ArrayList<ShowTime> showTimesList;
    //private ArrayList<Cinema> cinemaList;
    private CinemaFavorite objCinemaFav = new CinemaFavorite();

    public interface OnRefreshListener {
        public void onRefresh();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_showtime_fragment, container, false);
        lvExpShowtime = (ExpandableListView) view.findViewById(R.id.expShowtimeList);
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);

        //lvShowtime = (ListView) view.findViewById(R.id.lvShowtime);
        //txtCinemaNameTH = (TextView) view.findViewById(R.id.txtCinemaNameTH);

        llempty = (LinearLayout) view.findViewById(R.id.llempty);
        objCinemaFav = new CinemaFavorite();
        ArrayList<Cinema> cinemas = objCinemaFav.getFavorites(getActivity());
        if (cinemas != null) {
            if (cinemas.size() > 0) {
                lvExpShowtime.setVisibility(View.VISIBLE);
                llempty.setVisibility(View.GONE);
                //set up expand adapter if there is data
                try {
                    setupShowtimeAdapter();
                    expandAll();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                lvExpShowtime.setVisibility(View.GONE);
                llempty.setVisibility(View.VISIBLE);
            }}else{
            lvExpShowtime.setVisibility(View.GONE);
            llempty.setVisibility(View.VISIBLE);
        }


        txtShowDate = (TextView) view.findViewById(R.id.txtShowtimeDate);


        //txtCinemaNameTH.setText(chooseCinemaTH);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        txtShowDate.setText(dateFormat.format(date));

        llmovie = (LinearLayout) view.findViewById(R.id.llMovie);
        String chooseMovie = getActivity().getIntent().getStringExtra("MovieTitle");
//        if (chooseMovie.isEmpty()){
//            llmovie.setVisibility(View.INVISIBLE);
//        }else{
//            llmovie.setVisibility(View.VISIBLE);
//        }

        return view;
    }

    private void setupShowtimeAdapter() throws ParseException {
            lvShowtimeAdapter = new ShowtimeFragmentAdapter(getActivity(),getActivity().getIntent());
            lvExpShowtime.setAdapter(lvShowtimeAdapter);

        }
    //method to expand all groups
    private void expandAll() {
        int count = lvShowtimeAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            lvExpShowtime.expandGroup(i);
        }
    }

}


