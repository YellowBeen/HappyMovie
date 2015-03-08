package com.yellobeansoft.happymovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/17/15 AD.
 */
public class NearbyFragment extends Fragment {

    private CinemaNearbyAdapter lvCinemaAdapter;
    private ListView lvCinema;
    private ArrayList<Cinema> cinemaList;
    private TextView emptyNearby;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static NearbyFragment newInstance() {
        NearbyFragment fragment = new NearbyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("onCreateView", "Nearby");
        View view = inflater.inflate(R.layout.layout_cinema_nearby,container,false);
        lvCinema = (ListView) view.findViewById(R.id.lvCinema);
        emptyNearby = (TextView) view.findViewById(R.id.txtEmptyNearby);
        lvCinema.setEmptyView(emptyNearby);
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

    private void setupCinemaAdapter() {
        if (cinemaList != null) {
        lvCinemaAdapter = new CinemaNearbyAdapter(getActivity(), cinemaList);
            lvCinema.setAdapter(lvCinemaAdapter);
        }
    }

    private void addCinemaData() {
        CinemaTABLE objCinemaTABLE = new CinemaTABLE(getActivity());
        cinemaList = objCinemaTABLE.getNearByCinemas();
    }

    @Override
    public void onDestroyView() {
        Log.d("onDestroyView", "Nearby");
        super.onDestroyView();
    }

}

