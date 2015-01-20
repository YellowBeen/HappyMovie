package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Beboyz on 1/16/15 AD.
 */

public class FavFragment extends ListFragment {

    private CinemaAdapter lvCinemaAdapter;
    private ArrayList<Cinema> cinemaList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (cinemaList == null) {
            addCinemaData();
        }

        setupCinemaAdapter();


    }

    private void setupCinemaAdapter() {

        // Setup adapter
        lvCinemaAdapter = new CinemaAdapter(getActivity().getBaseContext(), cinemaList);
        setListAdapter(lvCinemaAdapter);

    }

    private void addCinemaData() {

        CinemaFavorite objCinemaFav = new CinemaFavorite();
        ContextProvider objContext = new ContextProvider();
        cinemaList = objCinemaFav.getFavorites(objContext.getContext());

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
