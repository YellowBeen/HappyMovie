package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

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

            addCinemaData();
            setupCinemaAdapter();

    }

    private void setupCinemaAdapter() {
        if (cinemaList != null) {
            lvCinemaAdapter = new CinemaAdapter(getActivity().getBaseContext(), cinemaList);
            setListAdapter(lvCinemaAdapter);
            lvCinemaAdapter.notifyDataSetChanged();
        }
    }

    private void addCinemaData() {

        CinemaFavorite objCinemaFav = new CinemaFavorite();
        cinemaList = null;
        cinemaList = objCinemaFav.getFavorites(getActivity().getBaseContext());

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }
}
