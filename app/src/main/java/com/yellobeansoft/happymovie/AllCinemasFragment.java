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

    public static AllCinemasFragment newInstance() {
        AllCinemasFragment fragment = new AllCinemasFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "AllCinema");
        View view = inflater.inflate(R.layout.layout_cinema_all,container,false);
        lvExpCinema = (ExpandableListView) view.findViewById(R.id.lvExp);


        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) view.findViewById(R.id.search);
        search.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);

        search.setOnCloseListener(this);
        search.clearFocus();//pon add fix always show keyboard

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
            ArrayList<Cinema> cinemaQuartier = new ArrayList<Cinema>();

            CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());
            cinemaList = objCinemaTABLE.getAllCinemas();
            for (int i=0; i < cinemaList.size(); i++){
               switch (cinemaList.get(i).getBrand()){
                   case "MAJOR CINEPLEX":
                       cinemaMAJOR.add(cinemaList.get(i));
                       break;
                   case "EGV CINEMA":
                       cinemaEGV.add(cinemaList.get(i));
                       break;
                   case "ESPLANADE CINEPLEX":
                       cinemaESPLANADE.add(cinemaList.get(i));
                       break;
                   case "HATYAI CINEPLEX":
                       cinemaHATYAI.add(cinemaList.get(i));
                       break;
                   case "IMAX THEATRE":
                       cinemaIMAX.add(cinemaList.get(i));
                       break;
                   case "MEGA CINEPLEX":
                       cinemaMEGA.add(cinemaList.get(i));
                       break;
                   case "PARADISE CINEPLEX":
                       cinemaPARADISE.add(cinemaList.get(i));
                       break;
                   case "PARAGON CINEPLEX":
                       cinemaPARAGON.add(cinemaList.get(i));
                       break;
                   case "QUARTIER CINEART":
                       cinemaQuartier.add(cinemaList.get(i));
                       break;
                   case "SFX CINEMA":
                       cinemaSFX.add(cinemaList.get(i));
                       break;
                   case "SF WORLD CINEMA":
                       cinemaSFWORLD.add(cinemaList.get(i));
                       break;
                   case "SF CINEMA CITY":
                       cinemaSF.add(cinemaList.get(i));
                       break;
               }

            }
            CinemaGroup cinemaGroup = new CinemaGroup("MAJOR CINEPLEX", cinemaMAJOR);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("EGV", cinemaEGV);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("ESPLANADE CINEPLEX", cinemaESPLANADE);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("HATYAI CINEPLEX", cinemaHATYAI);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("IMAX THEATRE", cinemaIMAX);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("MEGA CINEPLEX", cinemaMEGA);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("PARADISE CINEPLEX", cinemaPARADISE);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("PARAGON CINEPLEX", cinemaPARAGON);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("QUARTIER CINEART", cinemaQuartier);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("SF CINEMA CITY", cinemaSF);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("SFX CINEMA", cinemaSFX);
            cinemaGroups.add(cinemaGroup);
            cinemaGroup = new CinemaGroup("SF WORLD CINEMA", cinemaSFWORLD);
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

    @Override
    public void onDestroyView() {
        Log.d("onDestroyView", "AllCinema");
        super.onDestroyView();
    }
}



