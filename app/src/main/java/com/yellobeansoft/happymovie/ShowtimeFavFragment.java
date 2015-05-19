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

public class ShowtimeFavFragment extends Fragment {

    private ExpandableListView lvExpShowtime;
    private ArrayList<ShowtimeGroup> showtimeGroups = new ArrayList<ShowtimeGroup>();
    private ArrayList<Cinema> cinemaList;
    private ShowtimeExpandAdapter showtimeExpandAdapter;
    private Cinema cinema = new Cinema();
    private TextView emptyExp;
    private String cinemaName;
    private CinemaFavorite objCinemaFav = new CinemaFavorite();
    private ShowTimeTABLE objShowTimeTABLE;
    private String mEmptyText;
    ArrayList<ShowTime> showTimesList;
    private static String mChooseMovie;

    public static ShowtimeFavFragment newInstance(String chooseMovie) {
        ShowtimeFavFragment fragment = new ShowtimeFavFragment();
        mChooseMovie = chooseMovie;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.layout_showtime_movie_expand,container,false);
        lvExpShowtime = (ExpandableListView) view.findViewById(R.id.lvExpShowtime);
        lvExpShowtime.setGroupIndicator(null);
        // Matching View
        emptyExp = (TextView) view.findViewById(R.id.txtEmptyExp);

        addShowtimeData();
        try {
            setupShowtimeAdapter();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        emptyExp.setText(mEmptyText);
        lvExpShowtime.setEmptyView(emptyExp);
        return view;
    }


    @Override
    public void onResume()
    {
        try {
            setupShowtimeAdapter();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    private void setupShowtimeAdapter() throws ParseException {
        showtimeExpandAdapter = new ShowtimeExpandAdapter(getActivity(),showtimeGroups);
        lvExpShowtime.setAdapter(showtimeExpandAdapter);
        expandAll();
    }

    private void addShowtimeData() {
        showtimeGroups.clear();
        objCinemaFav = new CinemaFavorite();
        ArrayList<Cinema> cinemas = objCinemaFav.getFavorites(getActivity());
        if ( cinemas.size() == 0 ) {
            mEmptyText = getString(R.string.emptyFav);
        }else {
            if (!mChooseMovie.equalsIgnoreCase(null)) {
                objShowTimeTABLE = new ShowTimeTABLE(getActivity());
                for (int i = 0; i < cinemas.size(); i++) {
                    try {
                        cinema = cinemas.get(i);
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
                if (showTimesList.size() == 0) {
                    mEmptyText = getString(R.string.emptyShFav);
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
        Log.d("onDestroyView", "FavShowtime");
        super.onDestroyView();
    }
}
