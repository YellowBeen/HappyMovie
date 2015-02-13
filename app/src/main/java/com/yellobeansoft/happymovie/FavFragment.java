package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/16/15 AD.
 */

public class FavFragment extends Fragment {

    private CinemaAdapter lvCinemaAdapter;
    private ListView lvCinema;
    private ArrayList<Cinema> cinemaList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyFav;
    private CinemaFavorite objCinemaFav = new CinemaFavorite();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.layout_cinema_fav,container,false);
        // Matching View
        lvCinema = (ListView) view.findViewById(R.id.lvFavCinema);
        emptyFav = (TextView) view.findViewById(R.id.txtEmptyFav);
        lvCinema.setEmptyView(emptyFav);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);

        addCinemaData();
        setupCinemaAdapter();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addCinemaData();
                setupCinemaAdapter();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    public interface OnRefreshListener {
        public void onRefresh();
    }

    private void setupCinemaAdapter() {
        if (cinemaList != null) {
            lvCinemaAdapter = new CinemaAdapter(getActivity(), cinemaList);
            lvCinema.setAdapter(lvCinemaAdapter);
            lvCinemaAdapter.notifyDataSetChanged();
        } else {

        }
    }

    private void addCinemaData() {

        cinemaList = objCinemaFav.getFavorites(getActivity());
    }


}
