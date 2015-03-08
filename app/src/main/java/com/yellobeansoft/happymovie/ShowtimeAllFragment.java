package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Beboyz on 1/16/15 AD.
 */

public class ShowtimeAllFragment extends Fragment {

    private ExpandableListView lvExpShowtime;
    private ArrayList<ShowtimeGroup> showtimeGroups = new ArrayList<ShowtimeGroup>();
    private ArrayList<Cinema> cinemaList;
    private ShowtimeExpandAdapter showtimeExpandAdapter;
    private Cinema cinema = new Cinema();
    private TextView emptyNearby;
    private String cinemaName;
    private ShowTimeTABLE objShowTimeTABLE;
    ArrayList<ShowTime> showTimesList;
    private static String mChooseMovie;

    public static ShowtimeAllFragment newInstance(String chooseMovie) {
        ShowtimeAllFragment fragment = new ShowtimeAllFragment();
        mChooseMovie = chooseMovie;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "AllCinemaShowtime");
        // Inflate layout
        View view = inflater.inflate(R.layout.layout_showtime_movie_expand,container,false);
        lvExpShowtime = (ExpandableListView) view.findViewById(R.id.lvExpShowtime);
        // Matching View
        emptyNearby = (TextView) view.findViewById(R.id.txtEmptyFav);
        lvExpShowtime.setEmptyView(emptyNearby);
        addShowtimeData();
        try {
            setupShowtimeAdapter();
            expandAll();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }


    private void setupShowtimeAdapter() throws ParseException {
        showtimeExpandAdapter = new ShowtimeExpandAdapter(getActivity(),showtimeGroups);
        lvExpShowtime.setAdapter(showtimeExpandAdapter);
        lvExpShowtime.setGroupIndicator(null);
    }

    private void addShowtimeData() {
        if (!mChooseMovie.equalsIgnoreCase(null)) {
            CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());

            try {
                cinemaList = objCinemaTABLE.getAllCinemasByMovie(mChooseMovie);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            objShowTimeTABLE = new ShowTimeTABLE(getActivity());
            for (int i = 0; i < cinemaList.size(); i++) {
                try {
                    cinema = cinemaList.get(i);
                    cinemaName = cinema.getName();
                    showTimesList = objShowTimeTABLE.getShowTimeByMovieCinema(mChooseMovie, cinemaName, "");
                    if (showTimesList.size() != 0) {
                        ArrayList<ShowTime> showTime = new ArrayList<ShowTime>();
                        ShowtimeGroup showtimeGroup = new ShowtimeGroup(cinema, showTimesList);
                        showtimeGroups.add(showtimeGroup);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //method to expand all groups
    private void expandAll() {
        int count = showtimeExpandAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            lvExpShowtime.expandGroup(i);
        }
    }

    @Override
    public void onDestroyView() {
        Log.d("onDestroyView", "AllCinemaShowtime");
        super.onDestroyView();
    }
}
