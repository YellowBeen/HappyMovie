package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/17/15 AD.
 */
public class NearbyFragment extends Fragment {

    private CinemaAdapter lvCinemaAdapter;
    private ListView lvCinema;
    private ArrayList<Cinema> cinemaList;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_cinema_nearby,container,false);
        lvCinema = (ListView) view.findViewById(R.id.lvCinema);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        addCinemaData();
        setupCinemaAdapter();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                cinemaList.clear();
                addCinemaData();
                setupCinemaAdapter();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    private void setupCinemaAdapter() {
        if (cinemaList != null) {
        lvCinemaAdapter = new CinemaAdapter(getActivity(), cinemaList);
            lvCinema.setAdapter(lvCinemaAdapter);
            lvCinemaAdapter.notifyDataSetChanged();
        }
    }

    private void addCinemaData() {
        CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());
        cinemaList = objCinemaTABLE.getNearByCinemas();
    }

}

