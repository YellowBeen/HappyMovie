package com.yellobeansoft.happymovie;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/17/15 AD.
 */
public class AllCinemasFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private CinemaExpandAdapter lvCinemaAdapter;
    private SearchView search;
    private ExpandableListView lvExpCinema;
    private ArrayList<CinemaGroup> cinemaGroups = new ArrayList<CinemaGroup>();
    private ArrayList<Cinema> cinemaList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_cinema_all,container,false);
        lvExpCinema = (ExpandableListView) view.findViewById(R.id.lvExp);

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

        loaddata();

        lvCinemaAdapter = new CinemaExpandAdapter(getActivity(), cinemaGroups);
        lvExpCinema.setAdapter(lvCinemaAdapter);

    }

    private void loaddata() {

        if (cinemaGroups.size() == 0) {

            ArrayList<Cinema> cinemaMajor = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaEGV = new ArrayList<Cinema>();
            ArrayList<Cinema> cinemaSF = new ArrayList<Cinema>();

            CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());
            cinemaList = objCinemaTABLE.getAllCinemas();
            for (int i=0; i < cinemaList.size(); i++){
               switch (cinemaList.get(i).getBrand()){
                   case "Major":
                       cinemaMajor.add(cinemaList.get(i));
                       break;
                   case "EGV":
                       cinemaEGV.add(cinemaList.get(i));
                       break;
                   case "SF":
                       cinemaSF.add(cinemaList.get(i));
                       break;
               }

            }
            CinemaGroup cinemaGroup = new CinemaGroup("Major Cineplex", cinemaMajor);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("EGV", cinemaEGV);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("SF Cinema City", cinemaSF);
            cinemaGroups.add(cinemaGroup);
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
}



