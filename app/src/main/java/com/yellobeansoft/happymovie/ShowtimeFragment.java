package com.yellobeansoft.happymovie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;



public class ShowtimeFragment extends Fragment {
    private ExpandableListView lvExpShowtime;
    private ArrayList<ShowtimeGroup> showtimeGroups = new ArrayList<ShowtimeGroup>();


    private ListView lvShowtime;

    private TextView txtCinemaNameTH;
    private TextView txtShowDate;
    private ShowtimeFragmentAdapter lvShowtimeAdapter;
    private String chooseCinema;
    private String chooseCinemaTH;


    private ShowTimeTABLE objShowTimeTABLE;
    private TimePickerDialog timePickerDialog;
    ArrayList<ShowTime> showTimesList;
    private ArrayList<Cinema> cinemaList;
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
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);

        //lvShowtime = (ListView) view.findViewById(R.id.lvShowtime);
        //txtCinemaNameTH = (TextView) view.findViewById(R.id.txtCinemaNameTH);
        txtShowDate = (TextView) view.findViewById(R.id.txtShowtimeDate);
        cinemaList = objCinemaFav.getFavorites(getActivity());
        try {
            setupShowtimeAdapter();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //txtCinemaNameTH.setText(chooseCinemaTH);
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        txtShowDate.setText(dateFormat.format(date));
        expandAll();
        return view;
    }

    private void setupShowtimeAdapter() throws ParseException {
            lvShowtimeAdapter = new ShowtimeFragmentAdapter(getActivity(), cinemaList);
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


