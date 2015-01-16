package com.yellobeansoft.happymovie;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Beboyz on 1/15/15 AD.
 */
public class MovieActivity extends Activity{

    private ListView lvMovie;
    private MovieAdapter lvMovieAdapter;
    private ArrayList<MovieHolder> movieList;

    // Sample movies
    private String[] titles = new String[] {"Hobbit",
            "Taken3",
            "Foxcatcher",
            "Seventh Son",
            "Stand by me",
            "The good lie",
            "I fine thank you love you",
            "ตัวพ่อเรียกพ่อ",
            "รักหมดแก้ว",
            "Exodus",
            "สตรีเหล็ก",
            "Night at the museum"};
    // Sample movies pic
    private int[] images = {
            R.drawable.ic_taken3
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie);

        lvMovie = (ListView) findViewById(R.id.lvMovies);
        addMoviesData();
        setupMovieAdapter();


    }// onCreate

    private void addMoviesData() {

        // data
        movieList = new ArrayList<MovieHolder>();
        // Add data
        for (int i = 0; i < titles.length; i++) {
            MovieHolder movies = new MovieHolder();
            movies.setMovieTitle(titles[i]);
            movies.setMovieImg(getResources().getDrawable(images[0]));
            movies.setRating("5");
            movies.setMovieLength("120");
            movieList.add(movies);
        }

    }// addMoviesData

    private void setupMovieAdapter() {

        // Setup adapter
        lvMovieAdapter = new MovieAdapter(getBaseContext(), movieList);
        lvMovie.setAdapter(lvMovieAdapter);

    }// setupMovieAdapter

}
