package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class CinemaActivity extends Activity {


    private CinemaAdapter lvCinemaAdapter;
    private ArrayList<Cinema> cinemaList;
    private ListView lvCinema;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_cinema);

        lvCinema = (ListView) findViewById(R.id.lvCinemas);
        addCinemaData();
        setupCinemaAdapter();
    }

    private void setupCinemaAdapter() {

        // Setup adapter
        lvCinemaAdapter = new CinemaAdapter(getBaseContext(), cinemaList);
        lvCinema.setAdapter(lvCinemaAdapter);


    }

    private void addCinemaData() {

        try {
            cinemaList = XMLParser.parse(getAssets().open("cinemas.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }// addCinemaData

}
