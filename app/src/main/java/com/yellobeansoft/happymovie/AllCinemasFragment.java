package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Beboyz on 1/17/15 AD.
 */


public class AllCinemasFragment extends ListFragment {

    private CinemaAdapter lvCinemaAdapter;
    private ArrayList<Cinema> cinemaList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (cinemaList == null) {
            addCinemaData();
        }

        if (cinemaList != null) {
            setupCinemaAdapter();
        }


    }

    private void setupCinemaAdapter() {

        // Setup adapter
        lvCinemaAdapter = new CinemaAdapter(getActivity().getBaseContext(), cinemaList);
        setListAdapter(lvCinemaAdapter);

    }

    private void addCinemaData() {

        CinemaTABLE objCinemaTABLE = new CinemaTABLE();
        cinemaList = objCinemaTABLE.getAllCinemas();

//        try {
//            cinemaList = XMLParser.parse(getActivity().getAssets().open("cinemas.xml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
/*
public class AllCinemasFragment extends ListFragment {

    private CinemaAdapter lvCinemaAdapter;
    private ArrayList<Cinema> cinemaList;

    CinemaExpandAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listHeader;
    HashMap<String, List<String>> listItem;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        expListView = (ExpandableListView) getActivity().findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new CinemaExpandAdapter(this.getActivity(), listHeader, listItem);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //addCinemaData();
        //setupCinemaAdapter();

    }

    private void prepareListData() {
        listHeader = new ArrayList<String>();
        listItem = new HashMap<String, List<String>>();

        // Adding child data
        listHeader.add("Top 250");
        listHeader.add("Now Showing");
        listHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listItem.put(listHeader.get(0), top250); // Header, Child data
        listItem.put(listHeader.get(1), nowShowing);
        listItem.put(listHeader.get(2), comingSoon);
    }

    private void setupCinemaAdapter() {

        // Setup adapter
        lvCinemaAdapter = new CinemaAdapter(getActivity().getBaseContext(), cinemaList);
        setListAdapter(lvCinemaAdapter);

    }

    private void addCinemaData() {

        try {
            cinemaList = XMLParser.parse(getActivity().getAssets().open("cinemas.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}*/
