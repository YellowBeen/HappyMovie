package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

public class ShowtimeNearbyFragment extends Fragment {

    private ExpandableListView lvExpShowtime;
    private ArrayList<ShowtimeGroup> showtimeGroups = new ArrayList<ShowtimeGroup>();
    private ArrayList<Cinema> cinemaList;
    private ShowtimeExpandAdapter showtimeExpandAdapter;
    private Cinema cinema = new Cinema();
    private TextView emptyExp;
    private String cinemaName;
    private ShowTimeTABLE objShowTimeTABLE;
    ArrayList<ShowTime> showTimesList;
    private static String mChooseMovie;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static ShowtimeNearbyFragment newInstance(String chooseMovie) {
        ShowtimeNearbyFragment fragment = new ShowtimeNearbyFragment();
        mChooseMovie = chooseMovie;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "NearbyShowtime");
        // Inflate layout
        View view = inflater.inflate(R.layout.layout_showtime_movie_nearby_expand,container,false);
        lvExpShowtime = (ExpandableListView) view.findViewById(R.id.lvExpShowtime);
        // Matching View
        emptyExp = (TextView) view.findViewById(R.id.txtEmptyExp);
        emptyExp.setText(getString(R.string.emptyNearby));
        lvExpShowtime.setEmptyView(emptyExp);
        
        GPSTracker objGPS = new GPSTracker(getActivity());
        if (objGPS.isGPSEnabled) {
            addShowtimeData();
            try {
                setupShowtimeAdapter();
                expandAll();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GPSTracker objGPS = new GPSTracker(getActivity());
                if (objGPS.isGPSEnabled) {
                    addShowtimeData();
                    try {
                        setupShowtimeAdapter();
                        expandAll();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }


    private void setupShowtimeAdapter() throws ParseException {
        if(showtimeGroups!=null) {
            showtimeExpandAdapter = new ShowtimeExpandAdapter(getActivity(), showtimeGroups);
            lvExpShowtime.setAdapter(showtimeExpandAdapter);
            lvExpShowtime.setGroupIndicator(null);
        }
    }

    private void addShowtimeData() {
        if (!mChooseMovie.equalsIgnoreCase(null)) {
            CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());
            cinemaList = objCinemaTABLE.getNearByCinemasByMovie(mChooseMovie);

            if (cinemaList != null) {

                objShowTimeTABLE = new ShowTimeTABLE(getActivity());
                showtimeGroups.clear();
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
        Log.d("onDestroyView", "NearbyShowtime");
        super.onDestroyView();
    }
}
