package com.yellobeansoft.happymovie;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by Beboyz on 1/16/15 AD.
 */
public class ShowtimeAllFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private ShowtimeAllExpandAdapter lvCinemaAdapter;
    private SearchView search;
    private Cinema cinema = new Cinema();
    private ExpandableListView lvExpCinema;
    private TextView emptyExp;
    private ArrayList<CinemaGroup> cinemaGroups = new ArrayList<CinemaGroup>();
    private ArrayList<Cinema> cinemaList;
    private static Movies mChooseObjMovie;
    private CinemaGroup cinemaGroup;

    public static ShowtimeAllFragment newInstance(Movies chooseObjMovie) {
        ShowtimeAllFragment fragment = new ShowtimeAllFragment();
        mChooseObjMovie = chooseObjMovie;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "AllCinema");
        View view = inflater.inflate(R.layout.layout_cinema_all,container,false);
        lvExpCinema = (ExpandableListView) view.findViewById(R.id.lvExp);
        emptyExp = (TextView) view.findViewById(R.id.txtEmptyExp);
        emptyExp.setText(getString(R.string.emptyShAll));
        lvExpCinema.setEmptyView(emptyExp);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) view.findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);

        search.setOnCloseListener(this);


        int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) search.findViewById(id);
        textView.setTextColor(Color.WHITE);



        displayList();
        expandAll();
        return view;
    }

    private void displayList() {

        addShowtimeData();
        lvCinemaAdapter = new ShowtimeAllExpandAdapter(getActivity(), cinemaGroups, mChooseObjMovie);
        lvExpCinema.setAdapter(lvCinemaAdapter);

    }

    private void addShowtimeData() {
        if (!mChooseObjMovie.getMovieTitle().equalsIgnoreCase(null)) {
            CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());
            try {
                cinemaList = objCinemaTABLE.getAllCinemasByMovie(mChooseObjMovie.getMovieTitle());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ArrayList<Cinema> cinemaMAJOR = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaEGV = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaESPLANADE = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaHATYAI = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaIMAX = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaMEGA = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaPARADISE = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaPARAGON = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaSF = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaSFX = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaSFWORLD = new ArrayList<Cinema>();
            for (int i = 0; i < cinemaList.size(); i++) {
                    cinema = cinemaList.get(i);
                switch (cinema.getBrand()){
                    case "MAJOR CINEPLEX":
                        cinemaMAJOR.add(cinema);
                        break;
                    case "EGV CINEMA":
                        cinemaEGV.add(cinema);
                        break;
                    case "ESPLANADE CINEPLEX":
                        cinemaESPLANADE.add(cinema);
                        break;
                    case "HATYAI CINEPLEX":
                        cinemaHATYAI.add(cinema);
                        break;
                    case "IMAX THEATRE":
                        cinemaIMAX.add(cinema);
                        break;
                    case "MEGA CINEPLEX":
                        cinemaMEGA.add(cinema);
                        break;
                    case "PARADISE CINEPLEX":
                        cinemaPARADISE.add(cinema);
                        break;
                    case "PARAGON CINEPLEX":
                        cinemaPARAGON.add(cinema);
                        break;
                    case "SFX CINEMA":
                        cinemaSFX.add(cinema);
                        break;
                    case "SF WORLD CINEMA":
                        cinemaSFWORLD.add(cinema);
                        break;
                    case "SF CINEMA CITY":
                        cinemaSF.add(cinema);
                        break;
                }
            }
            if (cinemaMAJOR.size()!=0) {
                cinemaGroup = new CinemaGroup("MAJOR CINEPLEX", cinemaMAJOR);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaEGV.size()!=0) {
                cinemaGroup = new CinemaGroup("EGV", cinemaEGV);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaESPLANADE.size()!=0) {
                cinemaGroup = new CinemaGroup("ESPLANADE CINEPLEX", cinemaESPLANADE);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaHATYAI.size()!=0) {
                cinemaGroup = new CinemaGroup("HATYAI CINEPLEX", cinemaHATYAI);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaIMAX.size()!=0) {
                cinemaGroup = new CinemaGroup("IMAX THEATRE", cinemaIMAX);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaMEGA.size()!=0) {
                cinemaGroup = new CinemaGroup("MEGA CINEPLEX", cinemaMEGA);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaPARADISE.size()!=0) {
                cinemaGroup = new CinemaGroup("PARADISE CINEPLEX", cinemaPARADISE);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaPARAGON.size()!=0) {
                cinemaGroup = new CinemaGroup("PARAGON CINEPLEX", cinemaPARAGON);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaSF.size()!=0) {
                cinemaGroup = new CinemaGroup("SF CINEMA CITY", cinemaSF);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaSFX.size()!=0) {
                cinemaGroup = new CinemaGroup("SFX CINEMA", cinemaSFX);
                cinemaGroups.add(cinemaGroup);
            }
            if (cinemaSFWORLD.size()!=0) {
                cinemaGroup = new CinemaGroup("SF WORLD CINEMA", cinemaSFWORLD);
                cinemaGroups.add(cinemaGroup);
            }
        }


    }

    //method to expand all groups
    private void expandAll() {
        int count = lvCinemaAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            lvExpCinema.expandGroup(i);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public boolean onClose() {
        lvCinemaAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        lvCinemaAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        lvCinemaAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public void onDestroyView() {
        Log.d("onDestroyView", "AllCinema");
        super.onDestroyView();
    }
}


/*public class ShowtimeAllFragment extends Fragment {

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
}*/
